import java.util.HashMap;
import java.util.Map;


public class EM_DataNode {
	Map<Integer, Double> alpha_values = new HashMap<Integer, Double>();
	Map<Integer, Double> dataset_values = new HashMap<Integer, Double>();
	Map<Integer, Double> variance_cluster = new HashMap<Integer, Double>();
	Map<Integer, Double> mean_cluster = new HashMap<Integer, Double>();
	Map<Double, Map<Integer, Float>> probablity_distributed = new HashMap<Double, Map<Integer, Float>>();
	Map<Double, Map<Integer, Float>> weight_distributed = new HashMap<Double, Map<Integer, Float>>();
}
