package users;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import point.SalePoint;
import purchase.Purchase;
import utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Client extends Person {

    private final String CLIENT_XLSX_FILE_PATH = "./files/client.xlsx";

    public Client(int id, String firstName, String lastName, String phoneNumber, int passwordHash) {
        super(id, firstName, lastName, phoneNumber, passwordHash);
    }

    public Client() {}

    public Client findClientByPhone(String phone) throws IOException, InvalidFormatException {
        ArrayList<Client> clients = getClients();

        Client neededClient = null;
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(phone)) {
                neededClient = client;
            }
        }
        return neededClient;
    }

    public ArrayList<Client> getClients() throws IOException, InvalidFormatException {
        ArrayList<Client> clients = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(CLIENT_XLSX_FILE_PATH));

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
                String gotPasswordHah = dataFormatter.formatCellValue(row.getCell(4));

                Client client = new Client(Integer.parseInt(gotId), gotFirstName, gotLastName, gotPhoneNumber,Integer.parseInt(gotPasswordHah));
                clients.add(client);
            }
        }

        // Закрытие workbook
        workbook.close();

        return clients;
    }

    public void updateClientsFile(ArrayList<Client> clients) throws IOException, InvalidFormatException {
        String[] columns = {"id", "first_name", "last_name", "phone_number", "password_hash"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("client");

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

        // Create Other rows and cells with clients data
        int rowNum = 1;
        for(Client client: clients) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(client.getId());
            row.createCell(1)
                    .setCellValue(client.getFirstName());
            row.createCell(2)
                    .setCellValue(client.getLastName());
            row.createCell(3)
                    .setCellValue(client.getPhoneNumber());
            row.createCell(4)
                    .setCellValue(client.getPasswordHash());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(CLIENT_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }
}
