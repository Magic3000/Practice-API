package ru.magic3000.practice2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Addition {
    private Integer id;
    private String additional_info;
    private Integer additional_number;
}
