package com.anybodyherechat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.datasource.url=jdbc:h2:mem:testdb", "spring.datasource.driverClassName=org" +
        ".h2" +
        ".Driver", "spring.datasource.username=sa", "spring.datasource.password=password"})
class AnybodyHereChatApplicationTests {

    @Test
    void contextLoads() {
    }

}
