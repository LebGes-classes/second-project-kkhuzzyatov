package entities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Task {
    private int id;
    private String name;
    private int remainTime;
    private int[] dependency;
    private String status;
    private int responsibleId;
    private final String doneStatus = "done";
    private final String inWorkStatus = "in_work";

    private static final String TASK_XLSX_FILE_PATH = "./files/tasks.xlsx";

    public Task(int id, String name, int remainTime, int[] dependency, String status, int responsibleId) {
        this.id = id;
        this.name = name;
        this.remainTime = remainTime;
        this.dependency = dependency;
        this.status = status;
        this.responsibleId = responsibleId;
    }

    public static int getTaskOfEmployee(int id) throws IOException, InvalidFormatException {
        ArrayList<Task> tasks = Task.getTasks();
        for (Task task : tasks) {
            if (task.getResponsibleId() == id && !task.getStatus().equals(task.getDoneStatus())) {
                return id;
            }
        }
        return -1;
    }

    public static ArrayList<Task> getFreeTasks() throws IOException, InvalidFormatException {
        ArrayList<Task> tasks = Task.getTasks();
        ArrayList<Task> freeTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getResponsibleId() == -1) {
                freeTasks.add(task);
            }
        }
        return freeTasks;
    }

    public static ArrayList<Task> getTasks() throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(new File(TASK_XLSX_FILE_PATH));
        ArrayList<Task> tasks = new ArrayList<>();


        // Получение листа по индексу (первый лист)
        Sheet sheet = workbook.getSheetAt(0);

        // Создание DataFormatter для форматирования и получения значения ячейки как String
        DataFormatter dataFormatter = new DataFormatter();

        // цикл for-each для итерации по строкам и столбцам
        for (Row row: sheet) {
            if (row.getRowNum() != 0) {
                String gotId = dataFormatter.formatCellValue(row.getCell(0));
                String gotName = dataFormatter.formatCellValue(row.getCell(1));
                String gotRemainTime = dataFormatter.formatCellValue(row.getCell(2));
                String gotDependency = dataFormatter.formatCellValue(row.getCell(3));
                String gotStatus = dataFormatter.formatCellValue(row.getCell(4));
                String gotResponsibleId = dataFormatter.formatCellValue(row.getCell(5));

                String[] splited = gotDependency.split(" ");
                int[] dependency = new int[splited.length];
                for (int i = 0; i < splited.length; i++) {
                    dependency[i] = Integer.parseInt(splited[i]);
                }

                Task task = new Task(Integer.parseInt(gotId), gotName, Integer.parseInt(gotRemainTime), dependency, gotStatus, Integer.parseInt(gotResponsibleId));
                tasks.add(task);
            }
        }

        // Закрытие workbook
        workbook.close();

        return tasks;
    }

    public static void updateTasksFile() throws IOException, InvalidFormatException {
        ArrayList<Task> tasks = getTasks();

        String[] columns = {"идентификатор", "наименование", "часы", "зависимость", "статус", "ответственный"};

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet("tasks");

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
        for(Task task: tasks) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(task.getId());
            row.createCell(1)
                    .setCellValue(task.getName());
            row.createCell(2)
                    .setCellValue(task.getRemainTime());
            row.createCell(3)
                    .setCellValue(task.getDependency());
            row.createCell(4)
                    .setCellValue(task.getStatus());
            row.createCell(5)
                    .setCellValue(task.getResponsibleId());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(TASK_XLSX_FILE_PATH);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public int[] getDependencyArray() {
        return dependency;
    }

    public String getDependency() {
        String strDependency = "";
        strDependency += dependency[0];
        for (int i = 1; i < dependency.length; i++) {
            strDependency += (" " + dependency[i]);
        }
        return strDependency;
    }

    public String getStatus() {
        return status;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public void setStatusDone() {
        this.status = doneStatus;
    }

    public void setStatusInWork() {
        this.status = inWorkStatus;
    }

    public String getDoneStatus() {
        return doneStatus;
    }

    public String getInWorkStatus() {
        return inWorkStatus;
    }
}