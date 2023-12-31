package com.eshop.items.dto.converter;

import com.eshop.items.dto.ItemDto;
import com.eshop.items.entities.ItemEntity;

public class ItemConverter {

    public static ItemEntity itemDtoToitem(ItemDto itemDto) {
        ItemEntity item = new ItemEntity();
        item.setItemId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDetails(itemDto.getDetails());
        item.setCategories(itemDto.getCategories());

        return item;
    }

    public static ItemDto itemToItemDto(ItemEntity item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getItemId());
        itemDto.setName(item.getName());
        itemDto.setDetails(item.getDetails());
        itemDto.setCategories(item.getCategories());

        return itemDto;
    }
}
