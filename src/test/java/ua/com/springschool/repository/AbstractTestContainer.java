package ua.com.springschool.repository;

import org.junit.jupiter.api.AfterAll;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.com.springschool.SpringSchoolApplication;

@SpringBootTest(
    classes = SpringSchoolApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
abstract class AbstractTestContainer {

    @Container
    static PostgreSQLContainer<?> testContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", testContainer::getJdbcUrl);
        registry.add("spring.datasource.username", testContainer::getUsername);
        registry.add("spring.datasource.password", testContainer::getPassword);
    }

    @AfterAll
    static void tearDown() {
        testContainer.stop();
        testContainer.close();
    }
}
