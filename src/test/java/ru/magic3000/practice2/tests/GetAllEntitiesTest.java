package ru.magic3000.practice2.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.pojo.Entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetAllEntitiesTest extends BaseTest {
    /**
     * Verifies that all created entities are fetched correctly by comparing the expected and actual counts and IDs.
     */
    @Test(description = "Get All Entities Test")
    @Description("Verifies that all created entities are fetched correctly by comparing the expected and actual counts and IDs")
    public void GetAllEntities() {
        assert getCreatedEntities().size() == testEntitiesCount
                : String.format("Not all entities created, expected %s", testEntitiesCount);

        List<Entity> fetchedEntities = EntityDispatcher.getAll();
        assert fetchedEntities.size() >= getCreatedEntities().size() :
                String.format("Fetched entities less then created entities %s < %s%n", fetchedEntities.size(), getCreatedEntities().size());

        Set<Integer> fetchedEntityIds = fetchedEntities.stream()
                .map(Entity::getId)
                .collect(Collectors.toSet());

        List<Integer> missingEntities = getCreatedEntities().stream()
                .filter(id -> !fetchedEntityIds.contains(id))
                .toList();

        Allure.addAttachment("All entities: ", fetchedEntityIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        assert missingEntities.isEmpty() :
                String.format("Fetched entities don't contain created entities: %s", missingEntities);
    }
}
