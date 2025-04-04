package ru.magic3000.practice2.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class Entity {
    public Integer id;
    public String title;
    public boolean verified;
    public Addition addition;
    public List<Integer> important_numbers;

    @JsonCreator
    public Entity(
            @JsonProperty("id") Integer id,
            @JsonProperty("title") String title,
            @JsonProperty("verified") boolean verified,
            @JsonProperty("addition") Addition addition,
            @JsonProperty("important_numbers") List<Integer> important_numbers) {
        this.id = id;
        this.title = title;
        this.verified = verified;
        this.addition = addition;
        this.important_numbers = important_numbers;
    }
}
