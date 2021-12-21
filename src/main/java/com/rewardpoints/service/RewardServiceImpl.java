package com.rewardpoints.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rewardpoints.dto.Reward;
import com.rewardpoints.model.CustomerReward;
import com.rewardpoints.repository.CustomerRewardsRepository;

@Service
public class RewardServiceImpl {
	@Autowired
	private CustomerRewardsRepository customerRewardsRepository;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
	@Transactional
	public double getAllPointsAwardedByEndDate(Date endDate) {
		List<CustomerReward> result = new ArrayList<>();
		double totalPointsAwarded = 0;
		result = customerRewardsRepository.getAllTransactionsByEndDate(endDate);
		//for(CustomerReward cr : result) totalPointsAwarded+= cr.getPointsEarned();
		return totalPointsAwarded;
	}

	@Transactional
	public double getPointsAwardedToCustomer(long customerId, String toDate) {
		List<CustomerReward> result = new ArrayList<>();
		double totalPointsEarnedByCustomer = 0;
		LocalDate date=LocalDate.parse(toDate, formatter);
		String fromDate=date.minusMonths(3).toString();
		result = customerRewardsRepository.getAllTransactionsForCustomerByEndDate(customerId,fromDate, toDate);
		for(CustomerReward cr : result) {
			double cost=cr.getTransactionCost();

			if(cost>50 && cost<=100) {
				double diffCost=cost-50;
				totalPointsEarnedByCustomer+=1*diffCost;
			}
			if(cost>100) {
				double  costForPoints=cost-100;
				costForPoints=costForPoints*2;
				totalPointsEarnedByCustomer+=costForPoints+50;

			}
			System.out.println("cr  "+cr.getName()+"cost  "+cr.getTransactionCost());
		}
		return totalPointsEarnedByCustomer;
	}

	@Transactional
	public List<Reward> getPointsAwardedForAllCustomers() {
		List<CustomerReward> result = new ArrayList<>();
		LocalDate date=LocalDate.now();
		String toDate=date.toString();
		String fromDate=date.minusMonths(3).toString();
		result = customerRewardsRepository.getAllTransactionsForAllCustomerForThreeMonths(fromDate, toDate);
		Map<String,Map<String,Integer>> monthlyPointsData=new HashMap<String,Map<String,Integer>>();
		for(CustomerReward cr : result) {		
			if(monthlyPointsData.containsKey(cr.getName())) {
				Map<String,Integer> map=(Map<String,Integer>)	monthlyPointsData.get(cr.getName());
				map.put(cr.getTransactionDate(), calculateRewards(cr.getTransactionCost()));
			}else {
				Map<String,Integer> map=new HashMap<String,Integer>();
				map.put(cr.getTransactionDate(), calculateRewards(cr.getTransactionCost()));
				monthlyPointsData.put(cr.getName(), map);
			}
		}

		Map<String,Map<String,Integer>> monthlyPointsData1=new HashMap<String,Map<String,Integer>>();
		for(Map.Entry<String,Map<String,Integer>> pointsMap:monthlyPointsData.entrySet()) {

			String custName=pointsMap.getKey();
			Map<String,Integer> map=pointsMap.getValue();
			Map<String, Integer> result1 = new HashMap<String, Integer>();
			for(Map.Entry<String, Integer> entry:map.entrySet()) {
				String key = entry.getKey().split("-")[0] + "/" + entry.getKey().split("-")[1];
				Integer value = entry.getValue();
				Integer oldValue = result1.get(key) != null ? result1.get(key) : 0;
				result1.put(key, oldValue + value);
			}
			monthlyPointsData1.put(custName, result1);
		}

		List<Reward> rewardList=new ArrayList<>();
		for(Map.Entry<String,Map<String,Integer>> pointsMap:monthlyPointsData1.entrySet()) {
			Reward reward=new Reward();
			String custName=pointsMap.getKey();
			Map<String,Integer> map=pointsMap.getValue();
			reward.setCustomerName(custName);
			reward.setMonthlyData(map);
			reward.setTotalRewards(map.values().stream().reduce(0, Integer::sum));
			rewardList.add(reward);
		}
		return rewardList;
	}

	private int calculateRewards(double cost) {
		int totalPointsEarnedByCustomer=0;
		if(cost>50 && cost<=100) {
			double diffCost=cost-50;
			totalPointsEarnedByCustomer+=1*diffCost;
		}
		if(cost>100) {
			double  costForPoints=cost-100;
			costForPoints=costForPoints*2;
			totalPointsEarnedByCustomer+=costForPoints+50;

		}
		return totalPointsEarnedByCustomer;
	}

}
