package point;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Point {
    private int id;
    private String address;
    private String isClosed;

    private final String CELL_XLSX_FILE_PATH = "./files/cell.xlsx";

    public Point() {
    }

    public Point(int id, String address, String isClosed) {
        this.id = id;
        this.address = address;
        this.isClosed = isClosed;
    }

    public ArrayList<Cell> getCells(int pointId) throws IOException, InvalidFormatException {
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

                if (pointId == Integer.parseInt(gotPointId)) {
                    Cell cell = new Cell(Integer.parseInt(gotId), Integer.parseInt(gotCapacity), Integer.parseInt(gotPointId), Integer.parseInt(gotProductId), Integer.parseInt(gotProductQuantity), gotPointType);
                    cells.add(cell);
                }
            }
        }

        // Закрытие workbook
        workbook.close();

        return cells;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String isClosed() {
        return isClosed;
    }

    public void setClosed(String closed) {
        isClosed = closed;
    }
}
