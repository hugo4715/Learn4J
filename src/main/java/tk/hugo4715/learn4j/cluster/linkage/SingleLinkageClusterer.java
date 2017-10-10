package tk.hugo4715.learn4j.cluster.linkage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.hugo4715.learn4j.cluster.Clusterer;
import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.util.Pair;

/**
 * @see <a href=https://en.wikipedia.org/wiki/Single-linkage_clustering> Wikipedia page
 */
@AllArgsConstructor
public class SingleLinkageClusterer implements Clusterer {
	
	@Getter @Setter protected LinkageClustererConfig config;
	
	@Override
	public Dataset[] cluster(Dataset set) {
		Set<Set<double[]>> clusters = new HashSet<>();
		
		//create 1 cluster per point
		set.getContent().forEach(array -> clusters.add(new HashSet<double[]>(){{
			add(array);
		}}));
		
		
		while(clusters.size() > config.getClusterAmount()){
			//measure all distances and combine closest clusters
			Map<Pair<Set<double[]>,Set<double[]>>,Double> closest = new HashMap<>();// (set,set) -> distance
			
			//for each cluster, find the closest one
			for(Set<double[]> cluster : clusters){
				double min = Double.MAX_VALUE;
				Set<double[]> minSet = null;
				//iterate over all other clusters and measure
				for(Set<double[]> other : clusters){
					if(other.equals(cluster))continue;//skip the one we are currently measuring
					
					double dist = minDistance(cluster, other);
					if(dist < min){
						min = dist;
						minSet = other;
					}
				}
				
				closest.put(new Pair<Set<double[]>, Set<double[]>>(cluster, minSet), min);//we found the closest cluster, store it
			}

			//find 2 closest clusters
			Pair<Set<double[]>, Set<double[]>> closestClusters = closest.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).get(0).getKey();

			//remove the clusters
			clusters.remove(closestClusters.getLeft());
			clusters.remove(closestClusters.getRight());
			
			//add the resulting cluster
			Set<double[]> newCluster = closestClusters.getLeft();
			newCluster.addAll(closestClusters.getRight());
			clusters.add(newCluster);
		}
		
		//build the resulting dataset
		Dataset[] result = new Dataset[clusters.size()];
		int i = 0;
		for (Set<double[]> c : clusters) {
			result[i] = new Dataset(c);
			i++;
		}
		return result;
	}
	
	/**
	 * Return the minimum distance between the two sets
	 * @param set1
	 * @param set2
	 * @return
	 */
	protected double minDistance(Set<double[]> set1, Set<double[]> set2){
		double min = Double.MAX_VALUE;
		
		for(double[] a : set1){
			for(double[] b : set2){
				double dist = config.getDistance().measure(a, b);
				if(dist < min)min = dist;
				if(dist == 0)return dist;//we can't find a distance less than 0, so it's useless to continue
			}
		}
		
		return min;
	}

}
