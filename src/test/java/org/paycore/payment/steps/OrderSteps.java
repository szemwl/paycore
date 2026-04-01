package org.paycore.payment.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.paycore.payment.model.Order;
import org.paycore.payment.model.OrderStatus;

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
                .contentType(ContentType.JSON)
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

    public ValidatableResponse updateOrderById(UUID id, OrderStatus status) {
        return given()
                .spec(requestSpec)
                .when()
                .patch("/orders/" + id + "/status?status=" + status)
                .then();
    }

    public ValidatableResponse createOrderWithoutBody() {
        return given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .when()
                .post("/orders")
                .then();
    }

    public ValidatableResponse deleteOrderById(UUID id) {
        return given()
                .spec(requestSpec)
                .when()
                .delete("/orders/delete/{id}", id)
                .then();
    }
}
