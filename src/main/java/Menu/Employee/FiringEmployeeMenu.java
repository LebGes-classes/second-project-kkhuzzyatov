package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class FiringEmployeeMenu {
    public static void fireEmployee() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ УВОЛЬНЕНИЯ СОТРУДНИКА ======\n\n");
        System.out.print("Введите телефон сотрудника, которого хотите уволить: ");
        String phone = scanner.nextLine();

        Employee emptyEmployee = new Employee();
        Employee thisEmployee = emptyEmployee.findEmployeeByPhone(phone);
        if (thisEmployee == null) {
            System.out.print("Сотрудник не найден.");
        } else {
            if (thisEmployee.getPosition().equals(emptyEmployee.getFiredPositionName())) {
                System.out.print("Сотрудник уже уволен.");
            } else {
                thisEmployee.fire();
                System.out.print("Сотрудник уволен.");
            }
        }

        MainMenu.moveToMainMenu();
    }
}
