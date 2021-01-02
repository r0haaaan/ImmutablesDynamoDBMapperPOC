package com.github.simy4.poc.controllers;

import com.github.simy4.poc.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@IntegrationTest
class CrudControllerIT {

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void testCreateEntity() throws Exception {
    mockMvc
        .perform(post("/crud")
           .accept(MediaType.APPLICATION_JSON)
           .content("{\"name\":\"name\"}"))
        .andExpect(status().isOk())
        .andExpect(content().json("{}"));
  }
}
