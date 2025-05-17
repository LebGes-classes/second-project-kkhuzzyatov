package Menu;

import Menu.Client.SignUpClientMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class SignUpMenu {
    public static void openSignUpMenu() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== РЕГИСТРАЦИЯ ======\n\n");
        System.out.println("1. Зарегистрироваться как клиент");
        System.out.println("0. Перейти в главное меню");
        System.out.print("Выберите опцию: ");

        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                SignUpClientMenu.openSignUpClientInterface();
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
