package dev.backendlab.inventory;

import dev.backendlab.inventory.io.ConsoleInputHandler;
import dev.backendlab.inventory.io.ConsoleOutputHandler;

public class Main {

    public static void main(String[] args) {
        ConsoleOutputHandler outputHandler = new ConsoleOutputHandler();
        ConsoleInputHandler inputHandler = new ConsoleInputHandler();

        Inventory inventory = new Inventory();

        InventoryConsole inventoryConsole = new InventoryConsole(inputHandler, outputHandler, inventory);

        inventoryConsole.run();
    }
}
