package tk.hugo4715.learn4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import tk.hugo4715.learn4j.classifier.perceptron.PerceptronClassifier;
import tk.hugo4715.learn4j.data.CSVDatasetLoader;
import tk.hugo4715.learn4j.data.LabeledDataset;
import tk.hugo4715.learn4j.label.Label;
import tk.hugo4715.learn4j.util.Pair;

/**
 * Classify some points using a {@link PerceptronClassifier} 
 * @see
 * {@link PerceptronClassifier}
 */
public class PerceptronClassifierExample {

	public static void main(String[] args) throws Exception{
		//load a labeled dataset from a file (here labels are Strings)
		LabeledDataset<String> set = CSVDatasetLoader.loadLabeled(new File("/home/hugo4715/samples/sample2.csv"));
		
		//create a PerceptronClassifier using the dataset, this classifier is trained in the constructor
		PerceptronClassifier<String> classifier = new PerceptronClassifier<>(set);

		//create a map to store the series
		Map<Label<String>,Pair<List<Double>,List<Double>>> series = new HashMap<>();//label -> (set of x, set of y)
		
		//for each point, get its label and add it to the good series
		for(double[] point : set.getContent()){
			//get the label
			Label<String> out = classifier.classify(point).entrySet().stream().sorted(Map.Entry.comparingByValue()).findFirst().get().getKey();

			//add the points to the label set
			Pair<List<Double>,List<Double>> data = series.get(out);
			if(data == null)data = new Pair<>(new ArrayList<>(),new ArrayList<>());
			data.getLeft().add(point[0]);
			data.getRight().add(point[1]);
			series.put(out, data);
		}
		
		//show the series
		
		DefaultXYDataset d = new DefaultXYDataset();
		for(Entry<Label<String>, Pair<List<Double>, List<Double>>> s : series.entrySet()){
			double[] xSeries = s.getValue().getLeft().stream().mapToDouble(x -> x).toArray();
			double[] ySeries = s.getValue().getRight().stream().mapToDouble(y -> y).toArray();
			
			d.addSeries(s.getKey().getValue(), new double[][]{xSeries,ySeries});
		}
		

		//show the dataset using jfreechart
		final JFreeChart pieChart = ChartFactory.createScatterPlot("title", "x","y", d);
		final ChartPanel cPanel = new ChartPanel(pieChart);
		JOptionPane.showMessageDialog(null, cPanel);

	}

}
