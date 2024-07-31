package com.auth.ecommerce.service;

import com.auth.ecommerce.dao.ContactUsDao;
import com.auth.ecommerce.entity.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsDao contactUsDao;

    public ContactUs newContactUs(ContactUs contactUs){
        return contactUsDao.save(contactUs);
    }

}
