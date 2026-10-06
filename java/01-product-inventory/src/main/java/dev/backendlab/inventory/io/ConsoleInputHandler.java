package dev.backendlab.inventory.io;

import java.io.EOFException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    // [리뷰: 필수] static Scanner는 최초 System.in을 계속 잡고 있어 같은 JVM에서 입력을 교체한 뒤
    // Main을 다시 실행하면 새 입력 대신 소진된 이전 입력을 읽어 NoSuchElementException이 발생합니다.
    // Scanner를 private 인스턴스 필드로 두고 인스턴스마다 입력원을 받도록 하여 실행·테스트를 분리하세요.
    // 수정완료
    private final Scanner scanner;

    public ConsoleInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        // [리뷰: 필수] 메뉴 또는 상품 등록 도중 EOF가 오면 nextLine()이 예외를 던져 비정상 종료합니다.
        // InputHandler의 EOF 규약을 정하고 run과 입력 도중의 종료 처리까지 연결하세요.
        // null을 종료 신호로 쓴다면 메뉴에서 equals를 호출하기 전에 종료 여부를 확인해야 합니다.

        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException();
        }
        return scanner.nextLine();
    }

}
