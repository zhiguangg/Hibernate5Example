package com.java.hibernate.Hibernate5Example.query;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.entities.Timekeeper;
import com.java.hibernate.Hibernate5Example.util.DataUtils;
import com.java.hibernate.Hibernate5Example.util.HibernateUtils;

public class TransientToPersistentDemo {
	/*
	 * 1. transient -> persistent (save(), saveOrUpdate(), persist(), merge())
	 */

	public static void main(String[] args) {
		
		SessionFactory factory = HibernateUtils.getSessionFactory();
		
		Session session = factory.getCurrentSession();
		Department dept = null;
		Employee emp = null;
				
		try {
			session.getTransaction().begin();
			
			//persistent object
			dept = DataUtils.findDepartment(session, "D10");
			
			System.out.println("first change location");
			dept.setLocation("Chicago" + System.currentTimeMillis());
			System.out.println("-Location = " + dept.getLocation());
			System.out.println("calling flush...");
			//use session.flush() to actively push changes to DB. It works for all changed persistent object
			session.flush();
			System.out.println("-Flush OK");
			//change sth on persistent object
			dept.setLocation("Chicago" + System.currentTimeMillis());
			//print out location
			System.out.println("-Location = " + dept.getLocation());
			System.out.println("-Calling commit...");
			//commit
			session.getTransaction().commit();
			
			System.out.println("-Commit OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		//create session after it had been closed earlier 
		//caused by commit or update
		session = factory.getCurrentSession();
		
		try {
			session.getTransaction().begin();

			System.out.println("-Finding Department deptNo = D10");
			dept = DataUtils.findDepartment(session, "D10");
			
			System.out.println("-D10 Location=" + dept.getLocation());
			
			session.getTransaction().commit();
	
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		/*
		 * 2. transient -> persistent Using persist(Object)
		 */
		session = factory.getCurrentSession();
		try {
			System.out.println("2. transient -> persistent Using persist(Object)");
			session.getTransaction().begin();
			
			emp = DataUtils.findEmployee(session, "E7499");
			 
	        persist_Transient_persist(session, emp);
			session.getTransaction().commit();
	
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		/*
		 * 3. transient -> persistent Using save(Object)
		 */
		session = factory.getCurrentSession();
		try {
			System.out.println("3. transient -> persistent Using save(Object)");
			session.getTransaction().begin();
			
			emp = DataUtils.findEmployee(session, "E7499");
			persist_Transient_save(session, emp);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		/*
		 * 4. transient -> persistent Using saveOrUpdate(Object)
		 */
		session = factory.getCurrentSession();
		try {
			System.out.println("4. transient -> persistent Using saveOrUpdate(Object)");
			session.getTransaction().begin();
			
			emp = DataUtils.findEmployee(session, "E7499");
			persist_Transient_saveOrUpdate(session, emp);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		/*
		 * 5. Transient --> Persistent : Using merge(Object)
		 */
		session = factory.getCurrentSession();
		try {
			System.out.println("5. transient -> persistent Using merge(Object)");
			session.getTransaction().begin();
			
			emp = DataUtils.findEmployee(session, "E7499");
			
			persist_Transient_merge(session, emp);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
        HibernateUtils.shutdown();
	}
	
	//2.
	private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static Timekeeper persist_Transient_persist(Session session, Employee emp) {
		
		Timekeeper tk1 = new Timekeeper();
		
		tk1.setEmployee(emp);
		tk1.setInOut(Timekeeper.IN);
		tk1.setDateTime(new Date());
		
	    // Now, 'tk1' is transient object
	    System.out.println("- tk1 Persistent? " + session.contains(tk1));
	 
	    System.out.println("====== CALL persist(tk).... ===========");
		
		// Hibernate assign value to Id of 'tk1'
		// No action to DB.
		session.persist(tk1);
	    System.out.println("- tk1.getTimekeeperId() = " + tk1.getTimeKeeperId());
	    
	    System.out.println("- Call flush");
	    session.flush();
	    
	    String timekeeperId = tk1.getTimeKeeperId();
	    System.out.println("- timekeeperId = " + timekeeperId);
	    System.out.println("- inOut = " + tk1.getInOut());
	    System.out.println("- dateTime = " + dateFormat.format(tk1.getDateTime()));
	    System.out.println();
		
		return tk1;
	}
	
	//3 
	private static Timekeeper persist_Transient_save(Session session, Employee emp) {
		//create an Object, Transient state
		Timekeeper tk2 = new Timekeeper();
		tk2.setEmployee(emp);
		tk2.setDateTime(new Date());
		tk2.setInOut(Timekeeper.IN);
		
		// now tk2 is in Transient state
		System.out.println("- tk2 Persistent? " + session.contains(tk2));
		
		System.out.println("-Call save(tk)");
		// save() very similar to persist()
		// save() return ID, persist() return void
		// Hibernate assign an Id to tk2
		// No action on DB, and return ID of "tk2"
		Serializable id = session.save(tk2);
		
		System.out.println("-id = " + id);
		
		System.out.println("-tk2.getTimekeperId() = " + tk2.getTimeKeeperId());
		
		// now tk2 has Persistent state
		// it has been managed in Session
		System.out.println("- tk2 Persistent? " + session.contains(tk2));
		
		System.out.println("-Call flush()...");
		
		// To push data to DB, call flush()
		// If flush() is not called, data will be pushed to DB when calling commit(), (by executing insert statement
		session.flush();
		
		String id2 = tk2.getTimeKeeperId();
		System.out.println("- timekeeperId = " + id2);
		System.out.println("- inOut = " + tk2.getInOut());
		System.out.println("- dateTime = " + dateFormat.format(tk2.getDateTime()));
		System.out.println();
		
		return tk2;
	}
	
	//4
	private static Timekeeper 	persist_Transient_saveOrUpdate(Session session, Employee emp) {
		// create an Oject, Transient state
		Timekeeper tk3 = new Timekeeper();
		tk3.setEmployee(emp);
		tk3.setDateTime(new Date());
		tk3.setInOut(Timekeeper.IN);

	    // Now 'tk3' are state Transient.
	    System.out.println("- tk3 Persistent? " + session.contains(tk3));
	 
	    System.out.println("====== CALL saveOrUpdate(tk).... ===========");
	    // Here Hibernate checks if tk4 has Id or not. If not assign automatically
	    session.saveOrUpdate(tk3);
	    
	    System.out.println("- tk3.getTimekeeperId() = " + tk3.getTimeKeeperId());

		// Now 'tk3' has Persistent state
		// It has been managed in Session.
		// But no action insert, or update to DB.
		// ==> true
		System.out.println("- tk3 Persistent? " + session.contains(tk3));
	    
		System.out.println("- Call flush..");
		 
        // To push data into the DB, call flush().
        // If not call flush() data will be pushed to the DB when calling commit().
        // Now possible to Insert or Update DB. (!!!)
        // Depending on the ID of 'tk3' exists in the DB or not
        session.flush();
       
        String timekeeperId = tk3.getTimeKeeperId();
        System.out.println("- timekeeperId = " + timekeeperId);
        System.out.println("- inOut = " + tk3.getInOut());
        System.out.println("- dateTime = " + dateFormat.format(tk3.getDateTime()));
        System.out.println();
        
	    return tk3;
	}
	
	//5 * note in this example, tk4 still has not ID assigned after merge()
	private static Timekeeper persist_Transient_merge(Session session, Employee emp) {
		// create an Object, Transient state
		Timekeeper tk4 = new Timekeeper();
		tk4.setEmployee(emp);
		tk4.setInOut(Timekeeper.IN);
		tk4.setDateTime(new Date());
		
		System.out.println("- tk4 Persistent state? " + session.contains(tk4));
		
	    System.out.println("====== CALL merge(tk).... ===========");
	    // Here Hibernate checks if tk4 has ID or not, if not, Hibernate assigns a new value to ID of tk4Copy
	    // return copy of tk4
	    Timekeeper tk4Copy = (Timekeeper)session.merge(tk4);
	    System.out.println("- tk4.getTimekeeperId() = " + tk4.getTimeKeeperId());
	    System.out.println("- tk4Copy.getTimekeeperId() = " + tk4Copy.getTimeKeeperId());	    
	    // 'tk4Copy' has Persistent status
	    // ==> true
	    System.out.println("- tk4Copy Persistent? " + session.contains(tk4Copy));
	 
	    System.out.println("- Call flush..");
	    // This time insert or update to DB
	    session.flush();
	    
	    // 'tk4' still Transient, after flush().
	    // merge(..) safer than saveOrUpdate().
	    System.out.println("- tk4 Persistent? " + session.contains(tk4));
	 
	    //
	    String timekeeperId = tk4.getTimeKeeperId();
        System.out.println("- timekeeperId = " + timekeeperId);
        System.out.println("- inOut = " + tk4.getInOut());
        System.out.println("- dateTime = " + dateFormat.format(tk4.getDateTime()));
        System.out.println();
        return tk4;
    
	}
}
