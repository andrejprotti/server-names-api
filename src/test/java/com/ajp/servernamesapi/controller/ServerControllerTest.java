package com.ajp.servernamesapi.controller;

import com.ajp.servernamesapi.ServerNamesApiApplication;
import com.ajp.servernamesapi.model.Server;
import com.ajp.servernamesapi.repository.ServerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerNamesApiApplication.class)
@WebAppConfiguration
public class ServerControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private Server server;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ServerRepository serverRepository;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.serverRepository.deleteAll();

        this.server = serverRepository.save(new Server("test1", "description"));
    }

    @Test
    public void returnsCurrentServersCorrectly() throws Exception {
        mockMvc.perform(get("/servers/")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("test1")))
                .andExpect(jsonPath("$[0].description", is("description")));
    }

    @Test
    public void createsNewServerCorrectly() throws Exception {
        mockMvc.perform(post("/servers/")
                .content(this.json(new Server("test2", "description2")))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test2")))
                .andExpect(jsonPath("$.description", is("description2")));
    }

    @Test
    public void returnsServerByIdCorrectly() throws Exception {
        Server server = serverRepository.findAll().get(0);

        mockMvc.perform(get("/servers/" + server.getId())
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(server.getName())))
                .andExpect(jsonPath("$.description", is(server.getDescription())));
    }

    @Test
    public void returnsStatus404WithInvalidIdOnGet() throws Exception {
        mockMvc.perform(get("/servers/9999")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatesServerCorrectly() throws Exception {
        Server server = serverRepository.findAll().get(0);

        mockMvc.perform(put("/servers/" + server.getId())
                .content(this.json(new Server("updatedTest", "updatedDescription")))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("updatedTest")))
                .andExpect(jsonPath("$.description", is("updatedDescription")));
    }

    @Test
    public void returnsStatus404WithInvalidIdOnPut() throws Exception {
        mockMvc.perform(put("/servers/9999")
                .content(this.json(new Server("updatedTest", "updatedDescription")))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deletesServerCorrectly() throws Exception {
        Server server = serverRepository.findAll().get(0);

        mockMvc.perform(delete("/servers/" + server.getId())
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void returnsStatus404WithInvalidIdOnDelete() throws Exception {
        mockMvc.perform(get("/servers/9999")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void calculatesCurrentServerCountCorrectly() throws Exception {
        mockMvc.perform(get("/servers/count")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().json("1"));
    }

    @Test
    public void returnsHelpInformationCorrectly() throws Exception {
        mockMvc.perform(get("/help")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}