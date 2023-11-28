package com.petrbambas.dms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrbambas.dms.model.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DocumentControllerPostMethodTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPostDocument() throws Exception {

        // create a document instance to assert
        Document documentTested = new Document();
        documentTested.setName("Leasing terms");
        documentTested.setType("pdf");

        // Convert the User object to JSON
        String jsonRequest = objectMapper.writeValueAsString(documentTested);

        // Perform a POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/documents")
                        .with(user("petr").password("pass").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk()) // HTTP 200 for successful creation
                .andReturn();

        // Extract the JSON response
        String jsonResponse = result.getResponse().getContentAsString();

        // Deserialize the JSON response to a Document object
        Document newDocument = objectMapper.readValue(jsonResponse, Document.class);

        // Assert that the document information is as expected
        assertEquals("Leasing terms", newDocument.getName());
        assertEquals("pdf", newDocument.getType());
    }
}
