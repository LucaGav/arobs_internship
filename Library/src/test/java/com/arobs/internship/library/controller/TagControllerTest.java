package com.arobs.internship.library.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenFindTags_given_returnResponseEntity() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tag/tags"))
                .andExpect(status().isOk())
                .andReturn();
        String resultTags = result.getResponse().getContentAsString();
        Assertions.assertNotNull(resultTags);
    }
}
