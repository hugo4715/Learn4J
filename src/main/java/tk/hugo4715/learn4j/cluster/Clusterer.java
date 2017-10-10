package tk.hugo4715.learn4j.cluster;

import tk.hugo4715.learn4j.data.Dataset;

public interface Clusterer {
	
	/**
	 * Execute the clusterer
	 * @param set The data to cluster
	 * @return An array of smaller clusters
	 */
	public Dataset[] cluster(Dataset set);
}
