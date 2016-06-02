package CALCULATION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import Main.UserInterface;
import Member.trussLocalMatrix;
import Member.StructureType;
import Member.localMatrix;
import Member.trussProperty;
import Member.trussExternFactor;

public abstract class GlobalMatrix {
	StructureType structureType;
	protected int how_many_variables;
	
	public GlobalMatrix(StructureType structureType) {
		this.structureType = structureType;
	}
	
	protected int how_many_we_need_local_matrix(){
		return structureType.get_how_many_member();
	}
	int get_num_variables(){
		return how_many_variables;
	}
	abstract protected void how_many_we_need_variables();
	//Global Matrix 만들기
	abstract public double[][] CONSTRUCT_COEFFIECIENT_MATRIX();
	abstract public double[] CONSTRUCT_ROOT_MATRIX();
	abstract public double[] CONSTRUCT_CONSTANT_MATRIX();
	
	
	/***********************************************************************************************************
	 * METHOD convert_to_index
	 * 
	 *  key 값이 [U 숫자 V 숫자] , [U 숫자 U 숫자], [V 숫자 U 숫자], [V 숫자 V 숫자] 형식인데
	 *  
	 *  U, V 에 따라 '숫자'의 계산 방식이 달라진다.
	 ***********************************************************************************************************/
	protected int[] convert_to_index(String next) {
		int x_index = 0; 
		int y_index = 0; 	
		int[] index = {0, 0};
		
		// x 인덱스 구하기
		if (next.substring(0, 1).equals("u")) {
			x_index = 2 * ( Integer.valueOf(next.substring(1, 2)) - 1 );
		} 
		else if (next.substring(0, 1).equals("v") ) {
			x_index = 2 * ( Integer.valueOf(next.substring(1, 2)) ) - 1 ;
		}
		
		// y 인덱스 구하기
		if (next.substring(2, 3).equals("u") ) {
			y_index = 2 * ( Integer.valueOf(next.substring(3)) - 1 );
		} 
		else if (next.substring(2, 3).equals("v") ) {
			y_index = 2 * ( Integer.valueOf(next.substring(3)) ) - 1 ;
		}	
		index[0] = x_index;
		index[1] = y_index;
		
		return index;
	}
}
