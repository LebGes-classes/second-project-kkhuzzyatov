package product;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Product {
    private int id;
    private String Name;
    private float priceOfPurchasing;
    private float priceForClient;

    private final String PRODUCT_XLSX_FILE_PATH = "./files/product.xlsx";

    public Product() {
    }

    public Product findProductByName(String name) throws IOException, InvalidFormatException {
        ArrayList<Product> products = getProducts();

        Product neededProduct = null;
        for (Product product : products) {
            if (product.getName().equals(name)) {
                neededProduct = product;
            }
        }
        return neededProduct;
    }

    public ArrayList<Product> getProducts() throws IOException, InvalidFormatException {
        ArrayList<Product> products = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(new File(PRODUCT_XLSX_FILE_PATH));

        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotProductName = dataFormatter.formatCellValue(row.getCell(1));
                String gotPurchasingPrice = dataFormatter.formatCellValue(row.getCell(2));
                String gotPriceForProduct = dataFormatter.formatCellValue(row.getCell(3));

                Product product = new Product(Integer.parseInt(gotId), gotProductName, Integer.parseInt(gotPurchasingPrice), Integer.parseInt(gotPriceForProduct));
                products.add(product);
            }
        }

        // Закрытие workbook
        workbook.close();

        return products;
    }

    public void updateProductFile(ArrayList<Product> products) throws IOException, InvalidFormatException {
        String[] columns = {"id", "name", "purchasing_price", "price_for_product"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("product");

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
        for(Product product: products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(product.getId());

            row.createCell(1)
                    .setCellValue(product.getName());

            row.createCell(2)
                    .setCellValue(product.getPriceOfPurchasing());

            row.createCell(3)
                    .setCellValue(product.getPriceForClient());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(PRODUCT_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public Product(int id, String name, float priceOfPurchasing, float priceForProduct) {
        this.id = id;
        Name = name;
        this.priceOfPurchasing = priceOfPurchasing;
        this.priceForClient = priceForProduct;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public float getPriceOfPurchasing() {
        return priceOfPurchasing;
    }

    public float getPriceForClient() {
        return priceForClient;
    }
}
