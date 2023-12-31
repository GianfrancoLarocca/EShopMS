package com.eshop.price.dtos.mapper;

import com.eshop.price.dtos.PriceDto;
import com.eshop.price.entities.PriceEntity;

import java.util.stream.Collectors;

public class PriceMapper {

    public static PriceEntity dtoToEntity(PriceDto priceDto){

        PriceEntity price = new PriceEntity();
        price.setPriceId(priceDto.getId());
        price.setItemId(priceDto.getItemId());
        price.setPrice(priceDto.getPrice());
        price.setSales(priceDto.getSales().stream().map(SaleMapper::dtoToEntity).collect(Collectors.toSet()));
        price.setItemCategoriesId(priceDto.getItemCategoriesId());
        price.setIva(priceDto.getIva());
        return price;
    }

    public static PriceDto entityToDto(PriceEntity price){

        PriceDto priceDto = new PriceDto();
        priceDto.setId(price.getPriceId());
        priceDto.setItemId(price.getItemId());
        priceDto.setPrice(price.getPrice());
        priceDto.setSales(price.getSales().stream().map(SaleMapper::entityToDto).collect(Collectors.toSet()));
        priceDto.setItemCategoriesId(price.getItemCategoriesId());
        priceDto.setIva(price.getIva());
        return priceDto;
    }
}
