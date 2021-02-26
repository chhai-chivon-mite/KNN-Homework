
public class Result {
	
	private double distance;
    private IrisClass irisClass;

     Result(double distance, IrisClass irisClass) {
        this.distance = distance;
        this.irisClass = irisClass;
    }

     double getDistance() {
        return distance;
    }

     void setDistance(double distance) {
        this.distance = distance;
    }

	public IrisClass getIrisClass() {
		return irisClass;
	}

	public void setIrisClass(IrisClass irisClass) {
		this.irisClass = irisClass;
	}

}
