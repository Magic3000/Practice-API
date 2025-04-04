package ru.magic3000.practice2.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.helpers.Constants;
import ru.magic3000.practice2.pojo.Addition;
import ru.magic3000.practice2.pojo.Entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EditEntityTest extends BaseTest {
    /**
     * Tests the editing of an existing entity via API by updating its fields and verifying the changes.
     */
    @Test(description = "Edit Entity Test")
    @Description("Tests the editing of an existing entity via API by updating its fields and verifying the changes")
    public void EditEntity() {
        int entityId = getCreatedEntities().get(new Random().nextInt(getCreatedEntities().size()));
        Entity currentEntity = EntityDispatcher.getById(entityId);
        Entity newEntity = Entity.builder()
                .id(entityId)
                .title(Constants.TEST_TITLE)
                .verified(false)
                .addition(Addition.builder()
                        .id(currentEntity.getAddition().getId())
                        .additional_info(Constants.TEST_INFO)
                        .additional_number(5)
                        .build())
                .important_numbers(Arrays.asList(3, 4, 5))
                .build();
        EntityDispatcher.editById(entityId, newEntity);

        Entity editedEntity = EntityDispatcher.getById(entityId);
        assert editedEntity.equals(newEntity) : "Entities not equal";
    }
}
