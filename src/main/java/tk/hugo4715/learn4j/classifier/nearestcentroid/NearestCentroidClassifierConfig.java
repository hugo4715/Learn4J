package tk.hugo4715.learn4j.classifier.nearestcentroid;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.data.LabeledDataset;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class NearestCentroidClassifierConfig<T> {
	private Distance distance;
	private LabeledDataset<T> dataset;
}
