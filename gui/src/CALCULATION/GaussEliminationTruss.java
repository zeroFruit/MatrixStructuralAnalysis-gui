package CALCULATION;

import java.util.ArrayList;

import CALCULATION.OFFSET;
import Member.StructureType;

public class GaussEliminationTruss extends GaussElimination{
	public GlobalmatrixTruss global;
	double[][] CONSTRUCT_COEFFIECIENT_MATRIX;
	double[] CONSTRUCT_ROOT_MATRIX;
	double[] CONSTRUCT_CONSTANT_MATRIX;
	// this is for GUI Output Class
	ArrayList<Double> Result_Answer;
	double[][] Result_Coeffiecient;
	
	public GaussEliminationTruss(StructureType structuretype) {
		global = (GlobalmatrixTruss) structuretype.getGlobalMatrix();
		
		CONSTRUCT_COEFFIECIENT_MATRIX = global.CONSTRUCT_COEFFIECIENT_MATRIX();
		CONSTRUCT_ROOT_MATRIX = global.CONSTRUCT_ROOT_MATRIX();
		CONSTRUCT_CONSTANT_MATRIX = global.CONSTRUCT_CONSTANT_MATRIX();
		//test
		for (int i = 0; i < CONSTRUCT_CONSTANT_MATRIX.length; i++) {
			System.out.println("test: " + CONSTRUCT_CONSTANT_MATRIX[i]);
		}
	}
	
	public void array_size_test(){
		if (CONSTRUCT_COEFFIECIENT_MATRIX.length != CONSTRUCT_COEFFIECIENT_MATRIX.length) {
			System.out.println("ERROR : Size of coeffiecient matrix is different with the size of constant matrix");
		} 
		else if (CONSTRUCT_COEFFIECIENT_MATRIX.length != CONSTRUCT_COEFFIECIENT_MATRIX.length) {
			System.out.println("ERROR : Size of coeffiecient matrix is different with the size of root matrix");
		} 
		else {
			System.out.println("Size is same!");
		}
	}
	// return answer arrayList
	public ArrayList<Double> getResult(){
		return this.Result_Answer;
	}
	// return coeffiecient double[][] array
	public double[][] getCoefficient(){
		return this.Result_Coeffiecient;
	}
	public void calculate(){
		ArrayList<Double> Result =
		SOLVER(
				CONSTRUCT_COEFFIECIENT_MATRIX, 
				CONSTRUCT_ROOT_MATRIX, 
				CONSTRUCT_CONSTANT_MATRIX
				);
		// save result
		this.Result_Answer = Result;
		/****************************************************************
		 * 
		 * 여기다가 출력 포맷 만들기
		 * 
		 ****************************************************************/
		
		
		/****************************************************************
		 * TEST 
		 ****************************************************************/
		for (int i = 0; i < Result.size(); i++) {
			System.out.println(i + "  : " + Result.get(i));
		}
		
		/****************************************************************
		 * TEST
		 ****************************************************************/
		
		
	}
	
	

	private ArrayList<Double> SOLVER(
			double[][] construct_COEFFIECIENT_MATRIX, 
			double[] construct_ROOT_MATRIX, 
			double[] constrct_CONSTANT_MATRIX
			) {
		
		// TODO Auto-generated method stub
		double[][] coeffiecient_matrix = construct_COEFFIECIENT_MATRIX;
		double[] root_matrix = construct_ROOT_MATRIX;
		double[] constant_matrix = constrct_CONSTANT_MATRIX;
		
		ArrayList<Integer> save_index_who_is_not_variable = new ArrayList<Integer>();
		ArrayList<Integer> save_index_who_IS_VARIABLE = new ArrayList<Integer>();
		ArrayList<double[]> save_ARRAY_whose_root_is_variable = new ArrayList<double[]>();
		ArrayList<Double> save_root = new ArrayList<Double>();
		// set coefficient array
		this.Result_Coeffiecient = coeffiecient_matrix;
		
		/**********************************************************************************
		 * TEST
		 **********************************************************************************/
		
		System.out.println("coeffiecient matrix LENGTH : " + coeffiecient_matrix.length);
		System.out.println("root matrix LENGTH : " + root_matrix.length);
		
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("                    COEFFIECIENT MATRIX                  ");
		System.out.println("-----------------------------------------------------------------------------------");
		
		for (int i = 0; i < coeffiecient_matrix.length; i++) {
			System.out.println();
			for (int j = 0; j < coeffiecient_matrix[i].length; j++) {
				System.out.print("  " + String.format("%.2f", coeffiecient_matrix[i][j]) + "  ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println();
		System.out.println();
		
		System.out.println();
		System.out.println("-------------------------------------");
		System.out.println("	ROOT MATRIX");
		System.out.println("-------------------------------------");
		
		for (int i = 0; i < root_matrix.length; i++) {
			System.out.println("	"+String.format("%.2f", root_matrix[i])+"	");
		}
		
		System.out.println("-------------------------------------");
		System.out.println();
		System.out.println();
		
		/**********************************************************************************
		 * TEST
		 **********************************************************************************/
		
		for (int i = 0; i < root_matrix.length; i++) {
			if (root_matrix[i] != 999) {
				save_index_who_is_not_variable.add(i);
			}
			else {
			//	save_ARRAY_whose_root_is_variable.add(coeffiecient_matrix[i]);
				save_index_who_IS_VARIABLE.add(i);
				save_root.add(root_matrix[i]);
			}
		}

		/**********************************************************************************
		 * 
		 * 수정 : 변수 아닌 부분 계산해서 정리한 후 행렬 사이즈 줄여서 OFFSET 으로 넘기기
		 * 
		 **********************************************************************************/
		double[] constant_matrix_notvalue = new double[save_index_who_IS_VARIABLE.size()];
		
		for (int i = 0; i < save_index_who_IS_VARIABLE.size(); i++) {
			
			double[] reduced_coefficient_matrix = new double[save_index_who_IS_VARIABLE.size()];
			double not_val_sum = 0;
			int index_val = save_index_who_IS_VARIABLE.get(i);
			
			for (int j = 0; j < save_index_who_is_not_variable.size(); j++) {
				
				int index_notval = save_index_who_is_not_variable.get(j);
				not_val_sum += coeffiecient_matrix[index_val][index_notval] * root_matrix[index_notval];
			}
			
			constant_matrix_notvalue[i] = constant_matrix[index_val] - not_val_sum;
			
			for (int j = 0; j < save_index_who_IS_VARIABLE.size(); j++) {
				
				int index_val_column = save_index_who_IS_VARIABLE.get(j);
				reduced_coefficient_matrix[j] = coeffiecient_matrix[index_val][index_val_column];
			}
			
			save_ARRAY_whose_root_is_variable.add(reduced_coefficient_matrix);
		}	
		
	
		
		/*********************************************************************************/
		OFFSET offset = new OFFSET();
		offset.offset_coefficient(save_ARRAY_whose_root_is_variable, constant_matrix_notvalue);
		
		ArrayList<Double> result_root_array = offset.get_root_array();

		
		
		/*******************************************************************************************************
		 * 
		 * save_index_who_is_not_variable과 save_index_who_IS_VARIABLE에 저장되어 있는 
		 * 
		 * 인덱스를 비교하면서 root_matrix 에 있던 I_DONT_KNOW(999)를 해로 바꿔 넣는다.
		 * 
		 *******************************************************************************************************/
		
		ArrayList<Double> arrange_root_arraylist = 
				REARRANGE_MATRIX(
				save_index_who_is_not_variable, 
				
				save_index_who_IS_VARIABLE, 
				
				root_matrix, 
				
				result_root_array);
		ArrayList<Double> final_result_root_arraylist = new ArrayList<Double>();
		
		/* ******************************************************************************************************
		 * 
		 * coeffiecient matrix 와 root matrix 곱셈
		 * 
		  *******************************************************************************************************/
		
		for (int i = 0; i < coeffiecient_matrix.length; i++) {
			double F = 0;
			
			for (int j = 0; j < arrange_root_arraylist.size(); j++) {
				
				F += coeffiecient_matrix[i][j] * arrange_root_arraylist.get(j);
				
			}
			final_result_root_arraylist.add(F);
		}
		
			
		return final_result_root_arraylist;
	}
	
	/****************************************************************************************************************************************
	 * 
	 * REARRANGE_MATRIX 
	 * 
	 * @param 
	 * 1. 해(root) 배열 중에 변수가 아닌 곳의 주소를 모아 놓은 배열
	 * 
	 * 2. 해(root) 배열 중에 변수인 곳의 주소를 모아 놓은 배열
	 * 
	 * 3. 원래의 해 배열
	 * 
	 * 4. offset_coeffiecient 메소드를 통해 구한 해 행렬
	 * 
	 ****************************************************************************************************************************************/

	private ArrayList<Double> REARRANGE_MATRIX(
			ArrayList<Integer> save_index_who_IS_NOT_VARIABLE,
			//save index which was variable from the original root array
			ArrayList<Integer> save_index_who_IS_VARIABLE,
			//the array mixed with variable, non-variable
			//array before classify to 
			double[] original_root_array,
			//the ArrayList we get from the offset class
			ArrayList<Double> Result_ROOT_array) {
		
		
		ArrayList<Double> arrange_ROOT_MATRIX = new ArrayList<Double>();
		int size = Result_ROOT_array.size();
		
		int num = 0;
		boolean check1 = false;
		while (num < original_root_array.length) {
			//first, search from the non-variable index array
			//if index saved in 'save_index_who_IS_NOT_VARIABLE' same with 'num'
			//then end one loop
			for (int i = 0; i < save_index_who_IS_NOT_VARIABLE.size(); i++) {
				int index = save_index_who_IS_NOT_VARIABLE.get(i);
						
				if( index == num){
					double root = original_root_array[index];
					arrange_ROOT_MATRIX.add(root);
					
					//if 'check1' is set to 'true'
					//then second end loop also break
					//because we find the correct index
					check1 = true;
					break;
				}
			}
			
			//second, search from the variable index array
			for (int j = 0; j < save_index_who_IS_VARIABLE.size(); j++) {
				if (check1 == true){
					check1 = false;
					
					break;
				}
				int index = save_index_who_IS_VARIABLE.get(j);
				if ( index == num) {
					double root = Result_ROOT_array.get(size - 1 - j);			// 오류 !!!!! 조심 !!!!!!!!!!!!!!
					arrange_ROOT_MATRIX.add(root);
					
					break;
				}	 
			}
			num++;
		}
		//In 'arrange_ROOT_MATRIX', there is now root ORDERLY 
		return arrange_ROOT_MATRIX;
	}

}
