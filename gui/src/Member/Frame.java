package Member;

import java.util.ArrayList;

import CALCULATION.GaussElimination;
import CALCULATION.GlobalMatrix;
import CALCULATION.GlobalmatrixFrame;

public class Frame extends StructureType {
	public Frame(int how_many_node, int how_many_member, int type) {
		super(how_many_node, how_many_member, type);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void how_connected(int start, int end) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<int[]> get_connection_list() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int get_how_many_node() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int get_how_many_member() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public trussProperty makeProperty(double A, double E, double L, double angle) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public trussExternFactor makeFactor(double Fx, double Fy, double u, double v, String node_num) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public localMatrix getLocalMatrix() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GlobalMatrix getGlobalMatrix() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GaussElimination getGaussElimination() {
		// TODO Auto-generated method stub
		return null;
	}
}
