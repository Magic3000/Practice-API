package ru.magic3000.practice2.dispatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import ru.magic3000.practice2.helpers.JsonHelper;
import ru.magic3000.practice2.pojo.Entity;
import ru.magic3000.practice2.tests.BaseTest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class EntityDispatcher {
    static final String CREATE_ENDPOINT = "/api/create";
    static final String GET_ALL_ENDPOINT = "/api/getAll";
    static final String GET_BY_ID_ENDPOINT = "/api/get/{id}";
    static final String DELETE_BY_ID_ENDPOINT = "/api/delete/{id}";
    static final String PATCH_BY_ID_ENDPOINT = "/api/patch/{id}";

    /**
     * Generate entity-data from json-template.
     */
    public static String getRandomizedEntityPayload() {
        try {
            return Objects.requireNonNull(JsonHelper.readFromFile(
                            "src/test/resources/json/userCreationBody.json"))
                    .replace("entity_title", String.format("title-%s", UUID.randomUUID()))
                    .replace("3000", String.valueOf(new Random().nextInt(343)));
        }
        catch (NullPointerException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static void validateEntity(Entity entity) {
        assert entity.getTitle() != null : "Title should not be null";
        assert entity.getAddition() != null : "Addition should not be null";
        assert entity.getAddition().getId() != null : "Addition id should not be null";
        assert entity.getAddition().getAdditional_info() != null : "Additional info should not be null";
        assert entity.getAddition().getAdditional_number() != null : "Additional number should not be null";
        assert entity.getImportant_numbers() != null : "Important numbers should not be null";
        assert entity.getImportant_numbers().size() > 0 : "Important numbers should not be empty";
    }

    /**
     * Create entity and save to entities list.
     */
    public static int create() {
        Response response = given()
                .spec(BaseTest.requestSpecification)
                .body(getRandomizedEntityPayload())
                .when()
                .post(CREATE_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        int entityId = Integer.parseInt(response.getBody().asString());
        assert response.getBody().asString().contains(String.valueOf(entityId)) :
                String.format("Expected entity id %s not found in the response body", entityId);
        return entityId;
    }

    /**
     * Get status-code for get entity request, used for Remove Entity Test.
     */
    public static int getRemovedById(int id) {
        return given()
                .spec(BaseTest.requestSpecification)
                .pathParam("id", id)
                .when()
                .get(GET_BY_ID_ENDPOINT)
                .then()
                .extract()
                .statusCode();
    }

    /**
     * Get entity by id.
     */
    public static Entity getById(int id) {
        Response response = given()
                .spec(BaseTest.requestSpecification)
                .pathParam("id", id)
                .when()
                .get(GET_BY_ID_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Entity entity = objectMapper.readValue(response.getBody().asString(), Entity.class);
            assert entity.getId() == id : String.format("Expected entity id %s, actual %s", id, entity.getId());
            validateEntity(entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing response", e);
        }
    }

    /**
     * Delete entity by id.
     */
    public static void removeById(int id) {
        given()
                .spec(BaseTest.requestSpecification)
                .pathParam("id", id)
                .when()
                .delete(DELETE_BY_ID_ENDPOINT)
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

    /**
     * Edit entity by id.
     */
    public static void editById(int entityId, Entity newEntity) {
        given()
                .spec(BaseTest.requestSpecification)
                .pathParam("id", entityId)
                .body(newEntity)
                .when()
                .patch(PATCH_BY_ID_ENDPOINT)
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

    /**
     * Get all entities.
     */
    public static List<Entity> getAll() {
        String response = given()
                .spec(BaseTest.requestSpecification)
                .when()
                .get(GET_ALL_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode entitiesNode = rootNode.get("entity");
            List<Entity> entities = objectMapper.readValue(entitiesNode.toString(), new TypeReference<List<Entity>>() {});
            entities.forEach(EntityDispatcher::validateEntity);
            return entities;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse response into List<Entity>", e);
        }
    }
}
