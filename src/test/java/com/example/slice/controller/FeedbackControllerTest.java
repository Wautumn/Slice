package com.example.slice.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedbackControllerTest {
    private MockMvc mockMvc;

    @Test
    public void createFeedback() {
        try {
            String responseString = mockMvc.perform(post("/addFeedback")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).
                            param("title", "JUnit Test Feedback")
                            .param("content", "JUnit Test Feedback")
                            .param("userid","17"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void deleteFeedback() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/deleteFeedback")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "16"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findFeedbackById() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/findFeedbackById")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "16"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findFeedbackByUser() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/feedbackList")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "8"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findAllFeedback() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/adminGetFeedback")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void replyFeedback() {
        try {
            String responseString = mockMvc.perform(post("/handleFeedback")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "16")
                    .param("answer", "JUnit Test Feedback Reply"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }
}