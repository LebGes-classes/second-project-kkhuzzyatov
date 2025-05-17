package purchase;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Purchase {
    private int id;
    private String status;
    private int userId;
    private int pointId;
    private int numberOfGoods;
    private int productId;

    private final String PURCHASE_XLSX_FILE_PATH = "./files/purchase.xlsx";

    public Purchase(int id, String status, int userId, int pointId, int numberOfGoods, int productId) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.pointId = pointId;
        this.numberOfGoods = numberOfGoods;
        this.productId = productId;
    }

    public ArrayList<Purchase> getPurchases() throws IOException, InvalidFormatException {
        ArrayList<Purchase> purchases = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(PURCHASE_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotStatus = dataFormatter.formatCellValue(row.getCell(1));
                String gotUserId = dataFormatter.formatCellValue(row.getCell(2));
                String gotPointId = dataFormatter.formatCellValue(row.getCell(3));
                String gotNumberOfGoods = dataFormatter.formatCellValue(row.getCell(4));
                String gotProductId = dataFormatter.formatCellValue(row.getCell(5));

                Purchase purchase = new Purchase(Integer.parseInt(gotId), gotStatus, Integer.parseInt(gotUserId), Integer.parseInt(gotPointId), Integer.parseInt(gotNumberOfGoods), Integer.parseInt(gotProductId));
                purchases.add(purchase);
            }
        }

        // Закрытие workbook
        workbook.close();

        return purchases;
    }

    public void updatePurchaseFile(ArrayList<Purchase> purchases) throws IOException, InvalidFormatException {
        String[] columns = {"id", "status", "user_id", "number_of_goods", "product_id"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("purchase");

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
        for(Purchase purchase: purchases) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(purchase.getId());

            row.createCell(1)
                    .setCellValue(purchase.getStatus());

            row.createCell(2)
                    .setCellValue(purchase.getUserId());

            row.createCell(3)
                    .setCellValue(purchase.getNumberOfGoods());

            row.createCell(4)
                    .setCellValue(purchase.getProductId());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(PURCHASE_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public Purchase() {}

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfGoods() {
        return numberOfGoods;
    }

    public int getProductId() {
        return productId;
    }

    public int getPointId() {
        return pointId;
    }
}
