import java.util.HashMap;
import java.util.Map;


public class Gaussian_Collection {
	
	public static void probability_gaussian(EM_DataNode datanode, int number_cluster){
		
		double data_value = 0.0;
		
		float probability_denominator = 0.0f;
		
		float exponent_denominator = 0.0f;
		
		float exponent_numerator = 0.0f;
		
		float exponent = 0.0f;
		
		float fraction = 0.0f;
		
		float probability_per_cluster = 0.0f;
		
		for (int key: datanode.dataset_values.keySet()){
			
			Map<Integer, Float> probability_cluster = new HashMap<Integer, Float>();
			
			data_value = datanode.dataset_values.get(key);
			
			for ( int i = 0; i < number_cluster; i++){
				
				probability_denominator = (float) (2 * Math.PI * datanode.variance_cluster.get(i));
								
				probability_denominator = (float) (Math.sqrt(probability_denominator));
				
				exponent_numerator = (float) (-1 * ((data_value - datanode.mean_cluster.get(i)) * (data_value - datanode.mean_cluster.get(i))));
								
				exponent_denominator = (float) (2 * datanode.variance_cluster.get(i));		
				
				if (exponent_denominator == 0.0f){
					
					fraction = 0.0f;
				
				} else {
					
					fraction = exponent_numerator / exponent_denominator;	
				
				}			
				
				exponent = (float) Math.exp(fraction);			
				
				if (exponent == 0.0f){
					exponent = (float)0.00001;
				}
								
				probability_per_cluster = exponent / probability_denominator;
				
				if (Float.isNaN(probability_per_cluster)){
					probability_per_cluster = 0.0f;
				}
				
				probability_cluster.put(i, probability_per_cluster);				
				
			}
			
			datanode.probablity_distributed.put(data_value, probability_cluster);
		
		}
	
	}
	
	public static void estimation_step(EM_DataNode datanode, int number_cluster){
		
		double alpha_key = 0.0;
		
		float weight_numerator = 0.0f;
		
		float weight_denominator = 0.0f;
		
		float weight_fraction = 0.0f;
		
		for (double key: datanode.probablity_distributed.keySet()){
			
			Map<Integer, Float> probability_cluster = new HashMap<Integer, Float>();
			Map<Integer, Float> weight_cluster = new HashMap<Integer, Float>();
			
			probability_cluster = datanode.probablity_distributed.get(key);
			
			for (int subset: probability_cluster.keySet()){
				
				alpha_key = datanode.alpha_values.get(subset);
				weight_numerator = (float) (alpha_key * probability_cluster.get(subset));
				weight_denominator = weight_denominator + weight_numerator;
				
			}
			
			for ( int subset_key: probability_cluster.keySet()){
				
				alpha_key = datanode.alpha_values.get(subset_key);
				weight_numerator = (float) (alpha_key * probability_cluster.get(subset_key));				
				weight_fraction = weight_numerator / weight_denominator;
				weight_cluster.put(subset_key, weight_fraction);
				
			}
			
			datanode.weight_distributed.put(key, weight_cluster);
			
			weight_denominator = 0.0f;
			
		}
	
	}
	
	public static void maximization_step(EM_DataNode datanode, int number_cluster){
		
		double data_single = 0.0;
		double add_weight = 0.0;
		float weighted_mean = 0.0f;
		float variance_dataset = 0.0f;
		
		for (int i = 0; i < number_cluster; i++){
			
			for (int key: datanode.dataset_values.keySet()){
				
				Map<Integer, Float> weight_cluster = new HashMap<Integer, Float>();
				data_single = datanode.dataset_values.get(key);
				weight_cluster = datanode.weight_distributed.get(data_single);
				add_weight = add_weight + weight_cluster.get(i);
				weighted_mean =  weighted_mean + (float)(weight_cluster.get(i) * data_single);
				
			}
			
			add_weight = add_weight / datanode.dataset_values.size();
			weighted_mean = weighted_mean / datanode.dataset_values.size();
			
			datanode.alpha_values.put(i, add_weight);
			datanode.mean_cluster.put(i, (double) weighted_mean);
				
			for (int key: datanode.dataset_values.keySet()){
					
					Map<Integer, Float> weight_cluster = new HashMap<Integer, Float>();
					data_single = datanode.dataset_values.get(key);
					weight_cluster = datanode.weight_distributed.get(data_single);
					variance_dataset =  variance_dataset + (float)((weight_cluster.get(i) * (data_single - weighted_mean) * (data_single - weighted_mean))) ;
					
				}
				
			variance_dataset = variance_dataset / datanode.dataset_values.size();
			datanode.variance_cluster.put(i, (double) variance_dataset);
			
			
			
			add_weight = 0.0;
			weighted_mean = 0.0f;
			variance_dataset = 0.0f;
			
		}
	}
	
	public static void convergence(EM_DataNode datanode, int number_cluster, last_class last_class){
		
		probability_gaussian(datanode, number_cluster);
		
		Map<Integer, Float> probability_cluster = new HashMap<Integer, Float>();
		
		float cumulative_probability = 0.0f;
		
		float convergence_dataset = 0.0f;
		
		double data_value = 0.0;
		
		for (int key: datanode.dataset_values.keySet()){
			
			data_value = datanode.dataset_values.get(key); 
					
			probability_cluster = datanode.probablity_distributed.get(data_value);
			
			for (int i = 0 ; i < number_cluster; i++){
				
				cumulative_probability = cumulative_probability + (float)(datanode.alpha_values.get(i) * probability_cluster.get(i));
			
			}
			
			if ( cumulative_probability != 0.0f){
				cumulative_probability = (float) Math.log(cumulative_probability);
			}			
			
			convergence_dataset = convergence_dataset + cumulative_probability;
			
			cumulative_probability = 0.0f;
			
		}
		
		System.out.println(+convergence_dataset);
				
		if ( last_class.previous_convergence == 0.0f){
			
			last_class.previous_convergence = convergence_dataset;
			
			last_class.bool = false;
			
		} else {
			
			if (last_class.previous_convergence == convergence_dataset){
				
				 System.out.println("\n");
				 System.out.println("The value of convergence is: "+convergence_dataset);
				
				last_class.bool = true;
			
			} else {
				
				last_class.previous_convergence = convergence_dataset;
				
				last_class.bool = false;
				
			}
			
		}
				
	}
}
