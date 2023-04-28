package com.vending.machine.tokyogul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vending.machine.tokyogul.entity.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
