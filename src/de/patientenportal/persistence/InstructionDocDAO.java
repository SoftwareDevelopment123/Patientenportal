package de.patientenportal.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import de.patientenportal.entities.InstructionDoc;
import de.patientenportal.entities.User;
import de.patientenpotal.ftpconnection.FtpMethodenInDocs;
import de.patientenpotal.ftpconnection.FtpMethodenMDocs;


public class InstructionDocDAO {

	// InstructionDoc anlegen
		public static String createInstructionDoc(InstructionDoc newInstructionDoc) {
			Session session = HibernateUtil.getSessionFactory().openSession();

			try{
			session.beginTransaction();
			session.save(newInstructionDoc);
			session.getTransaction().commit();
			FtpMethodenInDocs.uploadInstructionDoc(newInstructionDoc);
			
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return "error";
			} finally {
				session.close();
			}
			return "success";
		}
	
	
	//InstructionDoc abrufen
		public static InstructionDoc getInstructionDoc (int instructionDocid){
			Session session = HibernateUtil.getSessionFactory().openSession();
			InstructionDoc instructiondoc = new InstructionDoc();
			
			try{
			session.beginTransaction();		
			instructiondoc = (InstructionDoc)session.get(InstructionDoc.class, instructionDocid);			
			session.getTransaction().commit();
			session.close();
			
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return null;
			} finally {
				session.close();
			}
				
			return instructiondoc;
			}

	//InstructionDoc ändern
		public static String updateInstructionDoc(InstructionDoc updatedinstructiondoc){
			int id = updatedinstructiondoc.getInstructionDocID();
			if(id!=0){
				
				String instructionType = updatedinstructiondoc.getInstructionType();
				String title =  updatedinstructiondoc.getTitle();
				String description =  updatedinstructiondoc.getDescription();
				String filetype = updatedinstructiondoc.getFileType();
				
				
				Session session = HibernateUtil.getSessionFactory().openSession();
				
				try{
				session.beginTransaction();				
				InstructionDoc instructiondoctoupdate = (InstructionDoc) session.get(InstructionDoc.class, id);
					
				
					instructiondoctoupdate.setInstructionType(instructionType);
					instructiondoctoupdate.setTitle(title);
					instructiondoctoupdate.setDescription(description);
					instructiondoctoupdate.setFileType(filetype);

				session.getTransaction().commit();
				} catch (Exception e) {
					System.err.println("Error: " + e);
					return "error";
				} finally {
					session.close();
				}
				return "success";
			} else {
				return "noID";
			}	
		}
		
	//InstructionDoc löschen
		
		public static String deleteInstructionDoc(int InstructionDocID){
			Session session = HibernateUtil.getSessionFactory().openSession();

			try{
			session.beginTransaction();
				InstructionDoc instructiondoc = (InstructionDoc)session.get(InstructionDoc.class, InstructionDocID);
			session.delete(instructiondoc);
			session.getTransaction().commit();
			
			} catch (Exception e) {
				System.err.println("Error: " + e);
				return "error";
			} finally {
				session.close();
			}
				
			return "success";
		}
	
	@SuppressWarnings("unchecked")
	public static List<InstructionDoc> getAllIDocs(){
		Session session = HibernateUtil.getSessionFactory().openSession(); 
		
		List<InstructionDoc> docs = new ArrayList<InstructionDoc>();
		try{
		session.beginTransaction();
		docs = session.createQuery("FROM instructiondoc").list(); 
	    session.getTransaction().commit();				
	    
		} catch (Exception e) {
			System.err.println("Error: " + e);
			return null;
		} finally {
			session.close();
		}
	    return docs;
	}
		
}
