package com.nojodan.empresasdle.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GuessResponse {
    private boolean correct;
    private String itemName;
    private List<AttributeComparison> comparisons;
}