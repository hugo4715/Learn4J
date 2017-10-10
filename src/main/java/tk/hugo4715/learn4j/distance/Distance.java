package tk.hugo4715.learn4j.distance;

/**
 * Measure a distance between 2 points in an n-dimensional space
 */
public interface Distance {
	public double measure(double[] pointA, double[] pointB);
}
