package com.lazyloading.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lazyloading.domain.LazyLoadingDay;

/**
 * This class manages the process to maximize the Lazy Loading per day
 * @author Erika
 *
 */
@Service
public class LazyDataProcessService {
	
	public static final double MIN_WEIGHT = 50.0;

	private List<LazyLoadingDay> lazyDaysData = new ArrayList<>();
	private Integer daysNo;
	
	/**
	 * @return
	 */
	public List<LazyLoadingDay> getLazyDaysData() {
		return lazyDaysData;
	}


	/**
	 * @param lazyDaysData
	 */
	public void setLazyDaysData(List<LazyLoadingDay> lazyDaysData) {
		this.lazyDaysData = lazyDaysData;
	}
	
	/**
	 * @return the daysNo
	 */
	public Integer getDaysNo() {
		return daysNo;
	}


	/**
	 * @param daysNo the daysNo to set
	 */
	public void setDaysNo(Integer daysNo) {
		this.daysNo = daysNo;
	}


	/**
	 * @param day
	 */
	public void addLazyLoadingDay(LazyLoadingDay day){
		lazyDaysData.add(day);
	}
	
	
	/**
	 * Load the data in lazyDaysData structure
	 * @param data
	 */
	public void initLazyData(List<BigDecimal> data) {
		if (!data.isEmpty()){
			lazyDaysData.clear();
			daysNo = data.get(0).intValueExact();
			data.remove(0);

			for (int i = 0; i < daysNo; i++){
				LazyLoadingDay dayLoadingItems = new LazyLoadingDay();
				int weightsNo = data.get(0).intValueExact();
				data.remove(0);
				
				List<BigDecimal> dayItems = new ArrayList<>();
				
				while(weightsNo > 0){
					dayItems.add(data.get(0));
					data.remove(0);
					weightsNo--;
				}
				if (!dayItems.isEmpty()) {
					
					Collections.sort(dayItems, Collections.reverseOrder());
					dayLoadingItems.setWeigths(dayItems);
					
					lazyDaysData.add(dayLoadingItems);
				}
			}
		}
	}
	
	
	/**
	 * @param majorElement
	 * @param totalElements
	 * @return
	 */
	public static boolean isMaximunFakeWeigth(BigDecimal majorElement, int totalElements){
		return majorElement.multiply(BigDecimal.valueOf(totalElements))
					.compareTo(BigDecimal.valueOf(MIN_WEIGHT)) >= 0;
	}	
	
	/**
	 * 
	 * @return return true if at least one travel is calculated 
	 */
	public boolean maximizeLazyLoading() {
		boolean calculated = false;
		if (!lazyDaysData.isEmpty()) {
			
			for(LazyLoadingDay dayList : lazyDaysData){
				BigDecimal actualWeightTravel = BigDecimal.ZERO;
				BigDecimal majorElement = BigDecimal.ZERO;
				int i = 0;
				
				List<BigDecimal> weigthList = new ArrayList<>(dayList.getWeigths());
				
				for (i = 0; i < weigthList.size(); i++){
					majorElement = weigthList.get(i);
					
					actualWeightTravel = actualWeightTravel.add(weigthList.get(i));
					weigthList.remove(i);
					int totalElements = 1;
					boolean completed = false;
					
						for(int j = weigthList.size() - 1; j >= -1 && !completed; j--){
							if (isMaximunFakeWeigth(majorElement, totalElements)){ 
								actualWeightTravel = BigDecimal.ZERO;
								dayList.setTravelsNo(dayList.getTravelsNo() + 1);
								totalElements = 0;
								completed = true;
								calculated = true;
							}
							else if (j >= 0){
								actualWeightTravel = actualWeightTravel.add(weigthList.get(j));
								weigthList.remove(j);	
								totalElements++;
							}
						}
						i = -1;
				}
			}
		}
		return calculated;
	}
	
	/**
	 * @return
	 */
	public List<Integer> getTravelsQuantity(){
		List<Integer> travelsQty = new ArrayList<>();
		for(LazyLoadingDay dayList : lazyDaysData){
			travelsQty.add(dayList.getTravelsNo());
		}
		return travelsQty;
	}
	
    /**
     * @param files
     * @throws IOException
     */
    public boolean calculateLazyLoading(List<BigDecimal> lazyData) throws IOException {
            initLazyData(lazyData);
            return maximizeLazyLoading();
    }
	
    
    public String toString(){
    	List<Integer> travelsQty = getTravelsQuantity();
    	StringBuilder tostring = new StringBuilder();
    	int caseNo = 1;
    	for (Integer qty : travelsQty){
    		tostring.append("Case #").append(caseNo).append(": ").append(qty);
    		tostring.append(System.getProperty("line.separator"));
    		caseNo++;
    	}
    	return tostring.toString();
    }

}
