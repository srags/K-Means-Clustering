/**
* Kmeans clusters a dataset using an implementation of the Kmeans algorithm
* @author Elizabeth Song and Shreyaa Raghavan
* @version 4.08.19
*/

import java.util.*;

public class Kmeans {
	private double[][] dataset;
	private double[][] centers;		//array of the centers of the clusters (in coordinate form)
	private double[][] prevCenters;
	private int numK;		//number of centers/clusters
	private double [][] distances;
	private ArrayList<ArrayList<Double[]>> clusters;
	
	
	//constructor that initializes dataset, numK, clusters, and prevCenters
	public Kmeans(double[][] data, int _k) {
		dataset = data;

		numK = _k;
		clusters = new ArrayList<ArrayList<Double[]>> (numK);
		for(int i = 0; i < numK; i++) {
			clusters.add(new ArrayList<Double[]>());
		}
	
		prevCenters = new double[numK][2];
	}
	
	
	//method that initializes centers to numK random points in dataset
	public void initializeCenters() {
		centers = new double[numK][2];
		int index;
		
		ArrayList<Integer> indices = uniqueRand();
		
		for(int i = 0;i < numK; i++) {
			index = indices.get(i);
			centers[i] = dataset[index];
		}
		
		System.out.println("centers: " + Arrays.deepToString(centers));
	}
	
	//method that resets the centers of the clusters
	public void computeCenters() {
		for(int i = 0; i < numK; i++) {
			centers[i] = newCenterOfOne(clusters.get(i));
		}
		
		System.out.println("new centers:" + Arrays.deepToString(centers));
	}
	
	//method that computes the new center of one cluster by taking the average of the points
	public double[] newCenterOfOne(ArrayList<Double[]> cluster) {
		Double sumX = new Double(0.0);
		Double sumY = new Double (0.0);
		
		for(int i = 0; i < cluster.size(); i++) {
			sumX += cluster.get(i)[0];
			sumY += cluster.get(i)[1];
		}
		
		sumX /= cluster.size();
		sumY /= cluster.size();
		
		double[] newCenter = {sumX.doubleValue(), sumY.doubleValue()};
		
		return newCenter;
	}


	//method that computes the distances of each point to the center
	public void computeDist() {
		distances = new double[dataset.length][numK];
		for (int r = 0; r < dataset.length; r++){
			for (int c = 0; c < numK; c++){
				distances[r][c] = distance(dataset[r][0], dataset[r][1], centers[c][0], centers[c][1]);
			}
		}
	}
	

	//method that stores the values of centers before the next iteration
	public void assignPrevCenters(){
		for(int i = 0; i < numK; i++) {
			for(int j = 0; j < 2; j++) {
				prevCenters[i][j] = centers[i][j];
			}
		}	
	}
	
	//method that assigns points to clusters
	public void assign() {
		for (ArrayList<Double[]> cluster: clusters){
			cluster.removeAll(cluster);
		}
		
		//iterate through distances.
		for(int r = 0; r < dataset.length; r++) {
			//for each row, find column index of smallest element
			int clusterNum = minIndex(distances[r]);
			
			//get coord corresponding to the location in distances and add it to cluster corresponding to column index
			Double[] coordinate = new Double[2];
			coordinate[0] = new Double(dataset[r][0]);
			coordinate[1] = new Double(dataset[r][1]);
			clusters.get(clusterNum).add(coordinate);
		}
		System.out.println(Arrays.deepToString(prevCenters));
	}
	
	//method that determines whether or not centers has changed
	public boolean centerEqual() {
		for(int i = 0; i < numK; i++){
			for(int j = 0; j < 2; j++) {
				if (prevCenters[i][j] != centers[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	//method that finds the index of the minimum value in an array
	public int minIndex(double[] arr) {
		int min = 0;
		
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] < arr[min]) {
				min = i;		
			}
		}
		
		return min;
	}
	
	//method that calculates the distance between any two points
	public double distance(double x1, double y1, double x2, double y2) {		
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	
	//method that sets the values of an array to 1 through N and randomly shuffles them
	public ArrayList<Integer> uniqueRand(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < dataset.length; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        ArrayList<Integer> randNum = new ArrayList<Integer>();
        for (int i = 0; i < numK; i++) {
            randNum.add(list.get(i));
        }
        
        return randNum;
    }
    
    //method that adds coordinate to a cluster
    public void addCoord_Cluster(Double x, Double y, ArrayList<Double[]> row) {
    	Double[] coordinate = {x, y};
    	row.add(coordinate);
    }
    
    public ArrayList<ArrayList<Double[]>> getClusters() {
    	return clusters;
    }
}