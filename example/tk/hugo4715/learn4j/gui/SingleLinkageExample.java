package tk.hugo4715.learn4j.gui;

import java.io.File;

import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import tk.hugo4715.learn4j.cluster.Clusterer;
import tk.hugo4715.learn4j.cluster.linkage.CompleteLinkageClusterer;
import tk.hugo4715.learn4j.cluster.linkage.LinkageClustererConfig;
import tk.hugo4715.learn4j.cluster.linkage.SingleLinkageClusterer;
import tk.hugo4715.learn4j.data.CSVDatasetLoader;
import tk.hugo4715.learn4j.data.Dataset;
import tk.hugo4715.learn4j.distance.EuclidianSquaredDistance;

/**
 * An example to show how to find clusters in a dataset using the SingleLinkageClusterer
 * @see 
 * {@link SingleLinkageClusterer} <br/> 
 * {@link CompleteLinkageClusterer} <br/>
 * {@link LinkageClustererConfig}
 */
public class SingleLinkageExample {

	public static void main(String[] args) throws Exception{
		
		//create the config using the builder
		LinkageClustererConfig config = LinkageClustererConfig.builder()
				.distance(new EuclidianSquaredDistance())//choose a distance measure
				.clusterAmount(3)//try to find 4 clusters
				.build();

		Clusterer c = new SingleLinkageClusterer(config);
		
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
