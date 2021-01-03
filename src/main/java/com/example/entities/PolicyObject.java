package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PolicyObject {
    String name;
    List<PolicySubObject> subObjects;
}
