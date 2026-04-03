package org.paycore.payment.integration.api;

import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.paycore.payment.integration.support.TestDatabaseCleaner;
import org.paycore.payment.spec.RequestSpecs;
import org.paycore.payment.steps.OrderSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public abstract class BaseApiTest {

    @Value("${base.url}")
    protected String baseUrl;

    @Autowired
    protected TestDatabaseCleaner dbCleaner;

    protected OrderSteps orderSteps;
    protected RequestSpecification requestSpec;

    @BeforeEach
    void setup() {
        dbCleaner.clean();

        requestSpec = RequestSpecs.baseRequestSpec(baseUrl);
        orderSteps = new OrderSteps(requestSpec);
    }
}
