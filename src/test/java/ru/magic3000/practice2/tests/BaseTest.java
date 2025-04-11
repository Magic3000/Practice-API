package ru.magic3000.practice2.tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.helpers.BaseRequests;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    private final ThreadLocal<List<Integer>> createdEntities = ThreadLocal.withInitial(ArrayList::new);
    final int testEntitiesCount = 5;
    public static RequestSpecification requestSpecification;

    @BeforeClass
    public void init() {
        RestAssured.registerParser("text/plain", Parser.JSON);
        requestSpecification = BaseRequests.initRequestSpecification();
        for (int i = 0; i < testEntitiesCount; i++) {
            createEntity();
        }
    }

    @Step("Creates a new entity and adds its ID to the list of created entities")
    private void createEntity() {
        int entityId = EntityDispatcher.create();
        assert entityId > 0 : "Error creating entity";
        getCreatedEntities().add(entityId);
    }

    public List<Integer> getCreatedEntities() {
        return createdEntities.get();
    }

    @AfterClass()
    public void tearDown() {
        List<Integer> entities = getCreatedEntities();
        entities.forEach(EntityDispatcher::removeById);
        createdEntities.remove();
    }
}
