package tk.hugo4715.learn4j;

import java.io.File;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import tk.hugo4715.learn4j.clusterer.Clusterer;
import tk.hugo4715.learn4j.clusterer.kmean.KMeansClusterer;
import tk.hugo4715.learn4j.clusterer.kmean.KMeansClustererConfig;
import tk.hugo4715.learn4j.clusterer.kmean.KMeansPlusPlusClusterer;
import tk.hugo4715.learn4j.data.CSVDatasetLoader;
import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.distance.EuclidianSquaredDistance;

/**
 * An example to show how to find clusters in a dataset using the KMeanClusterer (or KMeansPlusPlusClusterer)
 * @see 
 * {@link KMeansClusterer} <br/> 
 * {@link KMeansClustererConfig}
 */
public class KMeanClustererExample {

	public static void main(String[] args) throws Exception{
		
		//create the config using the builder
		KMeansClustererConfig config = KMeansClustererConfig.builder()
				.distance(new EuclidianSquaredDistance())//choose a distance measure
				.clusterAmount(2)//try to find clusters
				.build();

		Clusterer c = new KMeansPlusPlusClusterer(config);
		
		//load a dataset
		Dataset s = CSVDatasetLoader.load(new File("/home/hugo4715/sample.csv"));

		Dataset[] result = c.cluster(s);
		
		DefaultXYDataset d = new DefaultXYDataset();
		//fill the XY dataset
		for (int i = 0; i < result.length; i++) {
			double[] x = new double[result[i].getContent().size()];
			double[] y = new double[result[i].getContent().size()];
			
			for(int l = 0; l < x.length;l++){
				x[l] = result[i].getContent().get(l)[0];
				y[l] = result[i].getContent().get(l)[1];
			}
			d.addSeries((Comparable)i, new double[][]{x,y});
		}
		
		//show the dataset using jfreechart
		final JFreeChart pieChart = ChartFactory.createScatterPlot("title", "x","y", d);
		final ChartPanel cPanel = new ChartPanel(pieChart);
		JOptionPane.showMessageDialog(null, cPanel);
	}

}
