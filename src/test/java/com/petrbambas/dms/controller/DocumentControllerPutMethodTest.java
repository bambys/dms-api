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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class DocumentControllerPutMethodTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testUpdateDocument() throws Exception {
        // Create a Document object to be sent in the request
        Document testedDocument = new Document();
//        testedDocument.setId(1);
        testedDocument.setName("Contract No 555");
        testedDocument.setType("PDF");

        // Convert the Document object to JSON
        String jsonRequest = objectMapper.writeValueAsString(testedDocument);

        // Perform a PUT request to /api/documents/1
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/documents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Extract the JSON response
        String jsonResponse = result.getResponse().getContentAsString();

        // Deserialize the JSON response to a Document object
        Document updatedDocument = objectMapper.readValue(jsonResponse, Document.class);

        // Assert that the updated user information is as expected
        assertEquals("Contract No 555", updatedDocument.getName());
        assertEquals("PDF", updatedDocument.getType());
    }
}

