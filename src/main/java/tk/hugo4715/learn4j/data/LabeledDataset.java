package tk.hugo4715.learn4j.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.hugo4715.learn4j.label.Label;

/**
 * Represent a dataset with labels
 * @see 
 * {@link Dataset}
 */
public class LabeledDataset<T> extends Dataset {
	protected List<Label<T>> labels;
	
	public LabeledDataset() {
		this(new HashMap<>());
	}
	
	public LabeledDataset(Map<double[],Label<T>> set) {
		super();
		labels = new ArrayList<>();
		set.entrySet().forEach(e -> {
			this.storage.add(e.getKey());
			labels.add(e.getValue());
		});
	}
	
	public List<Label<T>> getLabels() {
		return labels;
	}
}
