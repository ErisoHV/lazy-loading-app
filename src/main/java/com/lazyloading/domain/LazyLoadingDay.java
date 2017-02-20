package com.lazyloading.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erika
 *
 */
public class LazyLoadingDay {

	private List<BigDecimal> weigths;
	private Integer travelsNo;
	
	/**
	 * Empty constructor 
	 */
	public LazyLoadingDay() {
		super();
		weigths = new ArrayList<>();
		travelsNo = 0;
	}
	
	/**
	 * @param weigths
	 * @param travelNo
	 */
	public LazyLoadingDay(List<BigDecimal> weigths, Integer travelNo) {
		super();
		this.setWeigths(weigths);
		this.setTravelsNo(travelNo);
	}

	/**
	 * @return
	 */
	public List<BigDecimal> getWeigths() {
		return weigths;
	}

	/**
	 * @param weigths
	 */
	public void setWeigths(List<BigDecimal> weigths) {
		this.weigths = weigths;
	}

	/**
	 * @return the travelNo
	 */
	public Integer getTravelsNo() {
		return travelsNo;
	}

	/**
	 * @param travelNo the travelNo to set
	 */
	public void setTravelsNo(Integer travelNo) {
		this.travelsNo = travelNo;
	}
	
}
