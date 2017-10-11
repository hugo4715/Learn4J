package tk.hugo4715.learn4j.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tk.hugo4715.learn4j.distance.Distance;

public class Utils {
	public static double sum(double[] a){
		double value = 0;
		for(double d : a)value += d;
		return value;
	}
	
	/**
	 * Create a centroid from a list of points
	 * @param points A list of points
	 * @return A centroid point
	 */
	public static double[] createCentroid(List<double[]> points){
		double[] c = new double[points.get(0).length];
		for (int i = 0; i < c.length; i++) {
			for(double[] p : points)c[i] += p[i];
			c[i] /= points.size();
		}
		return c;
	}
	
	public static boolean isSamePoints(double[] pointA, double[] pointB){
		for (int i = 0; i < pointA.length; i++) {
			if(pointA[i] != pointB[i])return false;
		}
		return true;
	}
	
	/**
	 * Return the closest neighbour of a point
	 * @param point The point
	 * @param neighbours a set of available neighbours
	 * @param distance The distance to use
	 * @return the closest neighbours in the set
	 */
	public static double[] getClosestPoint(double[] point, Set<double[]> neighbours,Distance distance){
		double[] best = null;
		double min = Double.MAX_VALUE;
		
		for(double[] c : neighbours){
			double dist = distance.measure(point, c);
			
			if(dist < min){
				best = c;
				min = dist;
			}
		}
		
		return best;
	}
	
	public static void debugArray(double[] array){
		System.out.print('{');
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if(i < array.length-1)System.out.print(",");
		}
		System.out.println('}');
	}

	public static <T> Set<T> setFromList(List<T> l){
		return new HashSet<>(l);
	}
	
	public static <T> List<T> listFromSet(Set<T> s){
		return new ArrayList<>(s);
	}
}
