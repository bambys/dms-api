package com.petrbambas.dms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrbambas.dms.model.Document;
import com.petrbambas.dms.model.Protocol;
import com.petrbambas.dms.repository.DocumentRepository;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ProtocolControllerPostMethodTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testPostProtocol() throws Exception {

        // create a protocol instance to assert
        Protocol protocolTested = new Protocol();
        protocolTested.setName("Contract No 1");
        protocolTested.setStatus(Protocol.ProtocolStatus.NEW);
        List<Document> documents = documentRepository.findAll();
        Document document = documents.get(1);
        Set<Document> documentsForProtocol = new HashSet<>();
        documentsForProtocol.add(document);
        protocolTested.setDocuments(documentsForProtocol);

        // Convert the User object to JSON
        String jsonRequest = objectMapper.writeValueAsString(protocolTested);

        // Perform a POST request to /api/documents
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/protocols")
                        .with(user("petr").password("pass").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Assuming you return HTTP 200 for successful creation
                .andReturn();

        // Extract the JSON response
        String jsonResponse = result.getResponse().getContentAsString();

        // Deserialize the JSON response to a Document object
        Protocol newDocument = objectMapper.readValue(jsonResponse, Protocol.class);

        // Assert that the protocol information is as expected
        assertEquals("Contract No 1", newDocument.getName());
    }
}
