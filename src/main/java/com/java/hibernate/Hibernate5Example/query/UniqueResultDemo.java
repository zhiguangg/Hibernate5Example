package com.java.hibernate.Hibernate5Example.query;

import org.hibernate.Query;
import org.hibernate.Session;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;

public class UniqueResultDemo {

	public static Department getDepartment(Session session, String deptNo) {
		final String sql = "Select d from " + Department.class.getName() + " d where d.deptNo = :deptNo";
		Query<Department> query = session.createQuery(sql);
		query.setParameter("deptNo", deptNo);
		return (Department)query.getSingleResult();
	}
	
	public static Employee getEmployee(Session session, Long empId) {
		final String sql = "Select e from " + Employee.class.getName() + " e where e.empId = :empId";
		Query<Employee> query = session.createQuery(sql);
		query.setParameter("empId", empId);
		return (Employee)query.getSingleResult();
	}
}
