package CALCULATION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import Member.StructureType;
import Member.Truss;
import Member.externalFactor;
import Member.trussExternFactor;
import Member.trussLocalMatrix;
import Member.trussProperty;

public class GlobalmatrixTruss extends GlobalMatrix{
	public GlobalmatrixTruss(StructureType structureType) {
		super(structureType);
	}
	protected void how_many_we_need_variables(){
		how_many_variables = structureType.get_how_many_node() * 2;
	}
	//Global Matrix 만들기
	public double[][] CONSTRUCT_COEFFIECIENT_MATRIX(){
		how_many_we_need_variables();
		double[][] Global_Coeffiecient = new double[how_many_variables][how_many_variables];
		
		for (int i = 0; i < how_many_variables; i++) {					
			for (int j = 0; j < how_many_variables; j++) {
				Global_Coeffiecient[i][j] = 0;
			}
		}
		ArrayList<int[]> temp_connection = structureType.get_connection_list();
		int check = temp_connection.size();								// 모든 connection 다 추출하기 위해
		int index = 0;													// 모든 connection 다 추출하기 위해
		
		while (check  > 0) {		
			int[] how_connect = temp_connection.get(index);
			int node_start = how_connect[0];
			int node_end = how_connect[1];
			trussProperty temp_property = (trussProperty) structureType.getPropertyList().get(index);		
			HashMap<String, Double> temp_coeffiecient = 
					structureType.getLocalMatrix().setup_trussCoefficientMatrix(temp_property, node_start, node_end);
			
			/**************************************************************************************************
			 * connection 에 의해 구한 Local Matrix 의 HashMap 에서 
			 * 
			 * key 값을 Global Matrix의 Array의 인덱스로 변환하고 그 인덱스에 값을 더한다.
			 **************************************************************************************************/
			
			Set<String> set = temp_coeffiecient.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = it.next();
				int[] global_index = convert_to_index(key);
				int x = global_index[0];
				int y = global_index[1];
				
				Global_Coeffiecient[x][y] = Global_Coeffiecient[x][y] + temp_coeffiecient.get(key);
			}
			
			check--;
			index++;
		}
		
		return Global_Coeffiecient;
	}
	
	
	public double[] CONSTRUCT_ROOT_MATRIX(){
		int temp_size  = structureType.getExternFactorList().size();
		ArrayList<externalFactor> temp_externFactorList = structureType.getExternFactorList();
		double[] temp_root_matrix = new double[ 2 * temp_size ];
		// initialize
		for (int i = 0; i < temp_root_matrix.length; i++) {
			temp_root_matrix[i] = 0.0;
		}
		// input value
		int j = 0;
		for (int i = 0; i <  temp_size ; i ++) {
			trussExternFactor externFactor = (trussExternFactor) temp_externFactorList.get(i);
			
			temp_root_matrix[j] = externFactor.get_u();
			j++;
			temp_root_matrix[j] = externFactor.get_v();
			j++;
		}
		return temp_root_matrix;
	}
	
	
	public double[] CONSTRUCT_CONSTANT_MATRIX(){
		int temp_size  = structureType.getExternFactorList().size();
		ArrayList<externalFactor> temp_externFactorList = structureType.getExternFactorList();
		double[] temp_constant_matrix = new double[ 2 * temp_size ];
		
		//초기화
		for (int i = 0; i < temp_constant_matrix.length; i++) {
			temp_constant_matrix[i] = 0.0;
		}
		
		//값 입력
		int j = 0;
		for (int i = 0; i <  temp_size  ; i++) {
			trussExternFactor externFactor = (trussExternFactor) temp_externFactorList.get(i);
			
			temp_constant_matrix[j] = externFactor.get_Fx();
			j++;
			temp_constant_matrix[j] = externFactor.get_Fy();
			j++;
		}
		
		return temp_constant_matrix;
	}
	
}
