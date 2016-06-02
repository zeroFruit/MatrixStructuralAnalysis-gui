package Member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class trussLocalMatrix extends localMatrix{
	public trussLocalMatrix(){
		super();
	}
	// calculate coefficient matrix
	@Override
	public HashMap<String, Double> setup_trussCoefficientMatrix(trussProperty property, int node_start, int node_end){
		HashMap<String, Double> Coefficient = new HashMap<String, Double>();
		double rad = property.get_angle() * (Math.PI / 180);
		double SIN = Math.sin(rad);
		double COS = Math.cos(rad);

		double a = property.get_A();
		double e = property.get_E();
		double l = property.get_L();
		double k = (a*e) / l ;
				
		Coefficient.put("u" + node_start + "u" + node_start, k*COS * COS ); 	// (1, 1)
		Coefficient.put("v" + node_start + "u" + node_start, k*COS * SIN ); 		// (1, 2)
		Coefficient.put("u" + node_end + "u" + node_start, -k*COS * COS ); 	// (1, 3)
		Coefficient.put("v" + node_end + "u" + node_start, -k*COS * SIN ); 	// (1, 4)
			
		Coefficient.put("u" + node_start + "v" + node_start, k*COS * SIN ); 		// (2, 1)
		Coefficient.put("v" + node_start + "v" + node_start, k*SIN * SIN ); 		// (2, 2)
		Coefficient.put("u" + node_end + "v" + node_start, -k*COS * SIN );		// (2, 3)
		Coefficient.put("v" + node_end + "v" + node_start, -k*SIN * SIN ); 		// (2, 4)
		
		Coefficient.put("u" + node_start + "u" + node_end, -k*COS * COS ); 	// (3, 1)
		Coefficient.put("v" + node_start + "u" + node_end, -k*COS * SIN ); 	// (3, 2)
		Coefficient.put("u" + node_end + "u" + node_end, k*COS * COS ); 	// (3, 3)
		Coefficient.put("v" + node_end + "u" + node_end, k*COS * SIN );		// (3, 4)
			
		Coefficient.put("u" + node_start + "v" + node_end, -k*COS * SIN ); 	// (4, 1)
		Coefficient.put("v" + node_start + "v" + node_end, -k*SIN * SIN ); 		// (4, 2)
		Coefficient.put("u" + node_end + "v" + node_end, k*COS * SIN ); 		// (4, 3)
		Coefficient.put("v" + node_end + "v" + node_end, k*SIN * SIN ); 		// (4, 4)
		
		/**********************************************************************************
		 * TEST
		 **********************************************************************************/
		System.out.println();
		System.out.println();
		System.out.println("NODE START - " + node_start);
		System.out.println("NODE END - "+ node_end);
		System.out.println();
		System.out.println();
		System.out.println("  SIN = " + SIN + "  COS = " + COS + "  RAD = " + rad);
		System.out.println();
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("                    LOCAL MATRIX                  ");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("     KEY		VALUE		");
		
		Set<String> keyset = Coefficient.keySet();
		Iterator<String> it = keyset.iterator();
		while (it.hasNext()) {
			String key = it.next();
			double value = Coefficient.get(key);
			System.out.println();
			System.out.println("   "+ key +"    		" + value + "		");
			System.out.println();
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println();
		
		/**********************************************************************************
		 * TEST
		 **********************************************************************************/
		
		return Coefficient;
	}
}
