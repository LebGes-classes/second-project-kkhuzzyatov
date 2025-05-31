package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.SalePoint;
import point.Storage;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HiringEmployeeMenu {
    public static void hireEmployee() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ НАЙМА СОТРУДНИКА ======\n\n");
        System.out.print("Введите \"storage\" или \"sell_point\", чтобы нанять сотрудника: ");
        String pointType = scanner.nextLine();

        switch (pointType) {
            case "storage":
                hireEmployeeToStorage();
                break;
            case "sell_point":
                hireEmployeeToSalePoint();
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private static void hireEmployeeToStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите storage address в который нужно нанять сотрудника: ");
        String address = scanner.nextLine();

        Storage emptyStorage = new Storage();
        if (emptyStorage.findStorageByAddress(address) == null) {
            System.out.print("Не существует склада с указанным address.");
        } else {
            System.out.print("Введите имя сотрудника, которого хотите нанять: ");
            String phone = scanner.nextLine();

            System.out.print("Введите фамилию сотрудника, которого хотите нанять: ");
            String firstName = scanner.nextLine();

            System.out.print("Введите телефон сотрудника, которого хотите нанять: ");
            String lastName = scanner.nextLine();

            System.out.print("Введите пароль для нового сотрудника: ");
            String password = scanner.nextLine();

            Employee emptyEmployee = new Employee();
            Employee thisEmployee = emptyEmployee.findEmployeeByPhone(phone);
            if (thisEmployee == null) {
                thisEmployee = new Employee(emptyEmployee.getEmployees().size() + 1, firstName, lastName, phone, Utils.passwordCoder(password), emptyStorage.findStorageByAddress(address).getId());
                ArrayList<Employee> employees = emptyEmployee.getEmployees();
                employees.add(thisEmployee);
                emptyEmployee.updateEmployeesFile(employees);
            } else {
                if (thisEmployee.getPosition().equals(emptyEmployee.getFiredPositionName())) {
                    thisEmployee.hire();
                    System.out.print("Сотрудник снова нанят.");
                } else {
                    System.out.print("Сотрудник уже нанят.");
                }
            }
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    private static void hireEmployeeToSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите SalePoint address в который нужно нанять сотрудника: ");
        String address = scanner.nextLine();

        SalePoint emptySalePoint = new SalePoint();
        if (emptySalePoint.findSalePointByAddress(address) == null) {
            System.out.print("Не существует очки продаж с указанным address.");
        } else {
            System.out.print("Введите имя сотрудника, которого хотите нанять: ");
            String phone = scanner.nextLine();

            System.out.print("Введите фамилию сотрудника, которого хотите нанять: ");
            String firstName = scanner.nextLine();

            System.out.print("Введите телефон сотрудника, которого хотите нанять: ");
            String lastName = scanner.nextLine();

            System.out.print("Введите пароль для нового сотрудника: ");
            String password = scanner.nextLine();

            Employee emptyEmployee = new Employee();
            Employee thisEmployee = emptyEmployee.findEmployeeByPhone(phone);
            if (thisEmployee == null) {
                thisEmployee = new Employee(emptyEmployee.getEmployees().size() + 1, firstName, lastName, phone, Utils.passwordCoder(password), emptySalePoint.findSalePointByAddress(address).getId());
                ArrayList<Employee> employees = emptyEmployee.getEmployees();
                employees.add(thisEmployee);
                emptyEmployee.updateEmployeesFile(employees);
            } else {
                if (thisEmployee.getPosition().equals(emptyEmployee.getFiredPositionName())) {
                    thisEmployee.hire();
                    System.out.print("Сотрудник снова нанят.");
                } else {
                    System.out.print("Сотрудник уже нанят.");
                }
            }
        }

        EmployeeMenu.moveToEmployeeMenu();
    }
}
