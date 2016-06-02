package CALCULATION;

import java.util.ArrayList;

public abstract class GaussElimination {
	abstract public void array_size_test();
	abstract public void calculate();
	abstract public ArrayList<Double> getResult();
	abstract public double[][] getCoefficient();
}
