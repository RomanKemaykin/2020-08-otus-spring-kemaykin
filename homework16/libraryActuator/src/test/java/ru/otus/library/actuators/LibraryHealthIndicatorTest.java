package ru.otus.library.actuators;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryHealthIndicatorTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void health() throws Exception {
        mvc.perform(get("/actuator/health/library"))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.details.randomValue").exists());
    }
}