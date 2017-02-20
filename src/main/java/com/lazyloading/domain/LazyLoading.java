package com.lazyloading.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * LazyLoading Entity that manages the LazyLoading Maximization Process results
 * @author Erika
 *
 */
@Entity
@Table(name = "LAZYLOADING")
public class LazyLoading{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String fileName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@NotEmpty
	private String userId;
	private String host;

	/**
	 * Empty constructor
	 */
	public LazyLoading() {
		super();
	}
	
	/**
	 * @param name
	 * @param route
	 * @param date
	 * @param id
	 * @param host
	 */
	public LazyLoading(String name, Date date, String id, String host) {
		super();
		this.fileName = name;
		this.date = date;
		this.userId = id;
		this.host = host;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setFileName(String name) {
		this.fileName = name;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return the id
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setUserId(String id) {
		this.userId = id;
	}
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

}
