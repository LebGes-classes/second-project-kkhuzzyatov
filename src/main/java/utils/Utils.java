package utils;

public class Utils {
    public static void clearConsole() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
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
