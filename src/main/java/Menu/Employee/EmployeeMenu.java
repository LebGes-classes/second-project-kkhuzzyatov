package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class EmployeeMenu {
    public static void openEmployeeMenu() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ СОТРУДНИКА ======\n\n");
        System.out.println("0.   Перейти в главное меню");
        System.out.println("1.   Открыть новый склад");
        System.out.println("2.   Получить информацию о складе");
        System.out.println("3.   Закрыть склад");
        System.out.println("4.   Нанять сотрудника");
        System.out.println("5.   Повысить сотрудника");
        System.out.println("6.   Уволить сотрудника");
        System.out.println("7.   Добавить новый продукт");
        System.out.println("8.   Получить информацию о товарах доступных к закупке");
        System.out.println("9.   Создать ячейку");
        System.out.println("10.  Закупить товар на склад");
        System.out.println("11.  Получить информацию о товарах на складе");
        System.out.println("12.  Открыть новый пункт продаж");
        System.out.println("13.  Получить информацию о пункте продаж");
        System.out.println("14.  Получить информацию о товарах на пункте продаж");
        System.out.println("15.  Закрыть пункт продаж");
        System.out.print("Выберите опцию: ");

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 0:
                MainMenu.moveToMainMenu();
                break;
            case 1:
                PointManagerMenu.openNewStorage();
                break;
            case 2:
                PointManagerMenu.printStorages();
                break;
            case 3:
                PointManagerMenu.closeStorage();
                break;
            case 4:
                HiringEmployeeMenu.hireEmployee();
                break;
            case 5:
                PromotionEmployeeMenu.promoteEmployee();
                break;
            case 6:
                FiringEmployeeMenu.fireEmployee();
                break;
            case 7:
                AddingNewProductMenu.addProduct();
                break;
            case 8:
                AddingNewProductMenu.printProducts();
                break;
            case 9:
                PointManagerMenu.addCell();
                break;
            case 10:
                ShoppingForStorageMenu.purchaseGoodsForStorage();
                break;
            case 11:
                PointManagerMenu.printCellsOnStorage();
                break;
            case 12:
                PointManagerMenu.openNewSalePoint();
                break;
            case 13:
                PointManagerMenu.printSalePoints();
                break;
            case 14:
                PointManagerMenu.printCellsOnSalePoint();
                break;
            case 15:
                PointManagerMenu.closeSalePoint();
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }

        MainMenu.moveToMainMenu();
    }

    public static void moveToEmployeeMenu() throws IOException, InvalidFormatException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nНажмите любую клавишу, чтобы перейти в меню сотрудника.");
        scanner.nextLine();

        Utils.clearConsole();
        openEmployeeMenu();
    }
}
