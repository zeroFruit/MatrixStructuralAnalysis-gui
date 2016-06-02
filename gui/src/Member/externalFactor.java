package Member;

public abstract class externalFactor {
	protected double F[] = new double[2];
	protected double distance[] = new double[2];
	protected String nodeNum;
	public externalFactor(double Fx, double Fy, double u, double v, String nodeNum) {
		// TODO Auto-generated constructor stub
		this.F[0] = Fx; 
		this.F[1] = Fy;
		this.distance[0] = u; this.distance[1] = v;
		this.nodeNum = nodeNum;
	}
	abstract public double get_Fx();
	abstract public double get_Fy();
	abstract public double get_u();
	abstract public double get_v();
	abstract String get_nodeNum();
}
