package com.rewardpoints.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CUSTOMER_REWARD")
public class CustomerReward {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;

	//@NotNull
	@Column(name="customer_id")
	private long customerId;
	
	//@NotBlank
	@Column(name="customer_name")
	private String name;
	
	//@NotNull
	@Column(name="trans_cost")
	private double transactionCost;
	
//	private double pointsEarned;
	
	@Column(name="transaction_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreatedDate
	private String transactionDate;

}
