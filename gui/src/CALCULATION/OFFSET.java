package CALCULATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OFFSET {
	//ArrayList<double[]> recurConnection = new ArrayList<double[]>();
	ArrayList<Double> root_array = new ArrayList<Double>();
	double[] RESULT_constant_array;	
	
	//this ArrayList is for backward calculation after finishing pivoting, calculation
	//one element of double[] array is saved to this ArrayList every one looping
	//executing method 'recursiveGauss'
	private ArrayList<double[]> backwardGauss = new ArrayList<double[]>();
	
	void offset_coefficient(
			ArrayList<double[]> save_array_whose_root_is_variable, 
			double[] SAVE_constant_array
			) {

		/**********************************************
		 * #SHOULD BE FIXED
		 * we should check first whether there is root
		 **********************************************/
		
		
		
		
		//make array that connect coefficient array and constant
		//form :[ coefficient | constant ]
		ArrayList<double[]> connection = new ArrayList<double[]>();
		
		int size1 = save_array_whose_root_is_variable.size();
		for (int i = 0; i < size1; i++) {
			
			//+1 for saving constant
			double[] tempArray = new double[size1 + 1];
			
			for (int j = 0; j < size1; j++) {
				//0~(size-1)
				tempArray[j] = save_array_whose_root_is_variable.get(i)[j];
			}
			//size
			tempArray[size1] = SAVE_constant_array[i];
			
			connection.add(tempArray);
		}

		//start recursive function
		recursiveGauss(connection);
	}

	
	private void recursiveGauss(ArrayList<double[]> connection) {
		//this is made for the parameter for the next recursive method.
		//it save the result double[] array
		ArrayList<double[]> recurConnection = new ArrayList<double[]>();
		
		//sorting array set 'save_array_whose_root_is_variable' as DESC
		//to avoid the prime coefficient is '0'
		Collections.sort(connection, new Comparator<double[]>() {

			@Override
			public int compare(double[] o1, double[] o2) {
				// TODO Auto-generated method stub
				if(o1[0]>o2[0]){return -1;}
				
				else if(o1[0]<o2[0]){return 1;}
				
				else return 0;
			}
		});

		int index = 0;
		//array1 : pivoting array
		double[] array1 = connection.get(index);
		
		//test
		System.out.println();
		for (int i = 0; i < array1.length; i++) {
			System.out.print(String.format("%.2f ", array1[i]));
		}
		System.out.println();
		//test end
		
		//saved to the ArrayList 'backwardGauss' to calculate root at the last time.
		//the saved array is always 'pivoting array''
		backwardGauss.add(array1);

		double ratio;
		while (index < connection.size() - 1) {
			
			//array2 : this is rotating array, change every loop
			double[] array2 = connection.get(index + 1);
			
			//temp_result_array : this array will be added to the new ArrayList
			//						for parameter
			double[] temp_result_array = new double[array1.length - 1];
			
			//ratio is calculated by prime coefficient
			ratio = array2[0] / array1[0];
			
			//calculating part
			double[] ratioed_array1 = new double[array1.length];
			for (int i = 0; i < array1.length; i++) {
				
				ratioed_array1[i] = ratio * array1[i];
			}
			int p1idx = 1;
			for (int i = 0; i < temp_result_array.length; i++) {

				temp_result_array[i] = array2[p1idx] - ratioed_array1[p1idx];
				p1idx++;
			}
			
			recurConnection.add(temp_result_array);
			
			index++;
		}
		
		//from this, program choose whether we go one more 'recursiveGauss' method.
		//if the number of polynomial is more than one than we go one more method.
		if (recurConnection.size() > 1) {
			recursiveGauss(recurConnection);
			
			//start back substitution
			backwardCalc();
			
			return;
		} 
		else {
			//if program come to this block, 
			//then it should be there is one element in 'recurConnection'
			//and the element double[] length is '2'
			//index[0] for coefficient, index[1] for constant.
			//
			//from here,
			//we should get two roots, last one, and the second to the last one!
			
			/****last one****/
			double[] lastArr = recurConnection.get(0);
			double root1 = lastArr[1] / lastArr[0];

			root_array.add(root1);
			
			/****second to the last*****/
			int size = backwardGauss.size();
			
			//take the end of the 'backwardGauss'
			double[] polynomial = backwardGauss.get(size-1);
			//save the constant
			double constant = polynomial[polynomial.length-1];
			//calculate
			constant = constant - root_array.get(0)*polynomial[polynomial.length-2];
			double root2 = constant / polynomial[polynomial.length-2-1];
			
			root_array.add(root2);
			
			//the last element (double[]) should be romoved.
			backwardGauss.remove(size-1);
			
			return;
		}
	}

	private void backwardCalc() {
		// TODO Auto-generated method stub
		int size = backwardGauss.size();
		
		//take the end of the 'backwardGauss' 
		double[] polynomial = backwardGauss.get(size-1);
		//save the constant
		double constant = polynomial[polynomial.length-1];
		//how many times we should loop
		int loopNum = polynomial.length - 2;
		
		int i = 0;
		while (loopNum > i) {
			//test
			System.out.println("root_array size : " + root_array.size());
			System.out.println("polynomial len : " + polynomial.length);
			constant = constant - root_array.get(i)*polynomial[polynomial.length-2-i];
			i++;
		}
		double root1 = constant / polynomial[polynomial.length-2-i];
		
		root_array.add(root1);
		
		//the last element (double[]) should be romoved.
		backwardGauss.remove(size-1);
		
		System.out.println();
		System.out.println("this is done");
		System.out.println();
	}


	ArrayList<Double> get_root_array(){
		
		return root_array;
	}
}
