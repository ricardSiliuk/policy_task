package com.example.controllers;

import com.example.services.PolicyCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PolicyController {

    @Autowired
    PolicyCalculatorService policyCalculatorService;

}
