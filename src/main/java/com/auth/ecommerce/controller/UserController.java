package com.auth.ecommerce.controller;

import com.auth.ecommerce.entity.User;
import com.auth.ecommerce.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers(){
        userService.initRolesAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    //newly added
    @PostMapping({"/registerNewVendor"})
    public User registerNewVendor(@RequestBody User vendor){ return userService.registerNewVendor(vendor); }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this url is only accessible to admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this url is only accessible to user";
    }

    @GetMapping({"/forVendor"})
    @PreAuthorize("hasRole('Vendor')")
    public String forVendor(){return "this url is only accessible to vendor"; }

    @GetMapping({"/getUserDetailsByUserName"})
    public User getUserDetailsByUserName(){
        return userService.getUserDetailsByUserName();
    }

    @PutMapping("/{userName}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable String userName, @RequestBody Map<String, String> passwordRequest) {
        String newPassword = passwordRequest.get("newPassword");
        userService.updateUserPassword(userName, newPassword);
        return ResponseEntity.ok().build();
    }
}
