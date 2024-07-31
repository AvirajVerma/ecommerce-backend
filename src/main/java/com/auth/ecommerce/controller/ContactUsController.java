package com.auth.ecommerce.controller;

import com.auth.ecommerce.entity.ContactUs;
import com.auth.ecommerce.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping({"/newContactUs"})
    public ContactUs newContactUs(@RequestBody ContactUs contactUs){
        return contactUsService.newContactUs(contactUs);
    }
}
