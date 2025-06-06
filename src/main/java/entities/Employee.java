package entities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Employee/* extends Thread*/ {
    private int id;
    private String name;
    private int timeOfTasksCompleting;
    private int downtime;
    private int timeInWork;
    private int dayTime = 0;

    private static final String EMPLOYEE_XLSX_FILE_PATH = "./files/employees_data.xlsx";

    public void run() throws IOException, InvalidFormatException {
        if (dayTime < 8) {
            //try {
                int curTaskId = Task.getTaskOfEmployee(id);
                if (curTaskId == -1) {
                    ArrayList<Task> freeTasks = Task.getFreeTasks();
                    if (freeTasks.size() != 0) {
                        Task taskOfThis = freeTasks.get((new Random()).nextInt(freeTasks.size()));
                        taskOfThis.setResponsibleId(id);
                        taskOfThis.setStatusInWork();
                        curTaskId = taskOfThis.getId();
                    }
                }

                if (curTaskId != -1)
                {
                    Task taskOfThis = Task.getTasks().get(curTaskId);

                    int[] dependency = taskOfThis.getDependencyArray();
                    boolean isAllDependencyDone = true;
                    for (int dependencyId : dependency) {
                        if (!Task.getTasks().get(dependencyId).getStatus().equals(taskOfThis.getDoneStatus())) {
                            isAllDependencyDone = false;
                        }
                    }

                    if (isAllDependencyDone) {
                        taskOfThis.setRemainTime(taskOfThis.getRemainTime() - 1);
                        timeOfTasksCompleting++;
                        if (taskOfThis.getRemainTime() == 0) {
                            taskOfThis.setStatusDone();
                        }
                    } else {
                        downtime--;
                    }
                } else {
                    downtime--;
                }
                timeInWork++;
            /*} catch (IOException | InvalidFormatException e) {
                throw new RuntimeException(e);
            }*/

            dayTime++;
        }
    }

    public Employee(int id, String name, int timeOfTasksCompleting, int downtime, int timeInWork) {
        this.id = id;
        this.name = name;
        this.timeOfTasksCompleting = timeOfTasksCompleting;
        this.downtime = downtime;
        this.timeInWork = timeInWork;
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
                String gotName = dataFormatter.formatCellValue(row.getCell(1));
                String gotTimeOfTasksCompleting = dataFormatter.formatCellValue(row.getCell(2));
                String gotDowntime = dataFormatter.formatCellValue(row.getCell(3));
                String gotTimeInWork = dataFormatter.formatCellValue(row.getCell(4));

                Employee employee = new Employee(Integer.parseInt(gotId), gotName, Integer.parseInt(gotTimeOfTasksCompleting), Integer.parseInt(gotDowntime), Integer.parseInt(gotTimeInWork));
                employees.add(employee);
            }
        }

        // Закрытие workbook
        workbook.close();

        return employees;
    }

    public static void updateEmployeesFile() throws IOException, InvalidFormatException {
        ArrayList<Employee> employees = getEmployees();

        String[] columns = {"идентификатор", "имя сотрудника", "время, затрачиваемое сотрудниками на исполнение своих задач", "время простоя", "время проведённое на работе"};

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

            row.createCell(1)
                    .setCellValue(employee.getEmployeeId());
            row.createCell(1)
                    .setCellValue(employee.getEmployeeName());
            row.createCell(2)
                    .setCellValue(employee.getTimeOfTasksCompleting());
            row.createCell(3)
                    .setCellValue(employee.getDowntime());
            row.createCell(4)
                    .setCellValue(employee.getTimeInWork());
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

    public int getEmployeeId() {
        return id;
    }

    public String getEmployeeName() {
        return name;
    }

    public int getTimeOfTasksCompleting() {
        return timeOfTasksCompleting;
    }

    public int getDowntime() {
        return downtime;
    }

    public int getTimeInWork() {
        return timeInWork;
    }

    public int getDayTime() {
        return dayTime;
    }

    public void setDayTime(int dayTime) {
        this.dayTime = dayTime;
    }
}
