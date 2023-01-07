package com.hidhayaths.springkeycloakoauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String greetUser(Principal principal){
        return "hello, "+principal.getName();
    }

    @PreAuthorize("hasAuthority('SCOPE_write')")
    @GetMapping("/admin")
    public String greetAdmin(Principal principal){
        return "hello admin, "+principal.getName();
    }
}
