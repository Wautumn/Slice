package com.example.slice.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest {
    private MockMvc mockMvc;

    @Test
    public void createProject() {
        try {
            String responseString = mockMvc.perform(post("/createProject")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("userid", "17")
                    .param("name", "JUnit Test Project")
                    .param("description","JUnit Test Project")
                    .param("starttime", "2019-06-10 20:51:00")
                    .param("endtime", "2019-06-10 20:52:00")
                    .param("members", "[8, 10]")
                    .param("subtasks", "[JUnit Test Project Subtasks]"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void deleteProject() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/deleteProject")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("projectid", "42"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void addMembers() {
        try {
            String responseString = mockMvc.perform(post("/addMembers")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("projectid", "42")
                    .param("members", "[15]"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findAllTasks() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/findAllSubTasks")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("projectid", "22"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findProjectByUser() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getCreatedProject")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "8"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }

    @Test
    public void findProjectAttend() {
        try {
            String responseString = mockMvc.perform(
                    MockMvcRequestBuilders.get("/getJoinedProject")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userid", "8"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println(responseString);
        } catch (Exception exception) {

        }
    }
}