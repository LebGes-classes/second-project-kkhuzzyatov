package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.Cell;
import point.Storage;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingForStorageMenu {
    public static void purchaseGoodsForStorage(int storageId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКУПОК ТОВАРОВ ======\n\n");
        System.out.print("Введите id ячейки в которую хотите закупить товары: ");
        int cellId = scanner.nextInt();
        System.out.print("Введите количество товара, которое хотите закупить: ");
        int number = scanner.nextInt();

        boolean isFound = false;
        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            if (cell.getId() == cellId) {
                Storage emptyStorage = new Storage();
                ArrayList<Storage> storages = emptyStorage.getStorages();
                for (Storage storage : storages) {
                    if (storage.getId() == cell.getPointId()) {
                        storage.purchaseGoods(cellId, number);
                        isFound = true;
                    }
                }
            }
        }
        if (!isFound) {
            System.out.print("Не удалось найти ячейку");
        }

        MainMenu.moveToMainMenu();
    }

    public static void printProducts(int storageId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКУПОК ТОВАРОВ ======\n\n");
        System.out.print("Введите id ячейки в которую хотите закупить товары: ");
        int cellId = scanner.nextInt();
        System.out.print("Введите количество товара, которое хотите закупить: ");
        int number = scanner.nextInt();

        boolean isFound = false;
        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            if (cell.getId() == cellId) {
                Storage emptyStorage = new Storage();
                ArrayList<Storage> storages = emptyStorage.getStorages();
                for (Storage storage : storages) {
                    if (storage.getId() == cell.getPointId()) {
                        storage.purchaseGoods(cellId, number);
                        isFound = true;
                    }
                }
            }
        }
        if (!isFound) {
            System.out.print("Не удалось найти ячейку");
        }

        MainMenu.moveToMainMenu();
    }
}
