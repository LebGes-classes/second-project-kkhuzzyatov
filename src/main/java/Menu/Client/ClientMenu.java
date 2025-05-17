package Menu.Client;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class ClientMenu {
    public static void openClientMenu(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ КЛИЕНТА ======\n\n");
        System.out.println("1. Купить товар");
        System.out.println("2. Вернуть товар");
        System.out.println("3. Список товаров которые можно купить");
        System.out.println("4. Список покупок пользователя");
        System.out.println("0. Перейти в главное меню");
        System.out.print("Выберите опцию: ");

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                BuyGoodMenu.buyGood(clientId);
                break;
            case 2:
                RefundGoodMenu.refundGood(clientId);
                break;
            case 3:
                BuyGoodMenu.printGoodsAvailableToBuy();
                break;
            case 4:
                RefundGoodMenu.printAllPurchases(clientId);
                break;
            case 0:
                MainMenu.moveToMainMenu();
                break;
            default:
                System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                openClientMenu(clientId);
                break;
        }
    }
}
