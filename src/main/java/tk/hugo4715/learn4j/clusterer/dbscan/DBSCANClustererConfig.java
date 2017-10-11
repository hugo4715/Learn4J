package tk.hugo4715.learn4j.clusterer.dbscan;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class DBSCANClustererConfig {
	private Distance distance;
	private double minDistance;
	private double minDensity;
}
