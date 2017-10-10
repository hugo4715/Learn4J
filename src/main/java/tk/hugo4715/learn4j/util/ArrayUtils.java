package tk.hugo4715.learn4j.util;

public class ArrayUtils {
	public static double sum(double[] a){
		double value = 0;
		for(double d : a)value += d;
		return value;
	}
}
