package ru.magic3000.practice2.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;

import java.util.Random;

public class RemoveEntityTest extends BaseTest {
    /**
     * Verifies that an entity is successfully removed by ID.
     */
    @Test(description = "Remove Entity Test")
    @Description("Verifies that an entity is successfully removed by ID")
    public void RemoveEntity() {
        int entityId = getCreatedEntities().remove(new Random().nextInt(getCreatedEntities().size()));
        EntityDispatcher.removeById(entityId);
        int statusCode = EntityDispatcher.getRemovedById(entityId);
        assert statusCode == 500 : String.format("Entity with id %s was not removed, status-code: %s", entityId, statusCode);
    }
}
