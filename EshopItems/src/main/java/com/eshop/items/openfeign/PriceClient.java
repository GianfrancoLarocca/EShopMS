package com.eshop.items.openfeign;


import com.eshop.items.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Set;

@FeignClient(name = "EshopPrice", url = "localhost:9001", configuration = {OpenFeignConfig.class})
public interface PriceClient {

    @GetMapping(value = "/api/price/item/{itemId}")
    BigDecimal getItemPrice(@PathVariable("itemId") long itemId);

    @PostMapping(value = "/api/price/set/{itemId}")
    void setItemPrice(@PathVariable("itemId") long itemId, @RequestBody BigDecimal price, Set<Long> itemCategories);
}
