package org.paycore.payment.steps;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.paycore.payment.model.Order;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    private final RequestSpecification requestSpec;

    public OrderSteps(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(requestSpec)
                .body(order)
                .when()
                .post("/orders")
                .then();
    }

    public ValidatableResponse getOrderById(UUID id) {
        return given()
                .spec(requestSpec)
                .when()
                .get("/orders/" + id)
                .then();
    }

    public ValidatableResponse updateOrderById(UUID id, String status) {
        return given()
                .spec(requestSpec)
                .log().all()
                .when()
                .patch("/orders/" + id + "/status?status=" + status)
                .then()
                .log().all();
    }
}
