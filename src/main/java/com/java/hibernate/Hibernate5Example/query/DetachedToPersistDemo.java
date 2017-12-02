package com.java.hibernate.Hibernate5Example.query;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.util.DataUtils;
import com.java.hibernate.Hibernate5Example.util.HibernateUtils;

public class DetachedToPersistDemo {

	/*
	 * An object detached from the management of Hibernate can be re-attached through some methods of Session:...
	 *		update(Object)
	 *		saveOrUpdate(Object)
	 *		merge(Object)
	 *		refresh(Object)
	 *		lock(Object)
	 */
	
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
     
		// 1. detached to persistent : Using update(Object)
		System.out.println("detached to persistent : Using update(Object)");
		Session session1 = factory.getCurrentSession();
		Employee emp = null;
		try {
			session1.getTransaction().begin();
 
			// This is a Persistent object.
			emp = DataUtils.findEmployee(session1, "E7499");
 
			// session1 was closed after a commit is called.
			session1.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session1.getTransaction().rollback();
		}
 
		// Open other session
		Session session2 = factory.getCurrentSession();
 
		try {
			session2.getTransaction().begin();
 
			// Check state of 'emp'
			// ==> false
			System.out.println("- emp Persistent? " + session2.contains(emp));
 
			System.out.println("Emp salary: " + emp.getSalary());
			emp.setSalary(emp.getSalary() + 100);
          
			// *** update (..) is only used for Detached object.
			// (Not for Transient object).
			// Use the update (emp) to bring back emp Persistent state.            
			session2.update(emp);
  
			// Call flush
			// Update statement will be called.
			session2.flush();
 
			System.out.println("Emp salary after update: " + emp.getSalary());
 
			// session2 was closed after a commit is called.
			session2.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session2.getTransaction().rollback();
		}	
		
		/*
		 * 2. detached to persistent : Using saveOrUpdate(Object)
		 */
		
		/*
		 * 3. detached to persistent : Using merge(Object)
		 */
		
		/*
		 * 4. detached to persistent : Using refresh(Object)
		 */
		Session  session3 = factory.getCurrentSession();
		Employee emp1 = null;
		try {
			System.out.println(" -------- detached to persistent : Using refresh(Object)----------");
			session3.getTransaction().begin();
			
			emp1 = DataUtils.findEmployee(session3, "E7499");
			
			//true
			System.out.println(" - emp Persistent? " + session3.contains(emp1));
			
	        session3.evict(emp1);
	        
			// false, emp1 in Detached state
			System.out.println(" - emp Persistent? " + session3.contains(emp1));
	        System.out.println(" - Emp salary before update: " + emp1.getSalary());
			
	        //set new salary
	        emp1.setSalary(emp1.getSalary() + 100);
	        
	        //refresh: make a query statement and switch 'emp1' to Persistent state, changes are ignored
	        session3.refresh(emp1);
	        
	        System.out.println(" - emp Persistent? " + session3.contains(emp1));
	        System.out.println(" - Emp salary after update: " + emp1.getSalary());
	        
			session3.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session3.getTransaction().rollback();
		}
		
		/*
		 * 5. Detached --> Persistent : Using lock(Object)
		 */
		session3 = factory.getCurrentSession();
		emp1 = null;
		try {
			System.out.println(" -------- detached to persistent : Using lock(Object)----------");
			session3.getTransaction().begin();
			
			emp1 = DataUtils.findEmployee(session3, "E7499");
			
			//true
			System.out.println(" - emp Persistent? " + session3.contains(emp1));
			
	        session3.evict(emp1);
	        
			// false, emp1 in Detached state
			System.out.println(" - emp Persistent? " + session3.contains(emp1));
	        System.out.println(" - Emp salary before update: " + emp1.getSalary());
			
	        //set new salary
	        emp1.setSalary(emp1.getSalary() + 100);
	        
	        System.out.println("-Call lock(Object)...");
	        session3.buildLockRequest(LockOptions.NONE).lock(emp1);
			
	        // true, emp1 in Persistent state
	        System.out.println(" - emp Persistent? " + session3.contains(emp1));
	     	System.out.println(" - Emp salary after update: " + emp1.getSalary());
	     			
			session3.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session3.getTransaction().rollback();
		}
				
		HibernateUtils.shutdown();
	}
}
