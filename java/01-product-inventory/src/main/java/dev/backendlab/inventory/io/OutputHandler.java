package dev.backendlab.inventory.io;

import dev.backendlab.inventory.shop.Product;

import java.util.List;

public interface OutputHandler {

    void showProductMenu();
    
    void showProductIdCreateMessage();

    void showInvalidProductIdMessage();

    void showProductAlreadyExistsMessage();

    void showProductNameCreateMessage();

    void showProductPriceCreateMessage();

    void showInvalidProductPriceMessage();

    void showProductStockCreateMessage();

    void showInvalidProductNameMessage();

    void showInvalidProductStockMessage();

    void showProductsGetMessage();

    void showProductsGetResultMessage(List<Product> products);

    void showStockEditMessage();

    void showProductNotExistsMessage();

    void showStockInputMessage();

    void showProductStockEditResultMessage();

    void showInvalidMenuInputMessage();
}
