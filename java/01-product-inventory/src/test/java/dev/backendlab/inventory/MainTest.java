package dev.backendlab.inventory;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    @Test
    void showsInventoryAndExits() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream testOut =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            try {
                System.setIn(new ByteArrayInputStream(
                        "2\n4\n".getBytes(StandardCharsets.UTF_8)));
                System.setOut(testOut);

                assertDoesNotThrow(() -> Main.main(new String[0]));
                assertTrue(output.toString(StandardCharsets.UTF_8)
                        .contains("상품목록을 조회합니다."));
            } finally {
                System.setIn(originalIn);
                System.setOut(originalOut);
            }
        }
    }
}
