package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SignInEmployeeMenu {
    public static void openSignInClientInterface() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== ВХОД В КАЧЕСТВЕ СОТРУДНИКА ======\n\n");

        System.out.print("Телефон: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        boolean isFound = false;
        String employeeLevel = "";
        Employee emptyEmployee = new Employee();
        ArrayList<Employee> employees = emptyEmployee.getEmployees();
        for (Employee employee : employees) {
            if (employee.getPhoneNumber().equals(phoneNumber)) {
                if (employee.getPasswordHash() == Utils.passwordCoder(password)) {
                    employeeLevel = employee.getPosition();
                    if (employeeLevel.equals(emptyEmployee.getL3PositionName())) {
                        EmployeeMenu.openEmployeeMenu(employee.getId());
                    }
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println("Неверный логин или пароль.");
        } else if (!employeeLevel.equals(emptyEmployee.getL3PositionName())) {
            System.out.println("Пользователь имеет недостаточный уровень должности для управления.");
        }

        MainMenu.moveToMainMenu();
    }
}
