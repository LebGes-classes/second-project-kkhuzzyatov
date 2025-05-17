package cmd;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import point.Storage;
import users.Employee;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        MainMenu.openMainMenu();
    }
}
