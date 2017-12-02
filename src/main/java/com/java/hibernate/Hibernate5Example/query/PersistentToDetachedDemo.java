package com.java.hibernate.Hibernate5Example.query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.util.DataUtils;
import com.java.hibernate.Hibernate5Example.util.HibernateUtils;

/*
 * An object in a Persistent condition managed by Hibernate can be turned to the state of Detached (be independent from the 
 * management of Hibernate) through the method of Session:
 */
public class PersistentToDetachedDemo {

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();

		Session session = factory.getCurrentSession();
		Employee emp = null;
        Department dept = null;

		/*
		 * 1. evict(Object) change the object form persistent to detached state
		 */
		try {
			System.out.println("1. evict(Object) change the object form persistent to detached state");
			session.getTransaction().begin();

			// this object has Persistent state
			emp = DataUtils.findEmployee(session, "E7499");

			// true
			System.out.println("-emp Persistent? " + session.contains(emp));

			// using evict() to evict a sinlge object from session
			session.evict(emp);

			// false
			System.out.println("-emp Persistent? " + session.contains(emp));

			// all changes on the 'emp' will not update if not reattach to session
			emp.setEmpNo("NEW");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		/*
		 * 2. clear() evicts all objects in the session
		 */
		session = factory.getCurrentSession();
        try {
    	   System.out.println("2. clear() evicts all objects in the session");
           session.getTransaction().begin();
 
           // It is an object has Persistent status.
           emp = DataUtils.findEmployee(session, "E7499");
           dept = DataUtils.findDepartment(session, "D10");
 
        
           // clear() evicts all the objects in the session.
           session.clear();
 
 
           // Now 'emp' & 'dept' has Detached status
           // ==> false
           System.out.println("- emp Persistent? " + session.contains(emp));
           System.out.println("- dept Persistent? " + session.contains(dept));
 
           // All change on the 'emp' will not update
           // if not reatach 'emp' to session
           emp.setEmpNo("NEW");
 
           dept = DataUtils.findDepartment(session, "D20");
           System.out.println("Dept Name = "+ dept.getDeptName());
 
           session.getTransaction().commit();
        } catch (Exception e) {
           e.printStackTrace();
           session.getTransaction().rollback();
        }
        HibernateUtils.shutdown();
	}
}
