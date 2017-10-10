package tk.hugo4715.learn4j.cluster.dbscan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tk.hugo4715.learn4j.cluster.Clusterer;
import tk.hugo4715.learn4j.data.Dataset;

/**
 * Work in progress
 * This class implements the highly popular Density-based spatial clustering of applications with noise (DBSCAN) clustering method <br/>
 * @see
 * <a href=https://en.wikipedia.org/wiki/DBSCAN>Wikipedia page<br/>
 * {@link DBSCANClustererConfig}
 */
@AllArgsConstructor
public class DBSCANClusterer implements Clusterer {
	
	@Getter @Setter DBSCANClustererConfig config;
	
	
	@Override
	public Dataset[] cluster(Dataset set) {
		// TODO 
		return null;
	}

}
