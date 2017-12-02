package com.java.hibernate.Hibernate5Example.query;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.util.DataUtils;
import com.java.hibernate.Hibernate5Example.util.HibernateUtils;

public class PersistDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = HibernateUtils.getSessionFactory();
		
		Session session = factory.getCurrentSession();
		Department dept = null;
		Employee emp = null;
		try {
			session.getTransaction().begin();
			
			Long maxEmpId = DataUtils.getMaxEmpId(session);
			Long empId = maxEmpId + 1;
			
			//get persistent object
			dept = DataUtils.findDepartment(session, "D10");
			
			//create transient object
			emp = new Employee();
			emp.setEmpId(empId);
			emp.setEmpNo("E"+empId);
			emp.setEmpName("Name"+ empId);
			emp.setJob("Coder");
			emp.setSalary(1000f);
		    emp.setManager(null);
			emp.setHireDate(new Date());
			emp.setDepartment(dept);
		
			//using persist(..)
			//Now 'emp' is managed by Hibernate
			//it has persistent status
			//No action at this time with DB
			session.persist(emp);
			
			//At this step the data is pushed to the DB
			//Execute Insert statement
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		//After the session is closed (commit, rollback, close)
		//Objects 'emp', 'dept' became the detached objects
		//It is no longer in the control of Hibernate
		System.out.println("Emp No: "+ emp.getEmpNo());
        HibernateUtils.shutdown();
	}
}
