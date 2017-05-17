package dao;

import java.io.Serializable;
import org.hibernate.Session;

import entities.*;

// funktioniert noch nicht so richtig ...

@SuppressWarnings("unused")
public class DAO <T, PK extends Serializable>{

	private Class<T> ctype;
	
	public DAO(Class<T> ctype){
		this.ctype = ctype;
	}
	
	@SuppressWarnings("unchecked")
	public T get (int i){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		//T t = new Class<T>();
		
		session.beginTransaction();
		
		ctype = (Class<T>) session.get(ctype, i);
		
		session.getTransaction().commit();
		session.close();
		
		return (T) ctype;
	}

}
