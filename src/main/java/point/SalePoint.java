package point;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import purchase.Purchase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SalePoint extends Point {
    public SalePoint() {}

    private final String SALE_POINT_XLSX_FILE_PATH = "./files/sale_point.xlsx";

    public SalePoint(int id, String address) {
        super(id, address, false);
    }

    public SalePoint findSalePointByAddress(String address) throws IOException, InvalidFormatException {
        ArrayList<SalePoint> salePoints = getSalePoints();

        SalePoint neededSalePoint = null;
        for (SalePoint salePoint : salePoints) {
            if (salePoint.getAddress().equals(address)) {
                neededSalePoint = salePoint;
            }
        }
        return neededSalePoint;
    }

    public ArrayList<SalePoint> getSalePoints() throws IOException, InvalidFormatException {
        ArrayList<SalePoint> salePoints = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(SALE_POINT_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotAddress = dataFormatter.formatCellValue(row.getCell(1));

                SalePoint salePoint = new SalePoint(Integer.parseInt(gotId), gotAddress);
                salePoints.add(salePoint);
            }
        }

        // Закрытие workbook
        workbook.close();

        return salePoints;
    }

    public void updateSalePointFile(ArrayList<SalePoint> salePoints) throws IOException, InvalidFormatException {
        String[] columns = {"id", "address", "mol_id"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("sale_point");

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
        for(SalePoint salePoint: salePoints) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(salePoint.getId());

            row.createCell(1)
                    .setCellValue(salePoint.getAddress());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(SALE_POINT_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public void saleProduct(int cellId, int userId, int number) throws IOException, InvalidFormatException {
        ArrayList<Cell> cells = super.getCells(super.getId());
        Cell neededCell = null;
        for (Cell cell : cells) {
            if (cell.getId() == cellId) {
                neededCell = cell;
            }
        }
        if (neededCell == null) {
            System.out.println("Cell isn't found in sell point.");
            return;
        }
        if (neededCell.getProductQuantity() - number > 0) {
            Purchase purchaseObj = new Purchase();
            ArrayList<Purchase> purchases = purchaseObj.getPurchases();
            purchases.add(new Purchase(purchases.size() + 1, "sold", userId, getId(), number, neededCell.getProductId()));
            purchaseObj.updatePurchaseFile(purchases);

            neededCell.manageProductQuantity(-number);
        } else {
            System.out.println("There aren't enough products in cell.");
        }
    }

    public void refundProduct(int purchaseId) throws IOException, InvalidFormatException {
        Purchase purchaseObj = new Purchase();
        ArrayList<Purchase> purchases = purchaseObj.getPurchases();
        Purchase neededPurchase = null;
        for (Purchase purchase : purchases) {
            if (purchase.getId() == purchaseId) {
                neededPurchase = purchase;
            }
        }
        if (neededPurchase == null) {
            System.out.println("Purchase isn't found.");
        } else if (neededPurchase.getStatus().equals("refund")) {
            System.out.println("Purchase is refunded already.");
        } else {
            neededPurchase.setStatus("refund");
            purchaseObj.updatePurchaseFile(purchases);

            Cell emptyCell = new Cell();
            Cell newCell = new Cell();
            Cell cell = new Cell(emptyCell.getCells().size() + 1, neededPurchase.getNumberOfGoods(), getId(), neededPurchase.getProductId(), neededPurchase.getNumberOfGoods(), emptyCell.getStorageType());
            ArrayList<Cell> cells = emptyCell.getCells();
            cells.add(newCell);
            emptyCell.updateCellFile(cells);
        }
    }
}
