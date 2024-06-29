package com.auth.ecommerce.dao;

import com.auth.ecommerce.entity.OrderDetail;
import com.auth.ecommerce.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {

    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);


}
