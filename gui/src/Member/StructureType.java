package Member;

import java.util.ArrayList;

import CALCULATION.GaussElimination;
import CALCULATION.GlobalMatrix;

public abstract class StructureType {
	protected int how_many_node;
	protected int how_many_member;
	protected int type;
	protected ArrayList<int[]> connectionList;
	protected ArrayList<Property> propertyList;
	protected ArrayList<externalFactor> externFactorList;
	localMatrix localmatrix;
	
	public StructureType(int how_many_node, int how_many_member, int type) {
		this.how_many_node = how_many_node;
		this.how_many_member = how_many_member;
		this.type = type;
		connectionList = new ArrayList<int[]>();
		propertyList = new ArrayList<Property>();
		externFactorList = new ArrayList<externalFactor>();
	}
	abstract public void how_connected(int start, int end);
	abstract public ArrayList<int[]> get_connection_list();
	abstract public int get_how_many_node();
	abstract public int get_how_many_member();
	abstract public localMatrix getLocalMatrix();
	abstract public Property makeProperty(double A, double E, double L, double angle);
	abstract public externalFactor makeFactor(double Fx, double Fy, double u, double v, String node_num);
	abstract public GlobalMatrix getGlobalMatrix();
	abstract public GaussElimination getGaussElimination();
	/* this set method should be made also with Frame */
	public void set_Property_of_members(Property property, int type) {
		if (type == 0) {	// truss
			propertyList.add((trussProperty) property);
		}
		else if (type == 1) {	// frame
			propertyList.add((frameProperty) property);
		}
	}
	public void set_ExternFactor_to_members(externalFactor factor, int type) {
		if (type == 0) {	// truss
			externFactorList.add((trussExternFactor) factor);
		}
		else if (type == 1) {	// frame
			externFactorList.add((frameExternFactor) factor);
		}
	}
	public ArrayList<Property> getPropertyList(){
		return propertyList;
	}
	public ArrayList<externalFactor> getExternFactorList() {
		return externFactorList;
	}
}
