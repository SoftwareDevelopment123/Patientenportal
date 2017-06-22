package de.patientenportal.persistence;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import de.patientenportal.entities.InstructionDoc;

public class InstructionDocDAO {

	//InstructionDoc abrufen
		public static InstructionDoc getInstructionDoc (int instructionDocid){
			Session session = HibernateUtil.getSessionFactory().openSession();
			InstructionDoc instructiondoc = new InstructionDoc();
					
			session.beginTransaction();		
			instructiondoc = (InstructionDoc)session.get(InstructionDoc.class, instructionDocid);			
			session.getTransaction().commit();
			session.close();
				
			return instructiondoc;
			}

	//InstructionDoc ändern
		public static String updateInstructionDoc(InstructionDoc updatedinstructiondoc){
			int id = updatedinstructiondoc.getInstructionDocID();
			if(id!=0){
				
				String instructionType = updatedinstructiondoc.getInstructionType();
				String title =  updatedinstructiondoc.getTitle();
				String description =  updatedinstructiondoc.getDescription();
				
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();				
				InstructionDoc instructiondoctoupdate = (InstructionDoc) session.get(InstructionDoc.class, id);
							
					instructiondoctoupdate.setInstructionType(instructionType);
					instructiondoctoupdate.setTitle(title);
					instructiondoctoupdate.setDescription(description);

				session.getTransaction().commit();
				session.close();
				return "success";
			} else {
				return "noID";
			}	
		}
		
	//InstructionDoc löschen
		
		public static String deleteInstructionDoc(int InstructionDocID){
			Session session = HibernateUtil.getSessionFactory().openSession();

			session.beginTransaction();
				InstructionDoc instructiondoc = (InstructionDoc)session.get(InstructionDoc.class, InstructionDocID);
			session.delete(instructiondoc);
			session.getTransaction().commit();
					
			session.close();
			return "success";
		}
		
		
}
