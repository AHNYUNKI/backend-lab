package dev.backendlab.inventory;

import dev.backendlab.inventory.io.InputHandler;
import dev.backendlab.inventory.io.OutputHandler;
import dev.backendlab.inventory.shop.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class InventoryConsole {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final Inventory inventory;

    public InventoryConsole(InputHandler inputHandler, OutputHandler outputHandler, Inventory inventory) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.inventory = inventory;
    }

    public void run() {
        try {
            while (true) {
                outputHandler.showProductMenu();
                String firstMenuInput = inputHandler.readLine();

                if (isCreateProductSelected(firstMenuInput)) {
                    createProductProcess();

                } else if (isGetProductSelected(firstMenuInput)) {
                    getProductsProcess();

                } else if (isStockEditSelected(firstMenuInput)) {
                    editProductStockProcess();
                } else if (isExitSelected(firstMenuInput)) {
                    break;
                } else {
                    outputHandler.showInvalidMenuInputMessage();
                }
            }
        } catch (NoSuchElementException e) {
            outputHandler.showInvalidMenuInputMessage();
        }
    }

    private boolean isExitSelected(String firstMenuInput) {
        return firstMenuInput.equals("4");
    }

    private void editProductStockProcess() {
        outputHandler.showStockEditMessage();
        outputHandler.showProductIdCreateMessage();

        String productIdInput = inputHandler.readLine();
        if (isNullOrEmpty(productIdInput)) {
            outputHandler.showInvalidProductIdMessage();
            return;
        }

        if (cannotParseLong(productIdInput)) {
            outputHandler.showInvalidProductIdMessage();
            return;
        }

        long productId = Long.parseLong(productIdInput);

        if (inventory.isNotExistsProduct(productId)) {
            outputHandler.showProductNotExistsMessage();
            return;
        }

        outputHandler.showStockInputMessage();
        String stockInput = inputHandler.readLine();

        if (isNullOrEmpty(stockInput)) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        if (cannotParseInt(stockInput)) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        int stock = Integer.parseInt(stockInput);

        if (stock < 0) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        inventory.changeStock(productId, stock);

        outputHandler.showProductStockEditResultMessage();
    }

    // [리뷰: 선택] 이 메서드는 상품 하나가 아니라 전체 목록을 보여줍니다. listProducts로 이름을 맞추면
    // 단건 조회와 구분됩니다. 메뉴 판별과 출력 메서드에도 목록이라는 의미를 일관되게 사용해 보세요.
    // 수정완료.
    private void getProductsProcess() {
        outputHandler.showProductsGetMessage();

        List<Product> products = inventory.findAll();
        outputHandler.showProductsGetResultMessage(products);
    }

    private boolean isStockEditSelected(String firstMenuInput) {
        return firstMenuInput.equals("3");
    }

    private static boolean isGetProductSelected(String firstMenuInput) {
        return firstMenuInput.equals("2");
    }

    private void createProductProcess() {
        outputHandler.showProductIdCreateMessage();
        String idInput = inputHandler.readLine();

        if (isNullOrEmpty(idInput)) {
            outputHandler.showInvalidProductIdMessage();
            return;
        }

        if (cannotParseLong(idInput)) {
            outputHandler.showInvalidProductIdMessage();
            return;
        }

        Long productId = Long.parseLong(idInput);

        if (inventory.existsById(productId)) {
            outputHandler.showProductAlreadyExistsMessage();
            return;
        }


        outputHandler.showProductNameCreateMessage();
        String nameInput = inputHandler.readLine();

        if (isNullOrEmpty(nameInput)) {
            outputHandler.showInvalidProductNameMessage();
            return;
        }

        outputHandler.showProductPriceCreateMessage();
        String priceInput = inputHandler.readLine();

        if (isNullOrEmpty(priceInput)) {
            outputHandler.showInvalidProductPriceMessage();
            return;
        }

        if (cannotParseInt(priceInput)) {
            outputHandler.showInvalidProductPriceMessage();
            return;
        }
        ;

        int price = Integer.parseInt(priceInput);

        if (price < 0) {
            outputHandler.showInvalidProductPriceMessage();
            return;
        }

        outputHandler.showProductStockCreateMessage();
        String stockInput = inputHandler.readLine();

        if (isNullOrEmpty(stockInput)) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        if (cannotParseInt(stockInput)) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        int stock = Integer.parseInt(stockInput);

        if (stock < 0) {
            outputHandler.showInvalidProductStockMessage();
            return;
        }

        inventory.createProduct(productId, nameInput, price, stock);
    }

    private boolean cannotParseInt(String userInput) {
        try {
            Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    // [리뷰: 선택] 이 검사는 이름뿐 아니라 ID·가격·재고에도 쓰입니다.
    // userInputProductName을 userInput으로 바꾸면 특정 입력 전용이라는 오해를 줄일 수 있습니다.
    private static boolean isNullOrEmpty(String userInput) {
        return userInput == null || userInput.isEmpty();
    }

    // [리뷰: 선택] cannotParseInt는 판별만 하지만 이 메서드는 오류 문구까지 출력합니다.
    // 호출하는 쪽에서 메시지를 출력하도록 맞추면 숫자 판별과 화면 안내를 따로 테스트하고 재사용하기 쉽습니다.
    // 수정완료
    private boolean cannotParseLong(String userInput) {
        try {
            Long.parseLong(userInput);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    private boolean isCreateProductSelected(String firstMenuInput) {
        return firstMenuInput.equals("1");
    }

}
