package Member;

import java.util.ArrayList;
import java.util.HashMap;

import CALCULATION.GaussEliminationTruss;
import CALCULATION.GlobalMatrix;
import CALCULATION.GlobalmatrixTruss;


public class Truss extends StructureType {
	public Truss(int how_many_node, int how_many_member, int type) {
		super(how_many_node, how_many_member, type);
	}
	@Override
	public void how_connected(int start, int end) {
		int[] connection = {start, end};
		connectionList.add(connection);
	}
	@Override
	public ArrayList<int[]> get_connection_list() {
		return connectionList;
	}
	@Override
	public int get_how_many_node() {
		return how_many_node;
	}
	@Override
	public int get_how_many_member() {
		return how_many_member;
	}
	@Override
	public trussProperty makeProperty(double A, double E, double L, double angle) {
		return new trussProperty(A, E, L, angle);
	}
	@Override
	public trussExternFactor makeFactor(double Fx, double Fy, double u, double v, String node_num) {
		return new trussExternFactor(Fx, Fy, u, v, node_num);
	}
	@Override
	public trussLocalMatrix getLocalMatrix() {
		return new trussLocalMatrix();
	}
	@Override
	public GlobalmatrixTruss getGlobalMatrix(){
		return new GlobalmatrixTruss(this);
	}
	@Override
	public GaussEliminationTruss getGaussElimination(){
		return new GaussEliminationTruss(this);
	}
}
