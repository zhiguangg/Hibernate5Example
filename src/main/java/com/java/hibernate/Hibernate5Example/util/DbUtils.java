package com.java.hibernate.Hibernate5Example.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.java.hibernate.Hibernate5Example.entities.Department;
import com.java.hibernate.Hibernate5Example.entities.Employee;
import com.java.hibernate.Hibernate5Example.entities.SalaryGrade;

public final class DbUtils {

	public static void populateTables(Session session) {

		/*		Populate department table		*/
		Department department1 = new Department(10, "ACCOUNTING", "NEW YORK");
		session.save(department1);
		Department department2 = new Department(20, "RESEARCH", "DALLAS 10");
		session.save(department2);
		Department department3 = new Department(30, "SALES", "CHICAGO");
		session.save(department3);
		Department department4 = new Department(40, "OPERATIONS", "BOSTON");
		session.save(department4);

		//check after populate department table
		 String sql = "Select d from " + Department.class.getName() + " d "
		 + " order by d.deptName, d.deptNo ";
		// Create Query object.
		Query<Department> query1 = session.createQuery(sql);
		// Execute query.
		List<Department> departments = query1.getResultList();			
		System.out.println("----------------------------Department Table ----------------------------------");
		for (Department d : departments) {
			System.out.println("Dept: " + d.getDeptNo() + " : " + d.getDeptName());
		}
		System.out.println("----------------------------Department Table ----------------------------------");
		
		//------------populate Employee table -------------------------//
		//1
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 17);
		Date date1 = cal.getTime();
		Employee emp1 = new Employee(new Long(7839),"KING", "PRESIDENT", null, date1, new Float(5000.0), department1);
		session.save(emp1);
		//2
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, 2);
		date1 = cal.getTime();
		Employee emp2 = new Employee(new Long(7566),"JONES", "MANAGER", emp1, date1, new Float(2975.0), department2);
		session.save(emp2);
		//3
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		date1 = cal.getTime();
		Employee emp3 = new Employee(new Long(7902),"FORD", "ANALYST", emp2, date1, new Float(3000.0), department2);
		session.save(emp3);
		//4
		cal.set(Calendar.YEAR, 1980);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 17);
		date1 = cal.getTime();
		Employee emp4 = new Employee(new Long(7369),"SMITH", "CLERK", emp3, date1, new Float(800.0), department2);
		session.save(emp4);
		//5
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		date1 = cal.getTime();
		Employee emp5 = new Employee(new Long(7698),"BLAKE", "MANAGER", emp1, date1, new Float(2850.0), department3);
		session.save(emp5);
		//6
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 20);
		date1 = cal.getTime();
		Employee emp6 = new Employee(new Long(7499),"ALLEN", "SALESMAN", emp5, date1, new Float(1600.0), department3);
		session.save(emp6);
		//7
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 22);
		date1 = cal.getTime();
		Employee emp7 = new Employee(new Long(7521),"WARD", "SALESMAN", emp5, date1, new Float(1250.0), department3);
		session.save(emp7);
		//8
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 28);
		date1 = cal.getTime();
		Employee emp8 = new Employee(new Long(7654),"MARTIN", "SALESMAN", emp5, date1, new Float(1250.0), department3);
		session.save(emp8);
		//9
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.JUNE);
		cal.set(Calendar.DAY_OF_MONTH, 9);
		date1 = cal.getTime();
		Employee emp9 = new Employee(new Long(7782),"CLARK", "MANAGER", emp1, date1, new Float(2450.0), department2);
		session.save(emp9);
		//10
		cal.set(Calendar.YEAR, 1987);
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, 19);
		date1 = cal.getTime();
		Employee emp10 = new Employee(new Long(7788),"SCOTT", "ANALYST", emp2, date1, new Float(3000.0), department3);
		session.save(emp10);
		//11
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 8);
		date1 = cal.getTime();
		Employee emp11 = new Employee(new Long(7844),"TURNER", "SALESMAN", emp5, date1, new Float(1500.0), department2);
		session.save(emp11);
		//12
		cal.set(Calendar.YEAR, 1987);
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		date1 = cal.getTime();
		Employee emp12 = new Employee(new Long(7876),"ADAMS", "CLERK", emp5, date1, new Float(1100.0), department3);
		session.save(emp12);
		//13
		cal.set(Calendar.YEAR, 1981);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		date1 = cal.getTime();
		Employee emp13 = new Employee(new Long(7900),"ADAMS", "CLERK", emp5, date1, new Float(950.0), department1);
		session.save(emp13);
		//14
		cal.set(Calendar.YEAR, 1982);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		date1 = cal.getTime();
		Employee emp14 = new Employee(new Long(7934),"MILLER", "CLERK", emp1, date1, new Float(1300.0), department2);
		session.save(emp14);
		
		//check after populate employee table
		String sql2 = "Select e from "+Employee.class.getName() + " e order by e.empName, empNo";
		Query<Employee> query2 = session.createQuery(sql2);
		// Execute query.
		List<Employee> employees = query2.getResultList();			
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("----------------------------Employee Table ----------------------------------");
		for (Employee e : employees) {
			String manager = e.getManager() == null? "null":e.getManager().getEmpName();
			System.out.println("Emp: " + e.getEmpName() + " : " + e.getEmpNo() + " Hire_Date: " + dateFormat.format(e.getHireDate())
			+ " Job: " + e.getJob() + " Dept: " + e.getDepartment().getDeptName() + " Supervisor: " + manager
							+ " Salary: " + e.getSalary());
		}
		System.out.println("----------------------------Employee Table ----------------------------------");
		
		//-------------------  populate salary_grade table  ---------------------------------//
		SalaryGrade salaryGrade = new SalaryGrade(1, new Float(9999), new Float(3001));
		session.saveOrUpdate(salaryGrade);
		//check after
		String sql3 = "Select s from " + SalaryGrade.class.getName() + " s ";
		Query<SalaryGrade> query3 = session.createQuery(sql3);
		List<SalaryGrade> salaryGrades = query3.getResultList();
		System.out.println("----------------------------Salary_Grade Table ----------------------------------");
		for (SalaryGrade s : salaryGrades) {
			System.out.println("grade: "+ s.getGrade()+ " high salary: "+s.getHighSalary()+ " low salary: "+s.getLowSalary());
		}
		System.out.println("----------------------------Salary_Grade Table ----------------------------------");
	}
}