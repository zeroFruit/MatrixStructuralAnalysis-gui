package Member;

public class frameExternFactor extends externalFactor{
	/*
	 * we should consider extended stress
	 * and this stress converted to FEM
	 * 
	 * and also consider whether the support point is tilted
	 * if tilted Fx, Fy u, v should be changed
	 */
	public frameExternFactor(double Fx, double Fy, double u, double v, String nodeNum) {
		super(Fx, Fy, u, v, nodeNum);
		// TODO Auto-generated constructor stub
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
