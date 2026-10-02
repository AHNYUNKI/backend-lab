package dev.backendlab.inventory;

import dev.backendlab.inventory.shop.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Product> products = new ArrayList<>();

        boolean isProductRegistered = true;
        while (isProductRegistered) {
            System.out.println("1.상품등록");
            System.out.println("2.상품목록조회");
            System.out.println("3.상품재고변경");

            String userInput = br.readLine();

            if (userInput.equals("1")) {
                System.out.println("상품 ID를 입력해주세요.(숫자 타입만 입력 가능합니다.)");
                String productId = br.readLine();
                try {
                    Long.parseLong(productId);
                } catch (NumberFormatException e) {
                    System.out.println("숫자 타입만 입력 가능합니다.");
                    continue;
                }

                long id = Long.parseLong(productId);

                Product findProduct = products.stream()
                        .filter(product -> product.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (findProduct != null) {
                    System.out.printf("이미 등록된 상품입니다.");
                    continue;
                }

                System.out.println("상품명을 입력해주세요.");
                String name = br.readLine();

                System.out.println("상품 금액을 입력해주세요.");
                int price = Integer.parseInt(br.readLine());
                if (price < 0) {
                    System.out.println("상품 금액은 양수여야합니다.");
                    continue;
                }

                System.out.println("상품 재고를 입력해주세요.");
                int stock = Integer.parseInt(br.readLine());
                if (stock < 0) {
                    System.out.println("상품 재고는 양수여야합니다.");
                    continue;
                }

                products.add(Product.create(id, name, price, stock));

                System.out.println("상품 등록이 완료되었습니다.");
                System.out.println("1. 종료");
                System.out.println("2. 메뉴화면으로");

                String userInput2 = br.readLine();
                if (userInput2.equals("1")) {
                    isProductRegistered = false;
                    break;
                } else if (userInput2.equals("2")) {
                    continue;
                } else {
                    throw new IllegalArgumentException("잘못된 입력입니다.");
                }

            } else if (userInput.equals("2")) {
                System.out.println("현재 재고 목록");
                System.out.println("상품 ID|상품명|상품 금액|상품 재고");
                System.out.println("===================================");
                for (Product product : products) {
                    System.out.printf("%d|%s|%d|%d\n", product.getId(), product.getName(), product.getPrice(), product.getStock());
                }

                System.out.println("1. 종료");
                System.out.println("2. 메뉴화면으로");

                String userInput3 = br.readLine();
                if (userInput3.equals("1")) {
                    isProductRegistered = false;
                    break;
                } else if (userInput3.equals("2")) {
                    continue;
                } else {
                    throw new IllegalArgumentException("잘못된 입력입니다.");
                }
            } else if (userInput.equals("3")) {
                System.out.println("변경할 재고의 ID를 입력해주세요.");
                long id = Long.parseLong(br.readLine());
                Product findProduct = products.stream()
                        .filter(product -> product.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (findProduct == null) {
                    throw new IllegalArgumentException("해당 ID의 상품이 존재하지 않습니다.");
                }

                System.out.println("변경할 재고의 수량을 입력해주세요.");
                int changeStock = Integer.parseInt(br.readLine());

                if(changeStock < 0) {
                    throw new IllegalArgumentException("재고는 0보다 작을 수 없습니다.");
                }

                findProduct.changeStock(changeStock);

                System.out.println("재고가 변경되었습니다.");
                System.out.println("1. 종료");
                System.out.println("2. 메뉴화면으로");

                String userInput3 = br.readLine();
                if (userInput3.equals("1")) {
                    isProductRegistered = false;
                    break;
                } else if (userInput3.equals("2")) {
                    continue;
                } else {
                    throw new IllegalArgumentException("잘못된 입력입니다.");
                }
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }


    }
}
