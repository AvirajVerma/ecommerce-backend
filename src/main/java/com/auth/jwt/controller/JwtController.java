package com.auth.jwt.controller;

import com.auth.jwt.entity.JwtRequest;
import com.auth.jwt.entity.JwtResponse;
import com.auth.jwt.service.JwtService;
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
