package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class EmployeeMenu {
    public static void openEmployeeMenu(int clientId) throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ КЛИЕНТА ======\n\n");
        System.out.println("1. Закупить товар на склад");
        System.out.println("2. Получить информацию о товарах доступных к закупке");
        System.out.println("3. Нанять сотрудника");
        System.out.println("4. Уволить сотрудника");
        System.out.println("5. Повысить сотрудника");
        System.out.println("6. Открыть новый склад");
        System.out.println("7. Открыть новый пункт продаж");
        System.out.println("8. Закрыть склад");
        System.out.println("9. Закрыть пункт продаж");
        System.out.println("10. Получить информацию о складе");
        System.out.println("11. Получить информацию о пункте продаж");
        System.out.println("12. Получить информацию о товарах на складе");
        System.out.println("13. Получить информацию о товарах на пункте продаж");
        System.out.println("14. Добавить новый продукт");
        System.out.println("0. Перейти в главное меню");
        System.out.print("Выберите опцию: ");

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                ShoppingForStorageMenu.purchaseGoodsForStorage(clientId);
                break;
            case 2:
                AddingNewProductMenu.printProducts();
                break;
            case 3:
                HiringEmployeeMenu.hireEmployee();
                break;
            case 4:
                FiringEmployeeMenu.fireEmployee();
                break;
            case 5:
                PromotionEmployeeMenu.promoteEmployee();
                break;
            case 6:
                PointManagerMenu.openNewStorage();
                break;
            case 7:
                PointManagerMenu.openNewSalePoint();
                break;
            case 8:
                PointManagerMenu.closeStorage();
                break;
            case 9:
                PointManagerMenu.closeSalePoint();
                break;
            case 10:
                PointManagerMenu.printStorages();
                break;
            case 11:
                PointManagerMenu.printSalePoints();
                break;
            case 12:
                PointManagerMenu.printCellsOnStorage();
                break;
            case 13:
                PointManagerMenu.printCellsOnSalePoint();
                break;
            case 14:
                AddingNewProductMenu.addProduct();
                break;
            case 0:
                MainMenu.moveToMainMenu();
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }

        MainMenu.moveToMainMenu();
    }
}
