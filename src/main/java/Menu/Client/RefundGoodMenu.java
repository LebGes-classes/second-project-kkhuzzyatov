package Menu.Client;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.Cell;
import point.SalePoint;
import purchase.Purchase;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RefundGoodMenu {
    public static void refundGood(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ПОКУПКИ ТОВАРА ======\n\n");
        System.out.print("Введите id покупки которую хотите вернуть: ");
        int purchaseId = scanner.nextInt();

        boolean isFound = false;
        Purchase emptyPurchase = new Purchase();
        ArrayList<Purchase> purchases = emptyPurchase.getPurchases();
        for (Purchase purchase : purchases) {
            if (purchase.getId() == purchaseId && purchase.getUserId() == clientId) {
                SalePoint emptySalePoint = new SalePoint();
                ArrayList<SalePoint> salePoints = emptySalePoint.getSalePoints();
                for (SalePoint salePoint : salePoints) {
                    if (salePoint.getId() == purchase.getPointId()) {
                        salePoint.refundProduct(purchaseId);
                        isFound = true;
                    }
                }
            }
        }

        if (!isFound) {
            System.out.print("Не удалось найти покупку");
        }

        scanner.nextInt();
        ClientMenu.openClientMenu(clientId);
    }

    public static void printAllPurchases(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("id user_id number_of_goods product_id point_id");
        Purchase emptyPurchase = new Purchase();
        ArrayList<Purchase> purchases = emptyPurchase.getPurchases();
        for (Purchase purchase : purchases) {
            if (purchase.getStatus().equals("sold") && purchase.getUserId() == clientId) {
                System.out.println(purchase.getId() + " " + purchase.getUserId() + " " + purchase.getNumberOfGoods() + " " + purchase.getProductId() + " " + purchase.getPointId());
            }
        }

        scanner.nextInt();
        ClientMenu.openClientMenu(clientId);
    }
}
