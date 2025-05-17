package point;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Storage extends Point {
    public Storage() {}

    public Storage(int id, String address, int molId) {
        super(id, address, false);
    }

    private final String STORAGE_XLSX_FILE_PATH = "./files/storage.xlsx";

    public Storage(int id, String address) {
        super(id, address, false);
    }

    public Storage findStorageByAddress(String address) throws IOException, InvalidFormatException {
        ArrayList<Storage> storages = getStorages();

        Storage neededStorage = null;
        for (Storage storage : storages) {
            if (storage.getAddress().equals(address)) {
                neededStorage = storage;
            }
        }
        return neededStorage;
    }

    public ArrayList<Storage> getStorages() throws IOException, InvalidFormatException {
        ArrayList<Storage> storages = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(STORAGE_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotAddress = dataFormatter.formatCellValue(row.getCell(1));
                String gotMolId = dataFormatter.formatCellValue(row.getCell(2));

                Storage storage = new Storage(Integer.parseInt(gotId), gotAddress, Integer.parseInt(gotMolId));
                storages.add(storage);
            }
        }

        // Закрытие workbook
        workbook.close();

        return storages;
    }

    public void updateStorageFile(ArrayList<Storage> storages) throws IOException, InvalidFormatException {
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

        // Create Other rows and cells with storages data
        int rowNum = 1;
        for(Storage storage: storages) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(storage.getId());

            row.createCell(1)
                    .setCellValue(storage.getAddress());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(STORAGE_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public void purchaseGoods(int cellId, int number) throws IOException, InvalidFormatException {
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
        if (neededCell.getProductQuantity() + number < neededCell.getCapacity()) {
            neededCell.manageProductQuantity(number);
        } else {
            System.out.println("There aren't enough space in cell.");
        }
    }
}
