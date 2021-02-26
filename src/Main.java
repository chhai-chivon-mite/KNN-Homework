import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
	
	// 1. Select 10 records from each flower
	// 2. Using KNN to predict this record (Sepal Length : 3.1, Sepal Width : 2.3,Petal Length : 1.5, PetalWidth : 2.8)
	// 		in case : K = 1, K = 2, K = 3
							  	
	public static double sepalLength = 3.1;
	public static double sepalWidth = 2.3;
	public static double petalLength = 1.5;
	public static double petalWidth = 2.8;
	
	private static final int K = 3;
	  
	public static void main(String[] agrs) throws IOException {
		
		 KNNAlgorithm algorithm = new KNNAlgorithm(K);
		 
		 Map<IrisClass,List<Flower>> dataSets = loadData();
		 
		 System.out.println("Executing the " + K + "-Nearest Neighbors algorithm...");
		 
		 List<PredictionResult> predictionsResults = new ArrayList<>();
		 int goodPredictions = 0;
		 for (Map.Entry<IrisClass,List<Flower>> entry : dataSets.entrySet()) {
			 for(Flower f : entry.getValue()) {
				 Map<Flower, IrisClass> dataSet = new HashMap<>();
				 dataSet.put(f,entry.getKey());
				 algorithm.setDataSet(dataSet);
				 
				 IrisClass prediction = algorithm.predict(getInstance(), f);
				 System.out.println(prediction);
				 
				 PredictionResult result = new PredictionResult(entry.getKey(), prediction);
		         predictionsResults.add(result);

		         if (prediction == entry.getKey()) {
		            goodPredictions++;
		         }
			 }
		 }
		 
	    System.out.println("-----");
	    System.out.println("For K = " + K + " results are :");
	    System.out.println("     - Total predictions = " + dataSets.size() + " | good = " + goodPredictions + " | bad = " + (dataSets.size() - goodPredictions));
	    double accuracy = goodPredictions * 1.0 / dataSets.size();
	    System.out.println("     - Accuracy = " + accuracy * 100 + "%");
	}
	
	private static Flower getInstance() {
		
		Flower f = new Flower();
		f.setSepalLength(sepalLength);
		f.setSepalWidth(sepalWidth);
		f.setPetalLength(petalLength);
		f.setPetalWidth(petalWidth);

		return f;
	}
	
	private static Map<IrisClass,List<Flower>> loadData() throws IOException{
		Map<IrisClass,List<Flower>> result = new HashMap();
		List<Flower> setosas = getFlowerByRandom(10,IrisClass.SETOSA);
		result.put(IrisClass.SETOSA, setosas);
		List<Flower> versicolors = getFlowerByRandom(10,IrisClass.VERSICOLOR);
		result.put(IrisClass.VERSICOLOR, versicolors);
		List<Flower> virginicas = getFlowerByRandom(10,IrisClass.VIRGINICA);
		result.put(IrisClass.VIRGINICA, virginicas);
		return result;
	}
	
	private static List<Flower> getFlowerByRandom(int size, IrisClass irisClass) throws IOException{
		List<Flower> flowers = new ArrayList();
		List<Flower> flowersByType = getFlowsers().stream().filter(f -> f.getIrisClass().equals(irisClass)).collect(Collectors.toList());
		for(int i = 0 ; i < size ; i++) {
			Random random = new Random();
			Flower f = flowersByType.get(random.nextInt(flowersByType.size()));
			flowers.add(f);
			flowersByType = flowersByType
			  .stream()
			  .filter(e -> !e.equals(f))
			  .collect(Collectors.toList());
		}
		return flowers;
	}
	
	private static List<Flower> getFlowsers() throws IOException{
		List<Flower> flowsers = new ArrayList();
		
	    String data = readFile("iris.data");
	    String datas[] = data.split("\\r?\\n");

	    for(int i = 0 ; i < datas.length ; i++) {
	    	String[] objects = datas[i].split(",");
	    	Flower f = new Flower();
	    	f.setSepalLength(Double.valueOf(objects[0]));
	    	f.setSepalWidth(Double.valueOf(objects[1]));
	    	f.setPetalLength(Double.valueOf(objects[2]));
	    	f.setPetalWidth(Double.valueOf(objects[3]));
	    	f.setIrisClass(IrisClass.findByValue(objects[4]));
	    	flowsers.add(f);
	    }
	    return flowsers;
	}

	private static String readFile(String filename) throws IOException{
	    String content = null;
	    File file = new File(filename);
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader != null){
	            reader.close();
	        }
	    }
	    return content;
	}
}
