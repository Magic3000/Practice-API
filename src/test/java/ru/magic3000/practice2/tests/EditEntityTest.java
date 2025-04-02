package ru.magic3000.practice2.tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import ru.magic3000.practice2.dispatchers.EntityDispatcher;
import ru.magic3000.practice2.pojo.Addition;
import ru.magic3000.practice2.pojo.Entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class EditEntityTest extends BaseTest {
    /**
     * Tests the editing of an existing entity via API by updating its fields and verifying the changes.
     */
    @Test(description = "Edit Entity Test")
    @Description("Tests the editing of an existing entity via API by updating its fields and verifying the changes")
    public void EditEntity() {
        int entityId = getCreatedEntities().get(new Random().nextInt(getCreatedEntities().size()));
        Entity newEntity = Entity.builder()
                .title("Test title")
                .verified(false)
                .addition(Addition.builder()
                        .additional_info("Test info")
                        .additional_number(5)
                        .build())
                .important_numbers(Arrays.asList(3, 4, 5))
                .build();
        EntityDispatcher.editById(entityId, newEntity);

        Entity editedEntity = EntityDispatcher.getById(entityId);
        Map<String, Boolean> checks = Map.of(
                "title", editedEntity.getTitle().equals(newEntity.getTitle()),
                "isVerified", editedEntity.isVerified() == newEntity.isVerified(),
                "important_numbers", editedEntity.getImportant_numbers().equals(newEntity.getImportant_numbers()),
                "additional_info", editedEntity.getAddition().getAdditional_info().equals(newEntity.getAddition().getAdditional_info()),
                "additional_number", editedEntity.getAddition().getAdditional_number().equals(newEntity.getAddition().getAdditional_number())
        );
        checks.forEach((key, value) -> assertTrue(value, String.format("%s not equals", key)));
    }
}
