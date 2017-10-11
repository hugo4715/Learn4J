package tk.hugo4715.learn4j.clusterer.dbscan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.hugo4715.learn4j.clusterer.Clusterer;
import tk.hugo4715.learn4j.data.Dataset;

/**
 * Work in progress
 * This class implements the highly popular Density-based spatial clustering of applications with noise (DBSCAN) clustering method <br/>
 * @see
 * <a href=https://en.wikipedia.org/wiki/DBSCAN>Wikipedia page<br/>
 * {@link DBSCANClustererConfig}
 */
@AllArgsConstructor
public class DBSCANClusterer implements Clusterer {
	
	@Getter @Setter private DBSCANClustererConfig config;
	
	
	@Override
	public Dataset[] cluster(Dataset set) {
		int c = 0;//cluster counter
		Map<double[],Integer> categories = new HashMap<>();
		
		for(double[] point : set.getContent()){
			if(categories.containsKey(point))continue;
			Set<double[]> neighbors = findNeighbors(set, point);
			if(neighbors.size() < config.getMinDensity()){
				categories.put(point, 0);//label 0 is noise
				continue;
			}
			c++;//next cluster
			categories.put(point, c);
			
			neighbors.remove(point);//remove the point

			//we are using a queue because we can't modify a list while looping through it
			Queue<double[]> queue = new LinkedBlockingQueue<>(neighbors);
			while(!queue.isEmpty()){
				double[] n = queue.poll();
				if(categories.containsKey(n) && categories.get(n) == 0)categories.put(n, c);
				if(categories.containsKey(n))continue;
				categories.put(n, c);
				
				Set<double[]> newNeighbors = findNeighbors(set, n);
				
				if(newNeighbors.size() >= config.getMinDensity())queue.addAll(newNeighbors);
			}
		}
		
		
		//create the dataset
		Dataset[] result = new Dataset[c+1];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Dataset();
		}
		for(Entry<double[],Integer> entry : categories.entrySet()){
			result[entry.getValue()].addEntry(entry.getKey());
			
		}
		return result;
	}
	
	/**
	 * find the neighbors of a point (including the point itself)
	 * @param set The dataset
	 * @param point The point 
	 * @return A set of neighbors
	 */
	protected Set<double[]> findNeighbors(Dataset set, double[] point){
		Set<double[]> points = new HashSet<>();
		for(double[] i : set.getContent()){
			if(config.getDistance().measure(point, i) < config.getMinDistance())points.add(i);
		}
		return points;
	}

}
