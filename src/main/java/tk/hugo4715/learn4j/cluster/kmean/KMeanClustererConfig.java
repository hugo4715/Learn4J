package tk.hugo4715.learn4j.cluster.kmean;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class KMeanClustererConfig {
	private Distance distance;
	private int clusterAmount;
}
