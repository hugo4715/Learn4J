package tk.hugo4715.learn4j.classifier;

import java.util.Map;

import tk.hugo4715.learn4j.label.Label;

public interface Classifier<T> {
	
	/**
	 * Usefull method if you only care about the most probable result
	 * @param point The point
	 * @return The most probable label
	 */
	public default Label<T> mostProbable(double[] point) {
		return classify(point).entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().get().getKey();
	}

	/**
	 * Classify a new point using this classifier
	 * @param point The point
	 * @return A map of labels, with the associated probabilities 
	 * If the classifier is not fuzzy, the map values are only 1 and 0
	 * There is no obligation to return label with a probability of 0
	 */
	public Map<Label<T>,Double> classify(double[] point);
	
	/**
	 * Check if the classifier is fuzzy
	 * A fuzzy classifier is a classifier that shows probability associated with the label it choose
	 * @return True if fuzzy
	 */
	public boolean isFuzzy();
}
