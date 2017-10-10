package tk.hugo4715.learn4j.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represent a dataset
 * It looks a bit useless since it just wraps a list, but trust me it's not since we can make subclasses, with labels for example
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
