package point;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Cell {
    private int id;
    private int capacity;
    private int pointId;
    private int productId;
    private int productQuantity;
    private String pointType;
    private final String storageType = "storage";
    private final String salePointType = "salePoint";

    private final String CELL_XLSX_FILE_PATH = "./files/cell.xlsx";

    public Cell() {}

    public Cell(int id, int capacity, int pointId, int productId, int productQuantity, String pointType) {
        this.id = id;
        this.capacity = capacity;
        this.pointId = pointId;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.pointType = pointType;
    }

    public ArrayList<Cell> getCells() throws IOException, InvalidFormatException {
        ArrayList<Cell> cells = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(CELL_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotCapacity = dataFormatter.formatCellValue(row.getCell(1));
                String gotPointId = dataFormatter.formatCellValue(row.getCell(2));
                String gotProductId = dataFormatter.formatCellValue(row.getCell(3));
                String gotProductQuantity = dataFormatter.formatCellValue(row.getCell(4));
                String gotPointType = dataFormatter.formatCellValue(row.getCell(5));

                Cell cell = new Cell(Integer.parseInt(gotId), Integer.parseInt(gotCapacity), Integer.parseInt(gotPointId), Integer.parseInt(gotProductId), Integer.parseInt(gotProductQuantity), gotPointType);
                cells.add(cell);
            }
        }

        // Закрытие workbook
        workbook.close();

        return cells;
    }

    public void updateCellFile(ArrayList<Cell> cells) throws IOException, InvalidFormatException {
        String[] columns = {"id", "capacity", "point_id", "product_id", "product_quantity"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("cell");

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
        for(Cell cell: cells) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(cell.getId());

            row.createCell(1)
                    .setCellValue(cell.getCapacity());

            row.createCell(2)
                    .setCellValue(cell.getPointId());

            row.createCell(3)
                    .setCellValue(cell.getProductId());

            row.createCell(4)
                    .setCellValue(cell.getProductQuantity());

            row.createCell(5)
                    .setCellValue(cell.getPointType());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(CELL_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }


    public void manageProductQuantity(int productQuantity)  throws IOException, InvalidFormatException {
        this.productQuantity += productQuantity;
        if (this.productQuantity == 0)
        {
            this.productId = 0;
        }
        updateCellFile(getCells());
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPointId() {
        return pointId;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }


    public String getPointType() {
        return pointType;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getSalePointType() {
        return salePointType;
    }
}
