package tk.hugo4715.learn4j.cluster.dbscan;

import lombok.Builder;
import lombok.Data;
import tk.hugo4715.learn4j.distance.Distance;

@Builder
@Data
public class DBSCANClustererConfig {
	private Distance distance;
	private double mindistance;
}
