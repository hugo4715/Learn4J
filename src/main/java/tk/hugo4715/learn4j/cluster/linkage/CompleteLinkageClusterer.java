package tk.hugo4715.learn4j.cluster.linkage;

import java.util.Set;

public class CompleteLinkageClusterer extends SingleLinkageClusterer {

	public CompleteLinkageClusterer(LinkageClustererConfig config) {
		super(config);
	}

	@Override
	protected double minDistance(Set<double[]> set1, Set<double[]> set2) {
		double min = Double.MIN_VALUE;

		for(double[] a : set1){
			for(double[] b : set2){
				double dist = config.getDistance().measure(a, b);
				if(dist > min)min = dist;
			}
		}

		return min;
	}

}
