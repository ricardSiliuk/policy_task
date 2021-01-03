package com.example.services;

import com.example.entities.Policy;
import com.example.entities.PolicyObject;
import com.example.entities.PolicySubObject;
import com.example.entities.enums.PolicyStatus;
import com.example.entities.enums.RiskType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPolicyCalculatorService {

    PolicyCalculatorService policyCalculatorService = new PolicyCalculatorService();

    @Test
    void test_first_assertion() {
        PolicySubObject policySubObject1 = new PolicySubObject("TV", new BigDecimal("100"), RiskType.FIRE);
        PolicySubObject policySubObject2 = new PolicySubObject("DVD", new BigDecimal(("8")), RiskType.THEFT);

        PolicyObject policyObject = new PolicyObject("House", List.of(policySubObject1, policySubObject2));

        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        assertEquals(new BigDecimal("2.28"), premium);
    }

    @Test
    void test_second_assertion() {
        PolicySubObject policySubObject1 = new PolicySubObject("TV", new BigDecimal("500"), RiskType.FIRE); // 12
        PolicySubObject policySubObject2 = new PolicySubObject("DVD", new BigDecimal(("102.51")), RiskType.THEFT); // 5.1255

        PolicyObject policyObject = new PolicyObject("House", List.of(policySubObject1, policySubObject2));

        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        // Premiums will come out to 12 and 5.1255, but we round up as per task description.
        // Maybe the rounding up should happen here instead of Service itself?
        assertEquals(new BigDecimal("17.13"), premium);
    }

    @Test
    void test_empty_policy_object() {
        PolicyObject policyObject = new PolicyObject("House", List.of());

        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        // Policy has to contain at least one object, but PolicyObject's list can be empty.
        // Not sure whether that should throw or return a 0.
        assertEquals(new BigDecimal("0.00"), premium);
    }

}