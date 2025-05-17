package utils;

public class Utils {
    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public static int passwordCoder(String password) {
        int hash = 0;
        for (char letter : password.toCharArray()) {
            hash += (int) letter * 561;
        }
        return hash;
    }
}
