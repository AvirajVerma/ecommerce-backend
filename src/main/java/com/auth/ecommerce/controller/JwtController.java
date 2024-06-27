package com.auth.ecommerce.controller;

import com.auth.ecommerce.entity.JwtRequest;
import com.auth.ecommerce.entity.JwtResponse;
import com.auth.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class JwtController {

    @Autowired
    private JwtService jwtService;

//    @PostMapping({"/authenticate"})
    @PostMapping
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        return jwtService.createJwtToken(jwtRequest);
    }
}
