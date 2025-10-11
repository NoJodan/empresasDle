package com.nojodan.empresasdle.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttributeComparison {
    private String attributeName;
    private String value;
    private String hint;
}