import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	private static final String IRIS_SETOSA = "Iris-setosa";
	private static final String IRIS_VERSICOLOR = "Iris-versicolor";
	private static final String IRIS_VIRGINICA = "Iris-virginica";
	
	public static void main(String[] agrs) throws IOException {
		List<Flower> flows = new ArrayList();
		List<Flower> setosas = getFlowerByRandom(10,IRIS_SETOSA);
		List<Flower> versicolors = getFlowerByRandom(10,IRIS_VERSICOLOR);
		List<Flower> virginicas = getFlowerByRandom(10,IRIS_VIRGINICA);
		flows.addAll(setosas);
		flows.addAll(virginicas);
		flows.addAll(versicolors);
		System.out.println(flows.size());
	}
	
	private static List<Flower> getFlowerByRandom(int size, String type) throws IOException{
		List<Flower> flowers = new ArrayList();
		List<Flower> flowersByType = getFlowsers().stream().filter(f -> f.getClassName().equals(type)).collect(Collectors.toList());
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
	
	public static List<Flower> getFlowsers() throws IOException{
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
	    	f.setClassName(objects[4]);
	    	flowsers.add(f);
	    }
	    return flowsers;
	}

	public static String readFile(String filename) throws IOException{
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
