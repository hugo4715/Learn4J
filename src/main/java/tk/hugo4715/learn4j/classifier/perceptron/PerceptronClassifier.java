package tk.hugo4715.learn4j.classifier.perceptron;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import tk.hugo4715.learn4j.classifier.Classifier;
import tk.hugo4715.learn4j.data.LabeledDataset;
import tk.hugo4715.learn4j.label.Label;
import tk.hugo4715.learn4j.util.Pair;
import tk.hugo4715.learn4j.util.Utils;

/**
 * This classifier is a Perceptron Classifier, linear binary classifier: <br/>
 *   - binary => it can only be used with 2 labels<br/>
 *   - linear => series are separated with a line
 * @param <T> The label type 
 * 
 * @see
 * <a href=https://en.wikipedia.org/wiki/Perceptron>Wikipedia Page<br/>
 */
public class PerceptronClassifier<T> implements Classifier<T> {

	private double[] featureVector;//the weight of the feature vector
	Label<T>[] labels = new Label[2];//only 2 labels possible
	
	public PerceptronClassifier(LabeledDataset<T> set) {
		//fill the available labels and check arguments
		for(Label<T> l : set.getLabels()){
			if(labels[0] == null)labels[0] = l;
			else if(!labels[0].equals(l) && labels[1] == null)labels[1] = l;
		}
		if(labels[0] == null && labels[1] == null)throw new IllegalArgumentException("PerceptronClassifier cannot work with no labels!");

		//prepare inputs
		Queue<Pair<double[],Double>> inputs = new LinkedList<>();//storage for the inputs
		
		int f = 0;
		//change set structure to add the bias at index 0 and add label index
		for(double[] point : set.getContent()){
			double[] newPoint = new double[point.length+1];
			System.arraycopy(point, 0, newPoint, 1, point.length);
			newPoint[0] = 1;//used for the bias
			inputs.add(new Pair<double[],Double>(newPoint, set.getLabels().get(f).equals(labels[0]) ? 0d : 1d));
			f++;
		}
		
		//create feature list initialized with 0
		featureVector = new double[set.getContent().get(0).length+1];
		for (int i = 0; i < featureVector.length; i++)featureVector[i] = 0;
		
		//compute all elements of the set and update weights
		while(!inputs.isEmpty()){
			Pair<double[],Double> input = inputs.poll();
			
			//compute expected result
			double expected = input.getRight();
			
			//compute real result
			double result = 0;
			for (int i = 0; i < input.getLeft().length; i++) {
				result+= input.getLeft()[i] * featureVector[i];
			}
			result = result < 0.5 ? 0d : 1d;
			
			//update weights
			for (int i = 0; i < featureVector.length; i++) {
				double delta = (expected - result) * input.getLeft()[i];
				featureVector[i] += delta;  
			}
			
		}
	}
	
	
	/**
	 * compute the result of a point values 
	 * @param point The point
	 * @return The function output for that point
	 */
	protected double compute(double[] point){
		//change point structure to add bias at index 0
		double[] newPoint = new double[point.length+1];
		newPoint[0] = 1;
		System.arraycopy(point, 0, newPoint, 1, point.length);
		
		//compute function
		double total = 0;
		for (int i = 0; i < newPoint.length; i++) {
			total += newPoint[i] * featureVector[i];
		}
		return total;
	}
	
	@Override
	public Map<Label<T>, Double> classify(double[] point) {
		Map<Label<T>,Double> map = new HashMap<>();
		double result = compute(point);
		map.put(labels[0], result < 0.5 ? 1d : 0d);
		map.put(labels[1], result < 0.5 ? 0d : 1d);
		return map;
	}

	@Override
	public boolean isFuzzy() {
		return false;
	}

}
