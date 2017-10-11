package tk.hugo4715.learn4j.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.hugo4715.learn4j.label.Label;

/**
 * Utility class to quickly load a dataset, currently very basic
 * TODO add more option (select labels, ignore some columns, etc)
 * TODO make a dataset saver
 */
public class CSVDatasetLoader {
	
	/**
	 * Load a CSV file to a dataset 
	 * @param file
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Load a CSV file to a dataset using the last column as labels
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static LabeledDataset<String> loadLabeled(File file) throws IOException{
		try(FileReader fr = new FileReader(file);BufferedReader br = new BufferedReader(fr)){
			Map<double[],Label<String>> map = new HashMap<>();
			
			
			String line;
			while((line = br.readLine()) != null){
				String[] parts = line.split(",");
				double[] values = new double[parts.length-1];
				Label<String> label = null;
				for (int i = 0; i <= values.length; i++) {
					if(i == values.length)label = new Label(parts[i]);
					else values[i] = Double.valueOf(parts[i]);
				}
				map.put(values, label);
			}
			return new LabeledDataset<>(map);
		}
	}
}
