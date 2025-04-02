package ru.magic3000.practice2.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.pojo.Entity;

import java.util.Set;
import java.util.stream.Collectors;

public class AddEntityTest extends BaseTest {
    /**
     * Verify that all created entities are present in the list of all entities from api.
     */
    @Test(description = "Add Entity Test")
    @Description("Verify that all created entities are present in the list of all entities from api")
    public void AddEntity() {
        Set<Integer> fetchedEntityIds = EntityDispatcher.getAll().stream()
                .map(Entity::getId)
                .collect(Collectors.toSet());

        getCreatedEntities().forEach(entityId -> {
            assert fetchedEntityIds.contains(entityId) : String.format("Entity with id %s not found", entityId);
        });
    }
}
