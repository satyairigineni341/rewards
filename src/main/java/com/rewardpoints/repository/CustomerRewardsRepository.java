package com.rewardpoints.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rewardpoints.model.CustomerReward;

@Repository
public interface CustomerRewardsRepository extends JpaRepository<CustomerReward, Long> {
	
	@Query(value = "SELECT * FROM CUSTOMER_REWARD cr where cr.transactionDate between : endDate and MONTH(n.transactionDate) = MONTH(DATEADD(month, - 3, : endDate))", nativeQuery = true)
	List<CustomerReward> getAllTransactionsByEndDate(Date endDate);

	
	@Query(value =" select id,customer_id,customer_name,trans_cost,transaction_date from customer_reward where customer_id=:customerId and transaction_date between :fromDate and  :toDate\r\n"
			+ "group by customer_id,trans_cost,transaction_date", nativeQuery = true)
	List<CustomerReward> getAllTransactionsForCustomerByEndDate(@Param("customerId") long customerId,@Param("fromDate")  String fromDate,@Param("toDate")  String toDate);
	
	@Query(value =" select id,customer_id,customer_name,trans_cost,transaction_date from customer_reward where  transaction_date between :fromDate and  :toDate\r\n"
			+ "group by customer_id,trans_cost,transaction_date", nativeQuery = true)
	List<CustomerReward> getAllTransactionsForAllCustomerForThreeMonths(@Param("fromDate")  String fromDate,@Param("toDate")  String toDate);



}
