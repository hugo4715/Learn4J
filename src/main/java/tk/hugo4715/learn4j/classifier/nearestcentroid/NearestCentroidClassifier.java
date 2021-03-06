package tk.hugo4715.learn4j.classifier.nearestcentroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;
import tk.hugo4715.learn4j.classifier.Classifier;
import tk.hugo4715.learn4j.label.Label;
import tk.hugo4715.learn4j.util.Utils;

/**
 * This classifier uses a k-means to generate the centroids on a training sets, then when it needs to classify a point, it assign labels based on the point distance to the centroids
 * @param <T> The label types
 * 
 * @see
 * {@link NearestCentroidClassifierConfig}
 */
@Data
public class NearestCentroidClassifier<T> implements Classifier<T> {


	private NearestCentroidClassifierConfig<T> config;

	private Map<Label<T>,double[]> centroids;
	
	public NearestCentroidClassifier(NearestCentroidClassifierConfig<T> config){
		this.config = config;
	}
	
	public void rebuildCentroids(){
		Map<Label<T>,List<double[]>> labels = new HashMap<>();
		
		
		Iterator<Label<T>> il = config.getDataset().getLabels().iterator();
		Iterator<double[]> iv = config.getDataset().getContent().iterator();
		
		while(il.hasNext() && iv.hasNext()){
			Label<T> label = il.next();
			double[] point = iv.next();
			
			if(!labels.containsKey(label))labels.put(label, new ArrayList<>());
			labels.get(label).add(point);
		}
		
		
		centroids = new HashMap<>();
		for(Entry<Label<T>,List<double[]>> en : labels.entrySet())centroids.put(en.getKey(), Utils.createCentroid(en.getValue()));
		
	}

	@Override
	public Map<Label<T>, Double> classify(double[] point) {
		//assign the labels probabilities based on the distance to the centroids
		
		Map<Label<T>,Double> map = new HashMap<>();
		
		double greatest = 0;
		for(Entry<Label<T>,double[]> en : centroids.entrySet()){
			double dist = config.getDistance().measure(en.getValue(), point);
			if(greatest < dist)greatest = dist;
			map.put(en.getKey(), dist);
		}
		
		Map<Label<T>,Double> result = new HashMap<>();
		for(Entry<Label<T>,Double> en : map.entrySet()){
			result.put(en.getKey(), 1 / (en.getValue() / greatest));
		}
		
		
		return result;
	}

	/**
	 * This classifier is fuzzy 
	 * Labels depends on the distance to the centroids
	 */
	@Override
	public boolean isFuzzy() {
		return true;
	}

}
