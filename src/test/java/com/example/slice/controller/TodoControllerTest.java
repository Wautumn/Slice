package com.example.slice.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TodoControllerTest {
    private MockMvc mockMvc;

    @Test
    public void createTodo() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/createTodo")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userid", "17")
            .param("name", "JUnit test Todolist"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findTodo() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/findTodo")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "17"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }
}