package com.appointment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "appointment")
public class AppointmentModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "patemail")
	private String patemail;
	@Column(name = "docemail")
	private String docemail;
	@Column(name = "date")
	private String date;
	@Column(name = "time")
	private String time;
	@Column(name = "flag")
	private int flag;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the patemail
	 */
	public String getPatemail() {
		return patemail;
	}
	/**
	 * @param patemail the patemail to set
	 */
	public void setPatemail(String patemail) {
		this.patemail = patemail;
	}
	/**
	 * @return the docemail
	 */
	public String getDocemail() {
		return docemail;
	}
	/**
	 * @param docemail the docemail to set
	 */
	public void setDocemail(String docemail) {
		this.docemail = docemail;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public AppointmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppointmentModel(Long id, String patemail, String docemail, String date, String time, int flag) {
		super();
		this.id = id;
		this.patemail = patemail;
		this.docemail = docemail;
		this.date = date;
		this.time = time;
		this.flag = flag;
	}
	
	

}
