package com.rewardpoints.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Reward {

	private String customerName;
	private Map<String,Integer> monthlyData;
	private Integer totalRewards;
	
}
