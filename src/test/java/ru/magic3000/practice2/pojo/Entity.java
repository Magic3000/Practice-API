package ru.magic3000.practice2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Entity {
    private Integer id;
    private String title;
    private boolean verified;
    private Addition addition;
    private List<Integer> important_numbers;
}
