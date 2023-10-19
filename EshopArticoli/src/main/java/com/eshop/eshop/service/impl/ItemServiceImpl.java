package com.eshop.eshop.service.impl;

import com.eshop.eshop.dto.ItemDto;
import com.eshop.eshop.dto.ItemResponse;
import com.eshop.eshop.dto.converter.ItemConverter;
import com.eshop.eshop.exceptions.ItemNotFoundException;
import com.eshop.eshop.models.ItemCategoryEntity;
import com.eshop.eshop.models.ItemEntity;
import com.eshop.eshop.repository.ItemRepository;
import com.eshop.eshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames={"items"})
public class ItemServiceImpl implements ItemService {

    @Autowired
    CacheManager cacheManager;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Cacheable
    public ItemResponse getAllItems(int pageNumber, int pageSize) {

        System.err.println("ALL ITEMS:");

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<ItemEntity> items = itemRepository.findAll(pageable);
        List<ItemEntity> listOfItem = items.getContent();

        List<ItemDto> itemsDto = new ArrayList<>();

        for(ItemEntity item: listOfItem){
            itemsDto.add(ItemConverter.itemToItemDto(item));
        }

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setContent(itemsDto);
        itemResponse.setPageNumber(items.getNumber());
        itemResponse.setPageSize(items.getSize());
        itemResponse.setTotalElements(items.getTotalElements());
        itemResponse.setTotalPages(items.getTotalPages());
        itemResponse.setLast(items.isLast());

        return itemResponse;
    }

    @Override
    @Cacheable(value = "item", key = "#id", sync = true)
    public ItemDto getItemById(long id) {

        System.err.println("ITEM BY ID:");

        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item could not be found"));

        return ItemConverter.itemToItemDto(item);
    }

    @Override
    @Cacheable(value = "category")
    public List<ItemDto> getItemsByCategory(String categoryName) {

        System.err.println("ITEM BY CATEGORY:");

        String filter = "%" + categoryName.toUpperCase() + "%";

        List<ItemEntity> items = itemRepository.selItemsByCategory(filter);

        return items.stream().map(ItemConverter::itemToItemDto).toList();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "items", allEntries = true),
            @CacheEvict(cacheNames = "category", allEntries = true),
    })
    public ItemDto createItem(ItemDto itemDto) {

        System.err.println("ITEM CREATED:");

        ItemEntity item = ItemConverter.itemDtoToitem(itemDto);

        ItemEntity newItem = itemRepository.save(item);

        return ItemConverter.itemToItemDto(newItem);
    }

    @Override
    @Caching(put = {
            @CachePut(cacheNames = "item", key = "#id"),
    },
    evict = {
            @CacheEvict(cacheNames = "items", allEntries = true),
            @CacheEvict(cacheNames = "category", allEntries = true),
    })
    public ItemDto updateItem(ItemDto itemDto, long id) {

        System.err.println("ITEM UPDATED:");

        itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item could not be updated"));

        if(itemDto.getId() != id){
            throw new ItemNotFoundException("Item could not be updated");
        }

        ItemEntity item = ItemConverter.itemDtoToitem(itemDto);

        ItemEntity updatedItem = itemRepository.save(item);

        return ItemConverter.itemToItemDto(updatedItem);
    }

    @Override
    public void deleteItemById(long id) {
        System.err.println("ITEM DELETED:");
        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item could not be deleted"));
        itemRepository.delete(item);
        this.evictCache(item.getItemId());
    }

    @Override
    public ItemDto setItemCategory(Long id, ItemCategoryEntity category) {

        System.err.println("SET ITEM CATEGORY:");

        ItemEntity item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));

        List<ItemCategoryEntity> categories = item.getCategories();
        categories.add(category);
        item.setCategories(categories);

        this.evictCache(id);

        return ItemConverter.itemToItemDto(itemRepository.save(item));
    }

    public void evictCache(Long id){
        cacheManager.getCache("item").evict(id);
        cacheManager.getCache("items").clear();
        cacheManager.getCache("category").clear();
        System.out.println("cache pulita");
    }

}
