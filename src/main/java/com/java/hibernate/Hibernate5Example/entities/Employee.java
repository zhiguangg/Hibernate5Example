package com.java.hibernate.Hibernate5Example.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="EMPLOYEE", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMP_NO"})})
public class Employee {

	private Long empId;
	private String empName;
	private String empNo;
	private Date hideDate;
	private byte[] image;
	private String job;
	private Float salary;
	private Employee manager;
	private Department department;
	private Set<Employee> employee = new HashSet<Employee>(0);
	
	public Employee() {
	}
	
	public Employee(Long empId, String empName, String job, Employee manager, Date hideDate, Float salary, Department department) {
	    this.empId = empId;
	    this.empNo = "E" + this.empId;
	    this.empName = empName;
	    this.job = job;
	    this.manager = manager;
	    this.hideDate = hideDate;
	    this.salary = salary;
	    this.department = department;	
	}
	@Id
	@Column(name="EMP_ID")
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	@Column(name="EMP_NAME", length= 50, nullable=false)
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Column(name="EMP_NO", length= 20, nullable=false)
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	@Column(name="HIRE_DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	public Date getHireDate() {
		return hideDate;
	}
	public void setHireDate(Date hireDate) {
		this.hideDate = hireDate;
	}
	@Column(name="IMAGE", length=111111, nullable=true)
	@Lob
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	@Column(name="JOB", length= 30, nullable=false)
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	@Column(name="SALARY", nullable=false)
	public Float getSalary() {
		return salary;
	}
	public void setSalary(Float salary) {
		this.salary = salary;
	}
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MNG_ID")
	public Employee getManager() {
		return manager;
	}
	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEPT_ID", nullable=false)
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="empId")
	public Set<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
	}
	
	
}
