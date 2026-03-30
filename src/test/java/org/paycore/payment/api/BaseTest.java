package org.paycore.payment.api;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.paycore.payment.spec.RequestSpecs;
import org.paycore.payment.steps.OrderSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {

    @Value("${base.url}")
    protected String baseUrl;

    protected OrderSteps orderSteps;
    protected RequestSpecification requestSpec;

    @BeforeEach
    void setup() {
        requestSpec = RequestSpecs.baseRequestSpec(baseUrl);
        orderSteps = new OrderSteps(requestSpec);
    }
}
