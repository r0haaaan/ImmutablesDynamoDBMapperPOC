package com.github.simy4.poc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.simy4.poc.IntegrationTest;
import com.github.simy4.poc.model.Entity;
import com.github.simy4.poc.model.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@IntegrationTest
class CrudControllerIT {

  private static final String CHANGE_ENTITY_PAYLOAD = "{"
          + "  \"name\":\"name\","
          + "  \"address\":{"
          + "    \"line1\":\"123 example st\","
          + "    \"country\":\"Australia\""
          + "  }"
          + "}";

  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private ObjectMapper objectMapper;

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
           .contentType(MediaType.APPLICATION_JSON)
           .content(CHANGE_ENTITY_PAYLOAD))
           .andExpect(status().isCreated())
           .andExpect(header().exists(HttpHeaders.LOCATION))
           .andExpect(content().json(CHANGE_ENTITY_PAYLOAD, false));
  }

  @Nested
  class ReadUpdateDelete {
    private Identity entityId;

    @BeforeEach
    void setUp() throws Exception {
      entityId = objectMapper.readValue(mockMvc
              .perform(post("/crud")
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(CHANGE_ENTITY_PAYLOAD))
              .andExpect(status().isCreated())
              .andReturn()
              .getResponse()
              .getContentAsString(StandardCharsets.UTF_8), Entity.class).getId();
    }

    @Test
    void testReadEntity() throws Exception {
      mockMvc
              .perform(get("/crud/{id}", entityId.getSk()).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().json(CHANGE_ENTITY_PAYLOAD, false));
    }

    @Test
    void testUpdateEntity() throws Exception {
      mockMvc
              .perform(patch("/crud/{id}", entityId.getSk())
                      .accept(MediaType.APPLICATION_JSON)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("{\"name\":\"other-name\"}"))
              .andExpect(status().isOk())
              .andExpect(content().json("{\"name\":\"other-name\"}", false));
    }

    @Test
    void testDeleteEntity() throws Exception {
      mockMvc
              .perform(delete("/crud/{id}", entityId.getSk()))
              .andExpect(status().isNoContent());

      mockMvc
              .perform(get("/crud/{id}", entityId.getSk()).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
    }
  }
}
