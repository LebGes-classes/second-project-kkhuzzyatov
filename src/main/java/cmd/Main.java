package cmd;

import Menu.MainMenu;
import entities.Employee;
import entities.Task;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        ArrayList<Employee> employees = Employee.getEmployees();
        for (Employee employee : employees) {
            employee.run(); //!
        }

        boolean isAllEmployeesFinishDay = false;
        while (!isAllEmployeesFinishDay) {
            isAllEmployeesFinishDay = true;
            for (Employee employee : employees) {
                if (employee.getDayTime() != 8) {
                    isAllEmployeesFinishDay = false;
                }
            }
        }
        Employee.updateEmployeesFile();
        Task.updateTasksFile();
    }
}
