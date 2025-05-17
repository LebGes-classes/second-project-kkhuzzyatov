package Menu;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu {
    public static void openMainMenu() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        System.out.println("====== ГЛАНОЕ МЕНЮ ======\n\n");
        System.out.println("1. Зарегистрироваться");
        System.out.println("2. Войти");
        System.out.println("0. Завершить программу");
        System.out.print("Выберите опцию: ");

        Scanner scanner = new Scanner(System.in);
        int choice;
        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                SignUpMenu.openSignUpMenu();
                break;
            case 2:
                SignInMenu.openSignInMenu();
                break;
            case 0:
                System.out.println("Выход из программы...");
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }

        MainMenu.moveToMainMenu();
    }

    public static void moveToMainMenu() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Нажмите любую клавишу, чтобы перейти в главное меню.");
        scanner.nextLine();
        MainMenu.openMainMenu();
    }
}
