package com.eshop.price.exceptions;

public class PriceNotFoundException extends RuntimeException{

    public PriceNotFoundException(String message) {
        super(message);
    }
}
