/**
* KmeansRunner instantiates Kmeans and graphically displays the results of the algorithm 
* @author Elizabeth Song and Shreyaa Raghavan
* @version 4.08.19
*/

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class KmeansRunner extends JPanel {

	private static int points;
	private static int numK = 7;
	final private static int SIZE = 700;
	private static double[][] dataset;
							  
							  
	//main method initializes JFrame with fixed dimensions
	public static void main(String[] args) throws IOException{
	
		ReadExcel test = new ReadExcel();
		test.setInputFile("Absenteeism_at_work.xls");
    	dataset = test.read();
    	
		points = dataset.length; 
		
    	JFrame frame = new JFrame();
    	frame.add(new KmeansRunner());
    	frame.setSize(SIZE, SIZE);
    	frame.setVisible(true);		
	}
	
	public void paint(Graphics g) {
	
		for (int i = 0; i < points; i++){		//plotting each of the points 
			double xTemp = 6 * dataset[i][0] - 200;
			double yTemp = 6* dataset[i][1] - 100;
			g.fillOval(SIZE/2 + (int)xTemp, SIZE/2 - (int)yTemp, 6, 6);
		}
		

		Kmeans km = new Kmeans(dataset, numK);
		km.initializeCenters();
		km.computeDist();

		km.assign();
		
		while(!km.centerEqual()){		//continues to loop until centers stop changing
				km.assignPrevCenters();
				km.computeCenters();
				km.computeDist();
				
				km.assign();
				System.out.println(km.centerEqual());
			
		}
		
		ArrayList<ArrayList<Double[]>> finalClusters = km.getClusters();
		
		Color red = new Color(255, 0, 0);
		Color aqua = new Color(50, 100, 200);
		Color green = new Color(0, 255, 0);
		Color purple = new Color(100, 0, 100);
		Color notyellow = new Color(100, 100, 0);
		Color blue = new Color(0, 0, 255);
		Color black = new Color(0, 0, 0);
		
		Color[] arr = {red, aqua, green, purple, notyellow, blue, black};
		
		for(int i = 0; i < numK; i++) {		//colors each final cluster a different color
			g.setColor(arr[i]);
			
			for(Double[] point: finalClusters.get(i)){
				double x = 6 *point[0].doubleValue()-200;
				double y = 6 *point[1].doubleValue()-100;
				g.fillOval(SIZE/2 + (int)x, SIZE/2 - (int)y, 6, 6);
			}
		}
		
	}
}