package ru.magic3000.practice2.helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseRequests {
    public static RequestSpecification initRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType("application/json")
                .setBaseUri(RestAssured.baseURI)
                .setPort(RestAssured.port)
                .setAccept("application/json")
                .build();
    }
}
