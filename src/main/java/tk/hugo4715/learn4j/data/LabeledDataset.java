package tk.hugo4715.learn4j.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tk.hugo4715.learn4j.label.Label;

public class LabeledDataset extends Dataset {
	protected List<Label> labels;
	
	public LabeledDataset(Map<double[],Label> set) {
		super();
		labels = new ArrayList<>();
		set.entrySet().forEach(e -> {
			this.storage.add(e.getKey());
			labels.add(e.getValue());
		});
	}
	
	public List<Label> getLabels() {
		return labels;
	}
}
