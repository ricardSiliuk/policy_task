package com.example.controllers;

import com.example.services.PolicyCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PolicyController.class)
class TestPolicyController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PolicyCalculatorService policyCalculatorService;

    @Test
    void testThatApiCorrectlyBuildsResponse() throws Exception {
        when(policyCalculatorService.calculate(any())).thenReturn(new BigDecimal("0.00"));

        mockMvc.perform(
                post("/calculatePremium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"number\": \"ABC\"," +
                                "\"status\":\"REGISTERED\"," +
                                "\"objects\": []" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premium").value(0));
    }

    @Test
    void testThatEmptyBodyReturnsCorrectErrorCode() throws Exception {
        mockMvc.perform(
                post("/calculatePremium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is(400));
    }
}