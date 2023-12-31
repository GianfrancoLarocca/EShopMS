package com.eshop.price.service;

import com.eshop.price.dtos.ItemClientRequestPriceCategory;
import com.eshop.price.dtos.PriceDto;
import com.eshop.price.dtos.SaleDto;

import java.math.BigDecimal;
import java.util.List;

public interface PriceService {

    PriceDto getPriceById(long priceId);
    BigDecimal getPriceByItem(long itemId);
    PriceDto setPriceToItem(long itemId, BigDecimal price);
    PriceDto setItemPriceAndCategories(ItemClientRequestPriceCategory itemClientRequest);
    PriceDto setPriceSaleSingleByPriceId(long priceId, SaleDto saleDto);
    PriceDto setPriceSaleSingleByItemId(long itemId, SaleDto saleDto);
    List<PriceDto> setPriceSaleByCategory(long saleId, long category);
    List<PriceDto> setPriceSaleAll(long saleId);
    String removeSaleSingleByItemId(long itemId, SaleDto saleDto);
    String removeSale(long saleId);
    String removeSaleFromSinglePrice(long itemId, long saleId);
}
