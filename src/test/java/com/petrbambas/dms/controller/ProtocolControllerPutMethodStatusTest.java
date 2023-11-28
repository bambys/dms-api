package com.petrbambas.dms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrbambas.dms.repository.ProtocolRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class ProtocolControllerPutMethodStatusTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProtocolRepository protocolRepository;
    @Autowired
    private DocumentRepository documentRepository;

    @Test
    public void testUpdateProtocolStatus() throws Exception {
        Protocol testedProtocol = protocolRepository.findAll().get(0);
        testedProtocol.setStatus(Protocol.ProtocolStatus.CANCELLED);

        // Convert the Document object to JSON
        String jsonRequest = objectMapper.writeValueAsString(testedProtocol);

        // Perform a PUT request to /api/documents/1
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/protocols/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Extract the JSON response
        String jsonResponse = result.getResponse().getContentAsString();

        // Deserialize the JSON response to a Document object
        Protocol updatedProtocol = objectMapper.readValue(jsonResponse, Protocol.class);

        // Assert that the updated status is as expected
        assertEquals(Protocol.ProtocolStatus.CANCELLED, updatedProtocol.getStatus());
    }
}


