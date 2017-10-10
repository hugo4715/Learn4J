package tk.hugo4715.learn4j.distance;

public class EuclidianDistance implements Distance {
	
	private EuclidianSquaredDistance squared;
	
	public EuclidianDistance() {
		squared = new EuclidianSquaredDistance();
	}
	@Override
	public double measure(double[] pointA, double[] pointB) {
		return Math.sqrt(squared.measure(pointA, pointB));
	}

}
