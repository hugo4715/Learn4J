package tk.hugo4715.learn4j.clusterer.linkage;

import java.util.Set;

/**
 * The CompleteLinkageClusterer is a subclass of the SingleLinkageClusterer and can be used in exactly the same way 
 * The only difference with his parent is the way the distance is calculated
 * @see 
 * <a href=https://en.wikipedia.org/wiki/Complete_linkage_clustering>Wikipedia page <br/>
 * {@link SingleLinkageClusterer}
 */
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
