package com.auth.ecommerce.dao;

import com.auth.ecommerce.entity.ContactUs;
import org.springframework.data.repository.CrudRepository;

public interface ContactUsDao extends CrudRepository<ContactUs, Long> {
}
