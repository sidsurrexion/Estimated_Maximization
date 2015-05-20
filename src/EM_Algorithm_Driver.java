import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class EM_Algorithm_Driver {

		@SuppressWarnings("resource")
		public static EM_DataNode FileReader(int k, int option) throws IOException{
			EM_DataNode datanode = new EM_DataNode();
			String file_directory = new File("").getAbsolutePath();
			String text_ext = "txt";
			
			BufferedReader reader = null;
		    FileInputStream fis = null;
		    
		    File file = null;
			file = new File(file_directory);
			
		    String[] paths = file.list();
		    String extension = null;
		    String line = null;
		    
		    int edge = 0;
		    int counter = 0;
		    
		    double record_entry = 0.0;		    
		    double variance_dataset = 0.0;
		    double variance_record = 0.0;
		    
		    double weight = 0.0;
		    		    
		    for (String path: paths){
		    	
		    	extension = path.substring(path.lastIndexOf(".") + 1, path.length());
		    	
		    	if (extension.equals(text_ext)){
		    		
		    		fis = new FileInputStream(file_directory + "//" + path);
					
					reader = new BufferedReader(new InputStreamReader(fis));
					
					if (option == 1 || option == 3){
						
						while ((line = reader.readLine()) != null){
							weight = weight + Double.parseDouble(line);
							datanode.dataset_values.put(counter,	Double.parseDouble(line));
							counter++;
						}
						
						weight = weight / counter;
											
						if (option == 1){
							
							for (int key: datanode.dataset_values.keySet()){
								record_entry = datanode.dataset_values.get(key);
								variance_dataset = variance_dataset + ((record_entry - weight) * (record_entry - weight));
							}
							
							variance_dataset = variance_dataset / counter ;
							
						}										
						
						weight = 0.0;
						
						System.out.println("The values for Mean and Variance are as under: ");
						
						for (int i = 0; i < k; i++){
							edge = (int) (Math.random() * (counter - 1));
							weight = weight + datanode.dataset_values.get(edge);
							datanode.mean_cluster.put(i, datanode.dataset_values.get(edge));
							System.out.print(datanode.dataset_values.get(edge)+" ");
							
							if (option == 1){
								variance_record = (double)(Math.random() * 0.9) * variance_dataset;
								datanode.variance_cluster.put(i, variance_record);
								System.out.print(variance_record);
								System.out.println("");
							}
							else {
								datanode.variance_cluster.put(i, 1.0);
								System.out.print("1.0");
								System.out.println("");
							}
						}
						
						System.out.println("");
						System.out.println("Values for Alpha:");
						
						for (int new_key: datanode.mean_cluster.keySet()){
							datanode.alpha_values.put(new_key, (datanode.mean_cluster.get(new_key) / weight));
							System.out.println(+(datanode.mean_cluster.get(new_key) / weight));
						}
						
						System.out.println("");
						System.out.println("The log likelihood values are as under:");
					} 
					
					else if (option == 2 || option == 4){
						
						String connect = "edge";
						int count = 0;
						int index = 0;
						int add_counter = 0;
						double cumulative_weight = 0.0;
						
						
						Map<Integer, Double> temp_weights = new HashMap<Integer, Double>();
						Map<Integer, String> random_weights = new TreeMap<Integer, String>();
						
						while ((line = reader.readLine()) != null){
							datanode.dataset_values.put(counter,Double.parseDouble(line));
							counter++;
						}
						
						for ( int j = 0 ; j < k ; j++){							
							edge = (int) (Math.random() * (counter - 1));
							weight = weight + datanode.dataset_values.get(edge);
							random_weights.put(edge, (connect + String.valueOf(edge)));
							temp_weights.put(edge, datanode.dataset_values.get(edge));						
						}						
											
						System.out.println("The values for Mean and Variance are as under: ");
						
						for (int implement: random_weights.keySet()){
							
							Map<Integer, Double> dataset_subset = new HashMap<Integer, Double>();
							
							if ( (index + 1) != random_weights.size()){
								
								for ( int execute =  count; execute < implement; execute++){
									weight = weight + datanode.dataset_values.get(execute);
									dataset_subset.put(execute, datanode.dataset_values.get(execute));
									add_counter++;
								}		
								
								count = implement;
								
							} else {
								
								for (int execute = implement; execute < counter; execute++){									
									weight = weight + datanode.dataset_values.get(execute);
									dataset_subset.put(execute, datanode.dataset_values.get(execute));
									add_counter++;
								}
								
							}
						
														
							weight = weight / add_counter;							
							
							System.out.print(weight+" ");
							
							datanode.mean_cluster.put(index, weight);
							
							cumulative_weight = cumulative_weight + weight;
							
							if (option == 2){
								
								for ( int subset_key: dataset_subset.keySet()){
									record_entry = dataset_subset.get(subset_key);
									variance_dataset = variance_dataset + ((record_entry - weight) * (record_entry - weight));
								}
								
								variance_dataset = variance_dataset / add_counter;
								
								System.out.print(variance_dataset+" ");
								System.out.println("");
								datanode.variance_cluster.put(index, variance_dataset);
								
							}
							
							else {
								System.out.print("1.0"+" ");
								datanode.variance_cluster.put(index, 1.0);
							}						
							
							
							weight = 0.0;
							
							add_counter = 0;
							
							record_entry = 0.0;
							
							variance_dataset = 0.0;
							
							index++;
							
						}
						
						System.out.println("");
						System.out.println("The Values for Alphas are as under: ");
						for (int new_key: datanode.mean_cluster.keySet()){
							System.out.println(datanode.mean_cluster.get(new_key) / cumulative_weight);
							datanode.alpha_values.put(new_key, (datanode.mean_cluster.get(new_key) / cumulative_weight));
						}
						
						System.out.println("");
						System.out.println("The log likelihood values are as under:");
						
					}
					
					if (option == 5){
						
						while ((line = reader.readLine()) != null){												
							
							datanode.dataset_values.put(counter, Double.parseDouble(line));			
							
							counter++;
							
						}
						
						double average_weight = 0.0;
						
						for ( int key: datanode.dataset_values.keySet()){
							
							Map<Integer, Double> random_weights = new HashMap<Integer, Double>();
							
							Map<Integer, Float> random_weight_distribution = new HashMap<Integer, Float>();
							
							for (int i = 0; i < k; i++){
								
								edge = (int) (Math.random() * (counter - 1));
								
								weight = weight + datanode.dataset_values.get(edge);
								
								random_weights.put(i, datanode.dataset_values.get(edge));
							
							}
							
							for (int subkey: random_weights.keySet()){
								
								average_weight = random_weights.get(subkey) / weight;
								
								random_weight_distribution.put(subkey, (float) average_weight);
								
							}
							
							datanode.weight_distributed.put(datanode.dataset_values.get(key), random_weight_distribution);
							
							weight = 0.0;
							
						}
						
						System.out.println("No initialization of parameters");						
						System.out.println("");
						System.out.println("The log likelihood values are as under:");
					}
					
					
		    	}
		    }
		    
		    return datanode;
		}
}
