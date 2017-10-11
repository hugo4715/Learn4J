package tk.hugo4715.learn4j.clusterer.kmean;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class KMeansClustererConfig {
	private Distance distance;
	private int clusterAmount;
}
