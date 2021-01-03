package com.example.entities;

import com.example.entities.enums.PolicyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Policy {

    String number;
    PolicyStatus status;
    List<PolicyObject> objects;
}
