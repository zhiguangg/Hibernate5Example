package com.java.hibernate.Hibernate5Example.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TIMEKEEPER")
public class Timekeeper {
	public static final char IN = 'I';
	public static final char OUT = 'O';
	
	private String timeKeeperId;
	private Date dateTime;
	private char inOut;
	private Employee employee;
	
	public Timekeeper() {
	}
	
	public Timekeeper(String timeKeeperId, Date dateTime, char inOut) {
		this.timeKeeperId = timeKeeperId;
		this.dateTime = dateTime;
		this.inOut = inOut;
	}

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	@Column(name="Timekeeper_Id", length=36)
	public String getTimeKeeperId() {
		return timeKeeperId;
	}

	public void setTimeKeeperId(String timeKeeperId) {
		this.timeKeeperId = timeKeeperId;
	}

	@Column(name="DATE_TIME", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Column(name="IN_OUT", nullable=false)
	public char getInOut() {
		return inOut;
	}

	public void setInOut(char inOut) {
		this.inOut = inOut;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EMP_ID")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
