package tk.hugo4715.learn4j.classifier.knn;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tk.hugo4715.learn4j.classifier.Classifier;
import tk.hugo4715.learn4j.label.Label;
import tk.hugo4715.learn4j.util.Pair;

/**
 * This classifier is implementing the K-nearest neighbors algorithm. <br/> 
 * When searching a point label, the classifier finds the k closest points in the reference set (provided in the config) and assign a probability to each label based on how many neighbors had that label 
 * 
 * @param <T> The label type 
 * 
 * @see
 * <a href=https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm>Wikipedia Page <br/>
 * {@link KNearestClassifierConfig}
 */
@Data
@AllArgsConstructor
public class KNearestClassifier<T> implements Classifier<T> {

	private KNearestClassifierConfig<T> config;

	@Override
	public Map<Label<T>, Double> classify(double[] point) {

		ClosestPointStorage storage = this.new ClosestPointStorage(point);
		for(double[] p : config.getDataset().getContent())storage.compute(p);

		//we got the k closest points, count the labels
		Map<Label<T>,Double> labels = new HashMap<>();
		for(double[] p : storage.getClosest().keySet()){
			Label<T> l = config.getDataset().getLabels().get(config.getDataset().getContent().indexOf(p));//get the label of the point
			Double i = labels.get(l);
			if(i == null)i = new Double(0);
			i += 1d / config.getK();
			labels.put(l, i);
		}
		
		return labels;
	}


	/**
	 * This classifier is fuzzy <br/>
	 * Probability of a label = number of time the label appeared in the neighborhood divided by k
	 */
	@Override
	public boolean isFuzzy() {
		return true;
	}


	/**
	 * This class hold the k-nearest points 
	 */
	private class ClosestPointStorage{
		@Getter private Map<double[],Double> closest = new HashMap<>();
		private double[] point;
		
		public ClosestPointStorage(double[] point) {
			this.point = point;
		}


		/**
		 * Check a point, if the distance is lower than the current points or if we do not yet have k points we add the point to the dataset
		 * @param check The point to compute
		 */
		public void compute(double[] check){
			//check the distance
			double distance = config.getDistance().measure(point, check);
			
			//get the farthest point in the set
			Pair<double[],Double> farthest = getFarthest();
		
			//if we should use the new point 
			if(farthest.getLeft() == null || distance < farthest.getRight()){
				if(closest.size() > config.getK())closest.remove(farthest.getLeft());//remove the farthest if we already have at least k points
				closest.put(check, distance);//add the new point
			}
		}
		
		
		/**
		 * Return the farthest point in the current computed points
		 * @return A pair in the form (point,distance)
		 */
		protected Pair<double[],Double> getFarthest(){
			double[] farthest = null;
			double maxDist = 0;

			for(Entry<double[],Double> en : closest.entrySet()){
				double dist = config.getDistance().measure(point, en.getKey());
				if(maxDist < dist){
					farthest = en.getKey();
					maxDist = dist;
				}
			}
			return new Pair<double[],Double>(farthest,maxDist);
		}
	}
}
