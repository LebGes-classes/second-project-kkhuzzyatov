package Menu.Client;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import users.Client;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SignUpClientMenu {
    public static void openSignUpClientInterface() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== РЕГИСТРАЦИЯ КЛИЕНТА ======\n\n");

        System.out.print("Имя: ");
        String firstName = scanner.nextLine();

        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();

        System.out.print("Телефон: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        Client client = new Client();
        ArrayList<Client> clients = client.getClients();
        clients.add(new Client(clients.size(), firstName, lastName, phoneNumber, Utils.passwordCoder(password)));
        client.updateClientsFile(clients);
        System.out.println("Вы успешно зарегистрировали клиента.");

        MainMenu.moveToMainMenu();
    }
}
