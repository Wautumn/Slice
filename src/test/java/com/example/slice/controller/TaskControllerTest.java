package com.example.slice.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {
    private MockMvc mockMvc;

    @Test
    public void getAllTasks() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getTasksByUserid")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "17"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void getTodayTasks() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getTodayTasksByUserid")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "17"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void insertTask() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.post("/insertTask")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userid", "17")
            .param("name", "JUnit test Task")
            .param("description", "JUnit test Task")
            .param("starttime", "2019-06-10")
            .param("endtime", "2019-06-11"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

}