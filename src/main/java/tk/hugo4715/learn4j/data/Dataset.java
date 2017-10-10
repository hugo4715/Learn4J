package tk.hugo4715.learn4j.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represent a dataset
 */
public class Dataset {
	protected List<double[]> storage;
	
	public Dataset() {
		this(new ArrayList<double[]>());
	}
	
	public Dataset(Set<double[]> storage) {
		this();
		for (double[] ds : storage) {
			this.storage.add(ds);
		}
	}

		
	public Dataset(List<double[]> storage) {
		this.storage = storage;
	}
	
	public List<double[]> getContent() {
		return storage;
	}
	
	public void addEntry(double[] value){
		storage.add(value);
	}
}
