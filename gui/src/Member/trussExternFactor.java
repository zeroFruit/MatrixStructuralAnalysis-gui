package Member;

public class trussExternFactor extends externalFactor {
	trussExternFactor(double Fx, double Fy, double u, double v, String node_num) {
		super(Fx, Fy, u, v, node_num);
	}
	@Override
	public double get_Fx() {
		// TODO Auto-generated method stub
		return F[0];
	}
	@Override
	public double get_Fy() {
		// TODO Auto-generated method stub
		return F[1];
	}
	@Override
	public double get_u() {
		// TODO Auto-generated method stub
		return distance[0];
	}
	@Override
	public double get_v() {
		// TODO Auto-generated method stub
		return distance[1];
	}
	@Override
	String get_nodeNum() {
		// TODO Auto-generated method stub
		return nodeNum;
	}
}
