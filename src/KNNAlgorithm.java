import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class KNNAlgorithm {
	
	private int k;
	private Map<Flower, IrisClass> dataSet;
	 
	public KNNAlgorithm(int k) {
	    this.k = k;
	}

	public void setDataSet(Map<Flower, IrisClass> dataSet) {
		this.dataSet = dataSet;
	}

	public IrisClass predict(Flower instance, Flower flower){
		 List<Result> result = new ArrayList<>();
		 for (Map.Entry<Flower, IrisClass> entry : dataSet.entrySet()) {
			 result.add(new Result(calculateDistance(instance, entry.getKey()),entry.getValue()));
		 }
		 Collections.sort(result, new DistanceComparator());
		 
		 List<IrisClass> neighborsToConsider = new ArrayList<>();
	     for (int i = 0; i <= k; i++) {
	    	 if (i >= result.size()) { // Avoid too big value of K
	                break;
	         }
	         neighborsToConsider.add(result.get(i).getIrisClass());
	     }
	     return ListUtils.mostCommonItem(neighborsToConsider);
	 }
	 
	 private double calculateDistance(Flower from, Flower to) {
		 double distance = 0.0;
	     distance += Math.pow(from.getSepalLength() - to.getSepalLength(), 2);
	     distance += Math.pow(from.getSepalWidth() - to.getSepalWidth(), 2);
	     distance += Math.pow(from.getPetalLength() - to.getPetalLength(), 2);
	     distance += Math.pow(from.getPetalWidth() - to.getPetalWidth(), 2);
	     return Math.sqrt(distance);
	 }

}
