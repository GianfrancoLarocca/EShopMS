package com.eshop.eshop.dto.converter;

import com.eshop.eshop.dto.ItemDto;
import com.eshop.eshop.models.ItemEntity;

public class ItemConverter {

    public ItemEntity itemDtoToitem(ItemDto itemDto) {
        ItemEntity item = new ItemEntity();
        item.setName(itemDto.getName());
        item.setDetails(itemDto.getDetails());
        item.setPrice(itemDto.getPrice());

        return item;
    }

    public ItemDto itemToItemDto(ItemEntity item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(item.getName());
        itemDto.setDetails(item.getDetails());
        itemDto.setPrice(item.getPrice());

        return itemDto;
    }
}
