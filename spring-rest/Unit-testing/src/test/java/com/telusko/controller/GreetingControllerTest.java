package com.telusko.controller;


import com.telusko.model.Alien;
import com.telusko.service.IGreeting;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(GreetingController.class)
public class GreetingControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IGreeting service;

    @Test
    @Disabled
    public void testGetGreetings2() throws Exception {

        //MockObject
        Mockito.when(service.generateGreeting("Kapil")).thenReturn("Good Night, Kapil");

        //RequestObject
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/wish2");

        ResultActions res = mockMvc.perform(requestBuilder);
        MvcResult result = res.andReturn();

        MockHttpServletResponse response = result.getResponse();

        // test cases
        int status = response.getStatus();

        assertEquals(200, status);
    }

    @Test
    public void testAddAlienInfo() throws Exception {
        //Mocking
        Mockito.when(service.addAlienInfo(ArgumentMatchers.any())).thenReturn(true);

        Alien alien = new Alien(1, "Saranga", "Kadawatha");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(alien);


        //RequestObject
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add-alien")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        ResultActions res = mockMvc.perform(requestBuilder);
        MvcResult result = res.andReturn();

        MockHttpServletResponse response = result.getResponse();

        //tests cases
        int status = response.getStatus();

        assertEquals(201, status);

    }

}
