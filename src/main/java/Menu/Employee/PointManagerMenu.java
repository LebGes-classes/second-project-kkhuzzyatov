package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.Cell;
import point.SalePoint;
import point.Storage;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PointManagerMenu {
    public static void openNewStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ОТКРЫТИЯ СКЛАДА ======\n\n");
        System.out.print("Введите адрес места, где хотите открыть склад: ");
        String address = scanner.nextLine();

        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage == null) {
            ArrayList<Storage> storages = emptyStorage.getStorages();
            storages.add(new Storage(emptyStorage.getStorages().size() + 1, address));
            emptyStorage.updateStorageFile(storages);
        } else {
            System.out.print("На указанном адресе уже открыт склад.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void openNewSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ТОЧКИ ПРОДАЖ ======\n\n");
        System.out.print("Введите адрес места, где хотите открыть точку продаж: ");
        String address = scanner.nextLine();

        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisStorage = emptySalePoint.findSalePointByAddress(address);
        if (thisStorage == null) {
            ArrayList<SalePoint> storages = emptySalePoint.getSalePoints();

            storages.add(new SalePoint(emptySalePoint.getSalePoints().size() + 1, address));
            emptySalePoint.updateSalePointFile(storages);
        } else {
            System.out.print("На указанном адресе уже открыт точку продаж.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void closeStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКРЫТИЯ СКЛАДА ======\n\n");
        System.out.print("Введите адрес места, где хотите закрыть склад: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage != null && !thisStorage.isClosed()) {
            thisStorage.setClosed(true);

            ArrayList<Employee> employees = Employee.getEmployees();
            for (Employee employee : employees) {
                if (employee.getPointId() == thisStorage.getId()) {
                    employee.fire();
                }
            }

            System.out.print("Склад закрыт, а все его сотрудники уволены.");
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void closeSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКРЫТИЯ СКЛАДА ======\n\n");
        System.out.print("Введите адрес места, где хотите закрыть склад: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisSalePoint = emptySalePoint.findSalePointByAddress(address);
        if (thisSalePoint != null && !thisSalePoint.isClosed()) {
            thisSalePoint.setClosed(true);

            ArrayList<Employee> employees = Employee.getEmployees();
            for (Employee employee : employees) {
                if (employee.getPointId() == thisSalePoint.getId()) {
                    employee.fire();
                }
            }

            System.out.print("Пункт продаж закрыт, а все его сотрудники уволены.");
        } else {
            System.out.print("Пункт продаж по указанному адресу не существует.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void printStorages() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где хотите закрыть склад: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage != null && !thisStorage.isClosed()) {

            System.out.println("Id: " + thisStorage.getId() + " Address: " + thisStorage.getAddress());
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void printSalePoints() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где хотите закрыть пункт выдачи: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisSalePoint = emptySalePoint.findSalePointByAddress(address);
        if (thisSalePoint != null && !thisSalePoint.isClosed()) {
            System.out.println("Id: " + thisSalePoint.getId() + " Address: " + thisSalePoint.getAddress());
        } else {
            System.out.print("Пункт выдачи по указанному адресу не существует.");
        }

        MainMenu.moveToMainMenu();
    }

    public static void printCellsOnStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где пункт выдачи: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage != null && !thisStorage.isClosed()) {
            Cell emptyCell = new Cell();
            ArrayList<Cell> cells = emptyCell.getCells();

            MainMenu.moveToMainMenu();
            for (Cell cell : cells) {
                if (thisStorage.getId() == cell.getPointId() && cell.getPointType().equals(cell.getStorageType())) {
                    System.out.println("Id: " + cell.getId() + " PointId: " + cell.getPointId() + " Capacity: " + cell.getCapacity() + "ProductQuantity: " + cell.getProductQuantity() + "ProductId: " + cell.getProductId());
                }
            }
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }



        MainMenu.moveToMainMenu();
    }

    public static void printCellsOnSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где пункт выдачи: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisSalePoint = emptySalePoint.findSalePointByAddress(address);
        if (thisSalePoint != null && !thisSalePoint.isClosed()) {
            Cell emptyCell = new Cell();
            ArrayList<Cell> cells = emptyCell.getCells();

            MainMenu.moveToMainMenu();
            for (Cell cell : cells) {
                if (thisSalePoint.getId() == cell.getPointId() && cell.getPointType().equals(cell.getSalePointType())) {
                    System.out.println("Id: " + cell.getId() + " PointId: " + cell.getPointId() + " Capacity: " + cell.getCapacity() + "ProductQuantity: " + cell.getProductQuantity() + "ProductId: " + cell.getProductId());
                }
            }
        } else {
            System.out.print("SalePoint по указанному адресу не существует.");
        }



        MainMenu.moveToMainMenu();
    }
}
