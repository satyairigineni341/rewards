package com.rewardpoints.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rewardpoints.repository.CustomerRewardsRepository;

@ExtendWith(SpringExtension.class)
public class RewardServiceImplTest {
	
	@InjectMocks
	private RewardServiceImpl service;
	
	@Mock
	private CustomerRewardsRepository customerRewardsRepository;
	

	
//	@Before
//	public void setUp() {
//		
//	}
	
	@Test
	public void getPointsAwardedToCustomerTest() {
		Assertions.assertNotNull(service.getPointsAwardedToCustomer(12, "2021-12-10"));
	}
	
	@Test
	public void getPointsAwardedForAllCustomers() {
		Assertions.assertNotNull(service.getPointsAwardedForAllCustomers());
	}

}
