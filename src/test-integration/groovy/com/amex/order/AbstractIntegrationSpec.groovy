package com.amex.order

import io.restassured.RestAssured
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [OrderServiceApplication])
@ActiveProfiles('local')
abstract class AbstractIntegrationSpec extends Specification {

    @LocalServerPort
    int localServerPort

    def setup() {
        RestAssured.port = localServerPort
    }

    def cleanup() {
        RestAssured.reset()
    }

    @Shared
    protected PostgreSQLContainer postgres =
        new PostgreSQLContainer()
            .withDatabaseName("order_service")
            .withUsername("order")
            .withPassword("SeCrEt1")

}