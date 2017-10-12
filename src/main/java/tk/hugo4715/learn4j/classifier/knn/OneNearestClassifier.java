package tk.hugo4715.learn4j.classifier.knn;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.hugo4715.learn4j.classifier.Classifier;
import tk.hugo4715.learn4j.label.Label;

@AllArgsConstructor
public class OneNearestClassifier<T> implements Classifier<T> {

	@Setter @Getter private OneNearestClassifierConfig<T> config;
	
	
	@Override
	public Map<Label<T>, Double> classify(double[] point) {
		//find the closest point, and assign the same label to the new point
		
		double min = Double.MAX_VALUE;
		double[] best = null;
		Label<T> bestLabel = null;
		
		int i = 0;
		for(double[] d : config.getDataset().getContent()){
			if(d != point){
				double dist = config.getDistance().measure(point, d);
				
				if(dist < min){
					min = dist;
					best = d;
					bestLabel = config.getDataset().getLabels().get(i); 
				}
			}
			i++;
		}
		
		Map<Label<T>,Double> map = new HashMap<>();
		map.put(bestLabel, 1d);
		return map;
	}
	


	@Override
	public boolean isFuzzy() {
		return true;
	}
}
