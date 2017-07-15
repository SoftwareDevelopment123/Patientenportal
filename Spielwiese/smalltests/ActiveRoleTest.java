package smalltests;

import java.util.ArrayList;
import java.util.List;

import de.patientenportal.entities.ActiveRole;

public class ActiveRoleTest {

	public static void main(String[] args) throws Exception {
	
	ActiveRole role = ActiveRole.Doctor;
	
	List<ActiveRole> expected =  new ArrayList<ActiveRole>();
		expected.add(ActiveRole.Doctor);
		expected.add(ActiveRole.Relative);
		
	System.out.println(!expected.contains(role));
		
	
	}
}
