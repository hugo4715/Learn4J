package tk.hugo4715.learn4j.classifier;

import java.util.Map;

import tk.hugo4715.learn4j.label.Label;

public interface Classifier<T> {
	
	/**
	 * Classify a new point using this classifier
	 * @param point The point
	 * @return A map of labels, with the associated probabilities 
	 * If the classifier is not fuzzy, the map values are only 1 and 0
	 * There is no obligation to return label with a probability of 0
	 */
	public Map<Label<T>,Float> classify(double[] point);
	
	/**
	 * Check if the classifier is fuzzy
	 * A fuzzy classifier is a classifier that shows probability associated with the label it choose
	 * @return True if fuzzy
	 */
	public boolean isFuzzy();
}
