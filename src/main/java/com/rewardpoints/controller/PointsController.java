package com.rewardpoints.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewardpoints.dto.Reward;
import com.rewardpoints.service.RewardServiceImpl;

//@RequestMapping("/api/rewards/")
@RestController
public class PointsController {
	
	@Autowired
	private RewardServiceImpl rewardServiceImpl;
	
	@GetMapping(value = "all/{endDate}")
	public double getTotalPointsAwarded(@PathVariable Date endDate) {
		return rewardServiceImpl.getAllPointsAwardedByEndDate(endDate);
	}
	
	@GetMapping("/byCustomer")
	public double getRewardsEarnedByCustomer(@RequestParam("customerId") long customerId,@RequestParam("endDate") String endDate) {
		return rewardServiceImpl.getPointsAwardedToCustomer(customerId,endDate);
	}
	@GetMapping("/allCustomers")
	public List<Reward> getPointsAwardedForAllCustomers() {
		return rewardServiceImpl.getPointsAwardedForAllCustomers();
	}

	@GetMapping("/test") 
	public String test() {
		return "welcome "; 
	}

}
