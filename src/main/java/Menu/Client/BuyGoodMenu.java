package Menu.Client;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.Cell;
import point.SalePoint;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BuyGoodMenu {
    public static void buyGood(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКУПОК ТОВАРОВ ======\n\n");
        System.out.print("Введите id ячейки в которую хотите закупить товары: ");
        int cellId = scanner.nextInt();
        System.out.print("Введите количество товара: ");
        int number = scanner.nextInt();

        boolean isFound = false;
        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            if (cell.getId() == cellId) {
                isFound = true;
                cell.manageProductQuantity(-number);
                System.out.print("Товары закуплены");
            }
        }
        emptyCell.updateCellFile(cells);
        if (!isFound) {
            System.out.print("Не удалось найти ячейку");
        }

        String stub = scanner.nextLine();
        ClientMenu.openClientMenu(clientId);
    }

    public static void printGoodsAvailableToBuy(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            System.out.println("Id: " + cell.getId() + " PointId: " + cell.getPointId() + " Capacity: " + cell.getCapacity() + " ProductQuantity: " + cell.getProductQuantity() + "ProductId: " + cell.getProductId());
        }

        scanner.nextLine();
        ClientMenu.openClientMenu(clientId);
    }
}
