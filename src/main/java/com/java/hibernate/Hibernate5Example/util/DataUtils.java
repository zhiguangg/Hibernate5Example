package com.java.hibernate.Hibernate5Example.util;

import org.hibernate.Query;
import org.hibernate.Session;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;

public class DataUtils {

   @SuppressWarnings({"deprecation", "unchecked"})
   public static Department findDepartment(Session session, String deptNo) {
       String sql = "Select d from " + Department.class.getName() + " d "//
               + " Where d.deptNo = :deptNo";
       Query<Department> query = session.createQuery(sql);
       query.setParameter("deptNo", deptNo);
       return query.getSingleResult();
   }
   
   @SuppressWarnings({"deprecation", "unchecked"})
   public static Long getMaxEmpId(Session session) {
	   String sql = "Select max(e.empId) from " + Employee.class.getName() + " e ";
	   Query<Number> query = session.createQuery(sql);
	   Number value = query.getSingleResult();
	   if(value == null) {
		   return 0L;
	   }
	   return value.longValue();
   }

   @SuppressWarnings({"deprecation", "unchecked"})
   public static Employee findEmployee(Session session, String empNo) {
       String sql = "Select e from " + Employee.class.getName() + " e "//
               + " Where e.empNo = :empNo";
       Query<Employee> query = session.createQuery(sql);
       query.setParameter("empNo", empNo);
       return query.getSingleResult();
   }  
}
