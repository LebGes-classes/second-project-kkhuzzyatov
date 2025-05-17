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

        System.out.println("====== МИНЮ ПОКУПКИ ТОВАРА ======\n\n");
        System.out.print("Введите id ячейки из которой хотите купить товар: ");
        int cellId = scanner.nextInt();
        System.out.print("Введите количество товара, которое хотите купить: ");
        int number = scanner.nextInt();

        boolean isFound = false;
        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            if (cell.getId() == cellId) {
                SalePoint emptySalePoint = new SalePoint();
                ArrayList<SalePoint> salePoints = emptySalePoint.getSalePoints();
                for (SalePoint salePoint : salePoints) {
                    if (salePoint.getId() == cell.getPointId()) {
                        salePoint.saleProduct(cellId, clientId, number);
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

    public static void printGoodsAvailableToBuy() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("id capacity point_id product_quantity");
        Cell emptyCell = new Cell();
        ArrayList<Cell> cells = emptyCell.getCells();
        for (Cell cell : cells) {
            System.out.println(cell.getId() + " " + cell.getCapacity() + " " + cell.getPointId() + " " + cell.getProductQuantity());
        }

    }
}
