package tk.hugo4715.learn4j.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to quickly load a dataset, currently very basic
 * TODO add more option (select labels, ignore some columns, etc)
 * TODO make a dataset saver
 */
public class CSVDatasetLoader {
	public static Dataset load(File file) throws IOException{
		try(FileReader fr = new FileReader(file);BufferedReader br = new BufferedReader(fr)){
			List<double[]> v = new ArrayList<>();
			
			String line;
			while((line = br.readLine()) != null){
				String[] parts = line.split(",");
				double[] values = new double[parts.length];
				for (int i = 0; i < values.length; i++) {
					values[i] = Double.valueOf(parts[i]);
				}
				v.add(values);
			}
			
			return new Dataset(v);
		}
	}
}
