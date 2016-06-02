package Main;
import Member.Frame;
import Member.StructureType;
import Member.Truss;


public class UserInterface {
	StructureType structuretype;
	int type;
	
	public UserInterface(int nodeNum, int memberNum, int type){
		setType(type);
		if (type == 0) {
			structuretype = new Truss(nodeNum, memberNum, type);
		}
		else if (type == 1) {
			structuretype = new Frame(nodeNum, memberNum, type);
		}
		else {
			System.out.println("type Error: choose wrong type");
			System.exit(1);
		}
	}
	private void setType(int type){
		this.type = type;
	}
	public void setProperty(double A, double E, double L, double angle){
		int memberNum = structuretype.getPropertyList().size();
		int memberSize = structuretype.get_how_many_member();
		if (memberNum <= memberSize) {
			structuretype.set_Property_of_members(structuretype.makeProperty(A, E, L, angle), this.type);
		} 
		else {
			System.out.println("Error: Input more than member size");
		}
	}
	public void setFactor(double Fx, double Fy, double u, double v, String node_num){
		int factorNum = structuretype.getExternFactorList().size();
		int factorSize = structuretype.get_how_many_node();
		if (factorNum <= factorSize) {
			structuretype.set_ExternFactor_to_members(structuretype.makeFactor(Fx, Fy, u, v, node_num), this.type);
		} 
		else {
			System.out.println("Error: Input more than extern factor size");
		}
	}
	public void howConnected(int start, int end){
		structuretype.how_connected(start, end);
	}
	void solution(){
		structuretype.getGaussElimination().array_size_test();
		structuretype.getGaussElimination().calculate();
	}
	public StructureType getStructureType(){
		return this.structuretype;
	}
}
