package ru.magic3000.practice2.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.pojo.Entity;

import java.util.Random;

import static ru.magic3000.practice2.helpers.Constants.rnd;

public class GetEntityTest extends BaseTest {
    /**
     * Verifies that a single entity is fetched correctly by ID and matches the expected ID.
     */
    @Test(description = "Get Entity Test")
    @Description("Verifies that a single entity is fetched correctly by ID and matches the expected ID")
    public void GetEntity() {
        int entityId = getCreatedEntities().get(rnd.nextInt(getCreatedEntities().size()));
        Entity entity = EntityDispatcher.getById(entityId);

        assert entity != null : String.format("Entity with id %s not found", entityId);
        assert entity.getId() == entityId : String.format("Expected entity id %s, actual %s", entityId, entity.getId());
    }
}
