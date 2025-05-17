package users;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Employee extends Person {
    private int pointId;
    private String position;
    private final String firedPositionName = "fired";
    private final String L1PositionName = "L1";
    private final String L2PositionName = "L2 (mol)";
    private final String L3PositionName = "L3 (admin)";

    private static final String EMPLOYEE_XLSX_FILE_PATH = "./files/employees.xlsx";

    public Employee(int id, String firstName, String lastName, String phoneNumber, int passwordHash, int pointId) {
        super(id, firstName, lastName, phoneNumber, passwordHash);
        this.pointId = pointId;
        this.position = L1PositionName;
    }

    public Employee(int id, String firstName, String lastName, String phoneNumber, int passwordHash, int pointId, String position) {
        super(id, firstName, lastName, phoneNumber, passwordHash);
        this.pointId = pointId;
        this.position = position;
    }

    public static Employee findEmployeeByPhone(String phone) throws IOException, InvalidFormatException {
        ArrayList<Employee> employees = getEmployees();

        Employee neededEmployee = null;
        for (Employee employee : employees) {
            if (employee.getPhoneNumber().equals(phone)) {
                neededEmployee = employee;
            }
        }
        return neededEmployee;
    }

    public Employee() {
    }

    public static ArrayList<Employee> getEmployees() throws IOException, InvalidFormatException {
        ArrayList<Employee> employees = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(EMPLOYEE_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotFirstName = dataFormatter.formatCellValue(row.getCell(1));
                String gotLastName = dataFormatter.formatCellValue(row.getCell(2));
                String gotPhoneNumber = dataFormatter.formatCellValue(row.getCell(3));
                String gotPasswordHah= dataFormatter.formatCellValue(row.getCell(4));
                String gotStorageId = dataFormatter.formatCellValue(row.getCell(5));
                String gotPosition = dataFormatter.formatCellValue(row.getCell(6));

                Employee employee = new Employee(Integer.parseInt(gotId), gotFirstName, gotLastName, gotPhoneNumber, Integer.parseInt(gotPasswordHah), Integer.parseInt(gotStorageId), gotPosition);
                employees.add(employee);
            }
        }

        // Закрытие workbook
        workbook.close();

        return employees;
    }

    public void updateEmployeesFile(ArrayList<Employee> employees) throws IOException, InvalidFormatException {
        String[] columns = {"id", "first_name", "last_name", "phone_number", "password_hash", "storageId", "position"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("employee");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(Employee employee: employees) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(employee.getId());
            row.createCell(1)
                    .setCellValue(employee.getFirstName());
            row.createCell(2)
                    .setCellValue(employee.getLastName());
            row.createCell(3)
                    .setCellValue(employee.getPhoneNumber());
            row.createCell(4)
                    .setCellValue(employee.getPasswordHash());
            row.createCell(5)
                    .setCellValue(employee.getPointId());
            row.createCell(6)
                    .setCellValue(employee.getPosition());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(EMPLOYEE_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public void fire() {
        position = firedPositionName;
    }

    public void hire() {
        position = L1PositionName;
    }

    public String promote() {
        if (position.equals(L1PositionName)) {
            position = L2PositionName;
            return "Сотрудник с id " + pointId + " повышен до уровня " + L2PositionName;
        } else if (position.equals(L2PositionName)) {
            position = L3PositionName;
            return "Сотрудник с id " + pointId + " повышен до уровня " + L3PositionName;
        } else if (position.equals(L3PositionName)) {
            return "Сотрудник с id " + pointId + " имеет максимальный уровень, поэтому не может быть повышен";
        } else {
            return "Сотрудник с id " + pointId + " уволен, поэтому не может быть повышен";
        }
    }

    public int getPointId() {
        return pointId;
    }

    public String getPosition() {
        return position;
    }

    public String getFiredPositionName() {
        return firedPositionName;
    }

    public String getL3PositionName() {
        return L3PositionName;
    }
}
