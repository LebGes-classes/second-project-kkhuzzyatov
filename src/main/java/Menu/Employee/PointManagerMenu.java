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
            if (thisStorage.isClosed().equals("false")) {
                System.out.print("На указанном адресе уже открыт склад.");
            } else {
                thisStorage.setClosed("false");
            }
        }

        EmployeeMenu.moveToEmployeeMenu();
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

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void closeStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКРЫТИЯ СКЛАДА ======\n\n");
        System.out.print("Введите адрес места, где хотите закрыть склад: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        ArrayList<Storage> storages = emptyStorage.getStorages();

        Storage thisStorage = null;
        for (Storage storage : storages) {
            if (storage.getAddress().equals(address)) {
                thisStorage = storage;
            }
        }
        if (thisStorage != null && thisStorage.isClosed().equals("false")) {
            thisStorage.setClosed("true");

            ArrayList<Employee> employees = Employee.getEmployees();
            for (Employee employee : employees) {
                if (employee.getPointId() == thisStorage.getId()) {
                    employee.fire();
                }
            }

            if (employees.get(0) != null) {
                employees.get(0).updateEmployeesFile(employees);
            }
            emptyStorage.updateStorageFile(storages);
            System.out.print("Склад закрыт, а все его сотрудники уволены.");
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void closeSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ЗАКРЫТИЯ СКЛАДА ======\n\n");
        System.out.print("Введите адрес места, где хотите закрыть склад: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        ArrayList<SalePoint> salePoints = emptySalePoint.getSalePoints();

        SalePoint thisSalePoint = null;
        for (SalePoint salePoint : salePoints) {
            if (salePoint.getAddress().equals(address)) {
                thisSalePoint = salePoint;
            }
        }
        if (thisSalePoint != null && thisSalePoint.isClosed().equals("false")) {
            thisSalePoint.setClosed("true");

            ArrayList<Employee> employees = Employee.getEmployees();
            for (Employee employee : employees) {
                if (employee.getPointId() == thisSalePoint.getId()) {
                    employee.fire();
                }
            }

            if (employees.get(0) != null) {
                employees.get(0).updateEmployeesFile(employees);
            }
            emptySalePoint.updateSalePointFile(salePoints);
            System.out.print("Пункт продаж закрыт, а все его сотрудники уволены.");
        } else {
            if (thisSalePoint.isClosed().equals("false")) {
                System.out.print("Пункт продаж по указанному адресу не существует.");
            } else {
                thisSalePoint.setClosed("false");
            }
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void printStorages() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где находится склад: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage != null && thisStorage.isClosed().equals("false")) {
            System.out.println("Id: " + thisStorage.getId() + " Address: " + thisStorage.getAddress());
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void printSalePoints() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где хотите закрыть пункт выдачи: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisSalePoint = emptySalePoint.findSalePointByAddress(address);
        if (thisSalePoint != null && thisSalePoint.isClosed().equals("false")) {
            System.out.println("Id: " + thisSalePoint.getId() + " Address: " + thisSalePoint.getAddress());
        } else {
            System.out.print("Пункт выдачи по указанному адресу не существует.");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void printCellsOnStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где пункт выдачи: ");
        String address = scanner.nextLine();
        Storage emptyStorage = new Storage();
        Storage thisStorage = emptyStorage.findStorageByAddress(address);
        if (thisStorage != null && thisStorage.isClosed().equals("false")) {
            Cell emptyCell = new Cell();
            ArrayList<Cell> cells = emptyCell.getCells();

            for (Cell cell : cells) {
                if (thisStorage.getId() == cell.getPointId() && cell.getPointType().equals(cell.getStorageType())) {
                    System.out.println("Id: " + cell.getId() + " PointId: " + cell.getPointId() + " Capacity: " + cell.getCapacity() + " ProductQuantity: " + cell.getProductQuantity() + "ProductId: " + cell.getProductId());
                }
            }
        } else {
            System.out.print("Склад по указанному адресу не существует.");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void printCellsOnSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите адрес места, где пункт выдачи: ");
        String address = scanner.nextLine();
        SalePoint emptySalePoint = new SalePoint();
        SalePoint thisSalePoint = emptySalePoint.findSalePointByAddress(address);
        if (thisSalePoint != null && thisSalePoint.isClosed().equals("false")) {
            Cell emptyCell = new Cell();
            ArrayList<Cell> cells = emptyCell.getCells();

            for (Cell cell : cells) {
                if (thisSalePoint.getId() == cell.getPointId() && cell.getPointType().equals(cell.getSalePointType())) {
                    System.out.println("Id: " + cell.getId() + " PointId: " + cell.getPointId() + " Capacity: " + cell.getCapacity() + " ProductQuantity: " + cell.getProductQuantity() + "ProductId: " + cell.getProductId());
                }
            }
        } else {
            System.out.print("SalePoint по указанному адресу не существует.");
        }



        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void addCell() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ СОЗДАНИЯ ЯЧЕЙКИ ======\n\n");
        System.out.print("Введите \"storage\" или \"sell_point\": ");
        String pointType = scanner.nextLine();

        switch (pointType) {
            case "storage":
                addCellToStorage();
                break;
            case "sell_point":
                addCellToSalePoint();
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private static void addCellToStorage() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите storage address: ");
        String address = scanner.nextLine();

        System.out.print("Введите productId: ");
        int productId = scanner.nextInt();

        Storage emptyStorage = new Storage();
        if (emptyStorage.findStorageByAddress(address) == null) {
            System.out.print("Не существует склада с указанным address.");
        } else {
            Storage thisStorage = emptyStorage.findStorageByAddress(address);
            Cell emptyCell = new Cell();
            Cell cell = new Cell(emptyCell.getCells().size() + 1, 200, thisStorage.getId(), productId, 0, emptyCell.getStorageType());
            ArrayList<Cell> cells = emptyCell.getCells();
            cells.add(cell);
            emptyCell.updateCellFile(cells);
            System.out.print("Ячейка создана");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    private static void addCellToSalePoint() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите SalePoint address: ");
        String address = scanner.nextLine();

        System.out.print("Введите productId: ");
        int productId = scanner.nextInt();

        SalePoint emptySalePoint = new SalePoint();
        if (emptySalePoint.findSalePointByAddress(address) == null) {
            System.out.print("Не существует очки продаж с указанным address.");
        } else {
            Cell emptyCell = new Cell();
            Cell cell = new Cell(emptyCell.getCells().size(), 50, emptySalePoint.findSalePointByAddress(address).getId(), productId, 0, emptyCell.getSalePointType());
            ArrayList<Cell> cells = emptyCell.getCells();
            cells.add(cell);
            emptyCell.updateCellFile(cells);
            System.out.print("Ячейка создана");
        }

        EmployeeMenu.moveToEmployeeMenu();
    }
}
