package Menu.Client;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import users.Client;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SignInClientMenu {
    public static void openSignInClientInterface() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== ВХОД В КАЧЕСТВЕ КЛИЕНТА ======\n\n");

        System.out.print("Телефон: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        boolean isFound = false;
        Client emptyClient = new Client();
        ArrayList<Client> clients = emptyClient.getClients();
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(phoneNumber)) {
                if (client.getPasswordHash() == Utils.passwordCoder(password)) {
                    ClientMenu.openClientMenu(client.getId());
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println("Неверный логин или пароль.");
        }

        MainMenu.moveToMainMenu();
    }
}
