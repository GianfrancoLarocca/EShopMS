package com.eshop.price.dtos.mapper;

import com.eshop.price.dtos.PriceDto;
import com.eshop.price.entities.PriceEntity;

import java.util.stream.Collectors;

public class PriceMapper {

    public static PriceEntity dtoToEntity(PriceDto priceDto){

        PriceEntity price = new PriceEntity();
        price.setPriceId(priceDto.getId());
        price.setPrice(priceDto.getPrice());
        price.setSales(priceDto.getSales().stream().map(SaleMapper::dtoToEntity).collect(Collectors.toSet()));
        return price;
    }

    public static PriceDto entityToDto(PriceEntity price){

        PriceDto priceDto = new PriceDto();
        priceDto.setId(price.getPriceId());
        priceDto.setPrice(price.getPrice());
        priceDto.setSales(price.getSales().stream().map(SaleMapper::entityToDto).collect(Collectors.toSet()));
        return priceDto;
    }
}