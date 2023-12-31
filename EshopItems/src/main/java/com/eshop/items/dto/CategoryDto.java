package com.eshop.items.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2283632856812370109L;

    private long id;
    private String CategoryName;
}
