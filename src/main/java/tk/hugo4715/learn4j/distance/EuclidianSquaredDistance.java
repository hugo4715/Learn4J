package tk.hugo4715.learn4j.distance;

import tk.hugo4715.learn4j.util.ArrayUtils;

public class EuclidianSquaredDistance implements Distance {

	@Override
	public double measure(double[] pointA, double[] pointB) {
		double[] deltas = new double[pointA.length];
		for (int i = 0; i < deltas.length; i++) {
			deltas[i] = Math.pow(pointA[i]-pointB[i], 2);
		}
		return ArrayUtils.sum(deltas);
	}

}
