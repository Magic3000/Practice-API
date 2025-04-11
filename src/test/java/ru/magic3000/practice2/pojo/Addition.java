package ru.magic3000.practice2.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Addition {
    public Integer id;
    public String additional_info;
    public Integer additional_number;

    @JsonCreator
    public Addition(
            @JsonProperty("id") Integer id,
            @JsonProperty("additional_info") String additional_info,
            @JsonProperty("additional_number") Integer additional_number) {
        this.id = id;
        this.additional_info = additional_info;
        this.additional_number = additional_number;
    }
}
