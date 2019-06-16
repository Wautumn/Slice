package com.example.slice.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectTaskControllerTest {
    private MockMvc mockMvc;

    @Test
    public void getData() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getAnalyzeData")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userid", "8")
                            .param("start", "2019-05-22").param("end", "2019-05-27"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void getProjectProgress() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getProjectProgress")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("projectid", "22"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }
}