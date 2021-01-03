package com.example.entities;

import com.example.entities.enums.RiskType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PolicySubObject {
    String name;
    BigDecimal sumInsured; // TODO: not sure about the type, maybe long was enough for this task
    RiskType riskType;
}
