package com.example.services;

import com.example.entities.Policy;
import com.example.entities.PolicyObject;
import com.example.entities.PolicySubObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import static com.example.entities.enums.RiskType.FIRE;
import static com.example.entities.enums.RiskType.THEFT;

@Component
public class PolicyCalculatorService {

    private static final BigDecimal LOWER_FIRE_COEFFICIENT = BigDecimal.valueOf(0.014);
    private static final BigDecimal HIGHER_FIRE_COEFFICIENT = BigDecimal.valueOf(0.024);

    private static final BigDecimal LOWER_THEFT_COEFFICIENT = BigDecimal.valueOf(0.11);
    private static final BigDecimal HIGHER_THEFT_COEFFICIENT = BigDecimal.valueOf(0.05);

    public BigDecimal calculate(Policy policy) {
        BigDecimal premium = BigDecimal.ZERO;
        return premium
                .add(calculateFirePremium(policy))
                .add(calculateTheftPremium(policy))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTheftPremium(Policy policy) {
        BigDecimal subgroupInsuranceSum = policy.getObjects()
                .stream()
                .map(PolicyObject::getSubObjects)
                .flatMap(Collection::stream)
                .filter(subObject -> subObject.getRiskType() == THEFT)
                .map(PolicySubObject::getSumInsured)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (subgroupInsuranceSum.compareTo(BigDecimal.valueOf(15)) >= 0) {
            return subgroupInsuranceSum.multiply(HIGHER_THEFT_COEFFICIENT);
        }
        return subgroupInsuranceSum.multiply(LOWER_THEFT_COEFFICIENT);
    }

    private BigDecimal calculateFirePremium(Policy policy) {
        BigDecimal subgroupInsuranceSum = policy.getObjects()
                .stream()
                .map(PolicyObject::getSubObjects)
                .flatMap(Collection::stream)
                .filter(subObject -> subObject.getRiskType() == FIRE)
                .map(PolicySubObject::getSumInsured)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (subgroupInsuranceSum.compareTo(BigDecimal.valueOf(100)) > 0) {
            return subgroupInsuranceSum.multiply(HIGHER_FIRE_COEFFICIENT);
        }
        return subgroupInsuranceSum.multiply(LOWER_FIRE_COEFFICIENT);
    }
}
