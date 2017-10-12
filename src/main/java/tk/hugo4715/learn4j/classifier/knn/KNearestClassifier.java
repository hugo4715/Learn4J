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



	@Override
	public boolean isFuzzy() {
		return true;
	}


	private class ClosestPointStorage{
		@Getter private Map<double[],Double> closest = new HashMap<>();
		private double[] point;
		
		public ClosestPointStorage(double[] point) {
			this.point = point;
		}


		public void compute(double[] check){
			//check the distance
			double distance = config.getDistance().measure(point, check);
			
			//get the farthest point in the set
			Pair<double[],Double> farthest = getFarthest();
		
			
			if(farthest.getLeft() == null || distance < farthest.getRight()){
				//replace the farthest point with the new point
				if(closest.size() > config.getK())closest.remove(farthest.getLeft());
				closest.put(check, distance);
			}
		}
		
		
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
