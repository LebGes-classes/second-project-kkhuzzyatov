package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class PromotionEmployeeMenu {
    public static void promoteEmployee() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ПОВЫШЕНИЯ СОТРУДНИКА ======\n\n");
        System.out.print("Введите телефон сотрудника, которого хотите повысить: ");
        String phone = scanner.nextLine();

        Employee emptyEmployee = new Employee();
        Employee thisEmployee = emptyEmployee.findEmployeeByPhone(phone);
        if (thisEmployee == null) {
            System.out.print("Сотрудник не найден.");
        } else {
            System.out.print(thisEmployee.promote());
        }

        EmployeeMenu.moveToEmployeeMenu();
    }
}
