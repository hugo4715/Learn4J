package tk.hugo4715.learn4j.classifier.knn;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.data.LabeledDataset;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class OneNearestClassifierConfig<T> {
	private LabeledDataset<T> dataset;
	private Distance distance;
}
