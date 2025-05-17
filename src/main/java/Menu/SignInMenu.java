package Menu;

import Menu.Client.SignInClientMenu;
import Menu.Employee.SignInEmployeeMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class SignInMenu {
    public static void openSignInMenu() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== ВХОД ======\n\n");
        System.out.println("1. Войти как клиент");
        System.out.println("2. Войти как сотрудник");
        System.out.println("0. Перейти в главное меню");
        System.out.print("Выберите опцию: ");

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                SignInClientMenu.openSignInClientInterface();
                break;
            case 2:
                SignInEmployeeMenu.openSignInClientInterface();
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
