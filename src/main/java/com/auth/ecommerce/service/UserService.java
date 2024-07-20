package com.auth.ecommerce.service;


import com.auth.ecommerce.configuration.JwtRequestFilter;
import com.auth.ecommerce.dao.RoleDao;
import com.auth.ecommerce.dao.UserDao;
import com.auth.ecommerce.entity.Role;
import com.auth.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDao.save(user);
    }

    //newly added
    public User registerNewVendor(User vendor) {
        Role role = roleDao.findById("Vendor").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        vendor.setRole(roles);

        vendor.setUserPassword(getEncodedPassword(vendor.getUserPassword()));
        return userDao.save(vendor);
    }

    public void initRolesAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin Role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created user");
        roleDao.save(userRole);

        //newly added
        Role vendorRole = new Role();
        vendorRole.setRoleName("Vendor");
        vendorRole.setRoleDescription("Default role for newly created vendor");
        roleDao.save(vendorRole);


        User adminUser = new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@123"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user = new User();
//        user.setUserFirstName("aviraj");
//        user.setUserLastName("verma");
//        user.setUserName("aviraj17");
//        user.setUserPassword(getEncodedPassword("aviraj@17"));
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User getUserDetailsByUserName() {
        String username = JwtRequestFilter.CURRENT_USER;
        return userDao.findById(username).get();
    }

    public void updateUserPassword(String userName, String newPassword) {
        User user = userDao.findById(userName).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }
}
