package com.example.controllers;

import com.example.entities.Policy;
import com.example.entities.Response;
import com.example.services.PolicyCalculatorService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PolicyController {

    @Autowired
    PolicyCalculatorService policyCalculatorService;

    @PostMapping(path = "/calculatePremium", consumes = "application/json", produces = "application/json")
    public Response calculatePremium(@RequestBody @NonNull Policy policy) {
        return new Response(policyCalculatorService.calculate(policy));
    }
}
