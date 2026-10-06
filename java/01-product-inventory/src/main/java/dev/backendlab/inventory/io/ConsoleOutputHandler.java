package dev.backendlab.inventory.io;

import dev.backendlab.inventory.shop.Product;

import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {

    @Override
    public void showProductMenu() {
        System.out.println("1.상품등록");
        System.out.println("2.상품목록조회");
        System.out.println("3.상품재고변경");
        System.out.println("4.종료");
    }

    @Override
    public void showProductIdCreateMessage() {
        System.out.println("상품ID를 입력하세요.");
    }

    @Override
    public void showInvalidProductIdMessage() {
        System.out.println("유효하지 않은 상품ID입니다.");
    }

    @Override
    public void showProductAlreadyExistsMessage() {
        System.out.println("이미 존재하는 상품ID입니다.");
    }

    @Override
    public void showProductNameCreateMessage() {
        System.out.println("상품명을 입력하세요.");
    }

    @Override
    public void showProductPriceCreateMessage() {
        System.out.println("상품가격을 입력하세요.");
    }

    @Override
    public void showInvalidProductPriceMessage() {
        System.out.println("유효하지 않은 상품가격입니다.");
    }

    @Override
    public void showProductStockCreateMessage() {
        System.out.println("상품재고를 입력하세요.");
    }

    @Override
    public void showInvalidProductNameMessage() {
        System.out.println("유효하지 않은 상품명입니다.");
    }

    @Override
    public void showInvalidProductStockMessage() {
        System.out.println("유효하지 않은 상품재고입니다.");
    }

    @Override
    public void showProductsGetMessage() {
        System.out.println("상품목록을 조회합니다.");
        System.out.println("상품ID|상품명|상품가격|상품재고");
    }

    @Override
    public void showProductsGetResultMessage(List<Product> products) {
        products.forEach(
                product ->
                        System.out.println(product.getId()
                                + "|"
                                + product.getName()
                                + "|"
                                + product.getPrice()
                                + "|"
                                + product.getStock()));
    }

    @Override
    public void showStockEditMessage() {
        System.out.println("재고를 수정합니다.");
    }

    @Override
    public void showProductNotExistsMessage() {
        System.out.println("상품이 존재하지 않습니다.");
    }

    @Override
    public void showStockInputMessage() {
        System.out.println("상품재고를 입력하세요.");
    }

    @Override
    public void showProductStockEditResultMessage() {
        System.out.println("상품재고를 수정했습니다.");
    }

    @Override
    public void showInvalidMenuInputMessage() {
        System.out.println("유효하지 않은 메뉴 입력입니다.");
    }

}
