package tk.hugo4715.learn4j.clusterer.kmean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.hugo4715.learn4j.clusterer.Clusterer;
import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.util.Utils;

/**
 * This is a KMean clusterer implementing the Lloyd's algorithm
 * If you want a better algorithm, you may want to take a look at {@link KMeansPlusPlusClusterer} <br/>
 * 
 * Using a distance function other than (squared) Euclidean distance may stop the algorithm from converging
 * @see
 * <a href=https://en.wikipedia.org/wiki/K-means_clustering>Wikipedia page <br/>
 * {@link KMeansClustererConfig}
 */
@AllArgsConstructor
public class KMeansClusterer implements Clusterer {
	
	@Getter @Setter protected KMeansClustererConfig config;
	
	
	/**
	 * Get the best centroids to classify this dataset
	 * @param set A dataset
	 * @return A list of centroids
	 */
	public List<double[]> getBestCentroids(Dataset set){
		List<double[]> clusters = getStartingCentroids(set);
		
		AtomicBoolean done = new AtomicBoolean(false);//true if centroid did not move in the last step
		
		while(!done.get()){
			
			//Assignment step
			Map<double[],List<double[]>> centroids = new HashMap<>();
			clusters.forEach(c -> centroids.put(c, new ArrayList<>()));
			
			//add each point to the closest cluster 
			set.getContent().forEach(point -> {
				centroids.get(Utils.getClosestPoint(point, centroids.keySet(), config.getDistance())).add(point);
			});
			
			//Update step
			clusters.clear();
			done.set(true);//set done to true, but if a point move reset it back to false
			
			centroids.forEach((double[] c, List<double[]> points) -> {
				double[] newCentroid = Utils.createCentroid(points);
				clusters.add(newCentroid);
				
				if(!Utils.isSamePoints(c, newCentroid))done.set(false);
			});
			
		}
		return clusters;
	}
	
	/**
	 * This method exists so it can be inherited (for example KMeans++ just needs to change overwrite this)
	 * @param set The dataset
	 * @return The default centroids
	 */
	protected List<double[]> getStartingCentroids(Dataset set){
		List<double[]> clusters = new ArrayList<>();

		//create n random cluster using existing data points
		Random rand = new Random();
		for(int i = 0; i < config.getClusterAmount();i++){
			double[] selected = null;
			//ensure we don't select the same point twice
			while(selected == null || clusters.contains(selected))selected = set.getContent().get(rand.nextInt(set.getContent().size()));
			clusters.add(selected);
		}
		return clusters;
	}
	
	/**
	 * Cluster the given dataset using the given centroids
	 * @param set The dataset
	 * @param centroidList A list of centroids
	 * @return The clustered datasets
	 */
	public Dataset[] cluster(Dataset set,List<double[]> centroidList){
		List<double[]> clusters = centroidList;
		
		//build resulting sets
		Dataset[] result = new Dataset[clusters.size()];
		
		Map<double[],List<double[]>> centroids = new HashMap<>();
		clusters.forEach(c -> centroids.put(c, new ArrayList<>()));
		set.getContent().forEach(point -> {
			centroids.get(Utils.getClosestPoint(point, centroids.keySet(), config.getDistance())).add(point);
		});
		
		int i = 0;
		for(List<double[]> l : centroids.values()){
			result[i] = new Dataset(l);
			i++;
		}
		return result;
		
	}
	
	@Override
	public Dataset[] cluster(Dataset set) {
		return cluster(set, getBestCentroids(set));
	}
	
	
	
}
