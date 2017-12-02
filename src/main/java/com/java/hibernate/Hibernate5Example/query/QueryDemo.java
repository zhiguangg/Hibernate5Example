package com.java.hibernate.Hibernate5Example.query;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.java.hibernate.Hibernate5Example.beans.ShortEmpInfo;
import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.util.DbUtils;
import com.java.hibernate.Hibernate5Example.util.HibernateUtils;

public class QueryDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = HibernateUtils.getSessionFactory();
		
		Session session = factory.getCurrentSession();

		try {
			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			//This population is for in memory h2, will try file or server mode later 11/29/2017
			//DbUtils.populateTables(session);

			/*
			 * 	Many ways to query data in Hibernate 
			 *  1. Query Object using HQL. Omitted since plenty of usage in populateTables() method.
			 *  2. Query Object using HQL w/ params
			 */
			String sql2 = "Select e from "+Employee.class.getName() + " e where e.department.deptNo=:deptNo ";
			//create query object
			@SuppressWarnings("unchecked")
			Query<Employee> query = session.createQuery(sql2);
			query.setParameter("deptNo", "D10");
			
			//Execute query
			List<Employee> employees = query.getResultList();
			
			for (Employee e : employees) {
				System.out.println("Emp: " + e.getEmpNo() + " : " + e.getEmpName());
			}
			
			/*
			 *  3. Query a few columns using HQL
			 */
			String sql3 = "Select e.empId, e.empNo, e.empName from " + Employee.class.getName() + " e ";
			//create query object
			Query<Object[]> query3 = session.createQuery(sql3);
			//execute query, get the array of object
			List<Object[]> lists = query3.getResultList();
			System.out.println("------------- query a few columns --------------------");
			for (Object[] emp : lists) {
				System.out.println("Emp Id:" + emp[0] + " Emp No:" + emp[1] + " Emp Name:" + emp[2]);
			}
			System.out.println("------------- query a few columns --------------------");
			
			/*
			 *  4. Query a few columns using HQL & JavaBean.
			 *  ShortEmpInfo.java in the beans folder
			 */
			String sql4 = "Select new "+ShortEmpInfo.class.getName() +"(e.empId, e.empNo, e.empName) from " + Employee.class.getName() + " e ";
			@SuppressWarnings("unchecked")
			Query<ShortEmpInfo> query4 = session.createQuery(sql4);
			List<ShortEmpInfo> empList = query4.getResultList();
			System.out.println("------------- query a few columns w/ JavaBean--------------------");
			for (ShortEmpInfo emp : empList) {
				System.out.println("Emp Id:" + emp.getEmpId() + " Emp No:" + emp.getEmpNo() + " Emp Name:" + emp.getEmpName());
			}
			System.out.println("------------- query a few columns w/ JavaBean--------------------");
			
			/*
			 *  5. Query returns unique result
			 */
			Department department = UniqueResultDemo.getDepartment(session, "D20");
			System.out.println("------------- query return unique result--------------------");
			System.out.println("Dept Name: " + department.getDeptName() + " DeptNo: " + department.getDeptNo());
			Set<Employee> emps = department.getEmployees();
			System.out.println("size of emps set: " + emps.size());
			for (Employee e : emps){
				System.out.println("  Emp Id: " + e.getEmpId() + " Emp No: " + e.getEmpNo() + " Emp Name: " + e.getEmpName());
			}
			Employee employee = UniqueResultDemo.getEmployee(session, new Long(7934));
			System.out.println("Emp Name: " + employee.getEmpName() + " EmpNo: " + employee.getEmpNo());
			// Commit data.
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
		}
        HibernateUtils.shutdown();
	}
}