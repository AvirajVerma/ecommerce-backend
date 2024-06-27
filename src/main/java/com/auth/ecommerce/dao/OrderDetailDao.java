package com.auth.ecommerce.dao;

import com.auth.ecommerce.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {

}
