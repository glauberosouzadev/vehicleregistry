package com.project.vehicleregistry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = VehicleRegistryApplication.class)
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/db/sql/reset.sql")
public class BaseCompTest {

    @Autowired
    public final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }
}