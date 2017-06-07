package smalltests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import de.patientenportal.entities.VitalData;
import de.patientenportal.entities.VitalDataType;

public class ListenTests {

	public static void main(String[] args) {
		
		VitalData vitaldata1 = new VitalData("früh",4.5,VitalDataType.BLOODSUGAR);
		VitalData vitaldata2 = new VitalData("mittag",5.0,VitalDataType.BLOODSUGAR);
		VitalData vitaldata3 = new VitalData("abend",6.5,VitalDataType.BLOODSUGAR);
		VitalData vitaldata4 = new VitalData("nachmittag",86.0,VitalDataType.WEIGHT);
		VitalData vitaldata5 = new VitalData("nachmittag",70.0,VitalDataType.HEARTRATE);
		
		List<VitalData> vitaldatas =  new ArrayList<VitalData>(Arrays.asList(vitaldata1, vitaldata2, vitaldata3, vitaldata4, vitaldata5));
			
		//folgendes geht auch
		//List<VitalData> vitaldatas = Arrays.asList(vitaldata1, vitaldata2, vitaldata3, vitaldata4, vitaldata5);
		
			//vitaldatas.add(vitaldata1);
			//vitaldatas.add(vitaldata2);
			//vitaldatas.add(vitaldata3);
			//vitaldatas.add(vitaldata4);
			//vitaldatas.add(vitaldata5);
		
		for ( int i = 0; i < vitaldatas.size(); i++)
		{
			VitalData vdata = vitaldatas.get(i);
			vdata.setVitalDataID(i+1);
			vitaldatas.set(i, vdata);
		}
		
		VitalData vd;
		
		for ( ListIterator<VitalData> it = vitaldatas.listIterator(); it.hasNext(); ){
			vd = it.next();
			System.out.println(vd.getVitalDataID() + " " + vd.getTimestamp() + " " + vd.getVitalDataType() + ": " + vd.getValue());
			System.out.println();
		}
		
		
		
		
		
	}

	
	
}
