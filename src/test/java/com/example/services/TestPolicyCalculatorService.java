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
    void assertThatFirstGivenExampleCalculatesCorrectly() {
        PolicySubObject policySubObject1 = new PolicySubObject("TV", new BigDecimal("100"), RiskType.FIRE);
        PolicySubObject policySubObject2 = new PolicySubObject("DVD", new BigDecimal(("8")), RiskType.THEFT);

        PolicyObject policyObject = new PolicyObject("House", List.of(policySubObject1, policySubObject2));

        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        assertEquals(new BigDecimal("2.28"), premium);
    }

    @Test
    void assertThatSecondGivenExampleCalculatesCorrectly() {
        PolicySubObject policySubObject1 = new PolicySubObject("TV", new BigDecimal("500"), RiskType.FIRE);
        PolicySubObject policySubObject2 = new PolicySubObject("DVD", new BigDecimal(("102.51")), RiskType.THEFT);

        PolicyObject policyObject = new PolicyObject("House", List.of(policySubObject1, policySubObject2));

        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        assertEquals(new BigDecimal("17.13"), premium);
    }

    @Test
    void assertThatCalculationWorksOnEmptyPolicyObjectList() {
        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of());

        BigDecimal premium = policyCalculatorService.calculate(policy);
        assertEquals(new BigDecimal("0.00"), premium);
    }

    @Test
    void assertThatCalculationWorksOnEmptyPolicySubObjectList() {
        PolicyObject policyObject = new PolicyObject("House", List.of());
        Policy policy = new Policy("1", PolicyStatus.APPROVED, List.of(policyObject));

        BigDecimal premium = policyCalculatorService.calculate(policy);
        assertEquals(new BigDecimal("0.00"), premium);
    }

}