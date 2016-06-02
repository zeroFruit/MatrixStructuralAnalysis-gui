package Member;

public abstract class Property {
	protected double A;
	protected double E;
	protected double L;
	protected double angle;

	public Property(double A, double E, double L, double angle) {
		// TODO Auto-generated constructor stub
		this.A = A;
		this.E = E;
		this.L = L;
		this.angle = angle;
	}
	abstract double get_A();
	abstract double get_E();
	abstract double get_L();
	abstract double get_angle();
}
