package tk.hugo4715.learn4j.cluster.kmean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.util.Utils;
import tk.hugo4715.learn4j.util.WeightedDistribution;
import tk.hugo4715.learn4j.util.WeightedDistribution.WeightedItem;

/**
 * This clusterer is a subclass of KMeansClusterer. It is globally better because it chooses the starting centroids differently
 * @see
 * <a href=https://en.wikipedia.org/wiki/K-means%2B%2B>Wikipedia page <br/>
 * {@link KMeansClusterer} <br/>
 * {@link KMeansClustererConfig}
 */
public class KMeansPlusPlusClusterer extends KMeansClusterer {

	public KMeansPlusPlusClusterer(KMeansClustererConfig config) {
		super(config);
	}
	
	
	@Override
	protected List<double[]> getStartingCentroids(Dataset set) {
		Random rand = new Random();
		
		List<double[]> centroids = new ArrayList<>();
		
		//first centroids is choosen at random in the set
		centroids.add(set.getContent().get(rand.nextInt(set.getContent().size())));
		
		while(centroids.size() < config.getClusterAmount()){
			WeightedDistribution<double[]> distrib = new WeightedDistribution<>();
			
			set.getContent().forEach(point -> {
				//for each point in the set, add it to the distrib with his probability  
				double[] closestCentroid = Utils.getClosestPoint(point, Utils.setFromList(centroids), config.getDistance());
				double distSquared = Math.pow(config.getDistance().measure(point, closestCentroid), 2);//probability is dist squared
				distrib.getItems().add(new WeightedItem<double[]>(distSquared, point));
			});
			
			centroids.add(distrib.nextItem());
		}
		return centroids;
	}
}
