package Menu.Employee;

import Menu.MainMenu;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import product.Product;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AddingNewProductMenu {
    public static void addProduct() throws IOException, InvalidFormatException {
        Utils.clearConsole();
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== МИНЮ ДОБАВЛЕНИЯ НОВОГО ТОВАРА ======\n\n");
        System.out.print("Введите наименование товара: ");
        String nameOfProduct = scanner.nextLine();

        Product emptyProduct = new Product();
        Product thisProduct = emptyProduct.findProductByName(nameOfProduct);
        if (thisProduct != null) {
            System.out.print("Product с таким именем уже существует.");
        } else {
            System.out.print("Введите закупочную цену товара: ");
            int purchasingPrice = scanner.nextInt();
            System.out.print("Введите цену продажи товара: ");
            int priceForClient = scanner.nextInt();

            ArrayList<Product> products = emptyProduct.getProducts();
            products.add(new Product(products.size() + 1, nameOfProduct, purchasingPrice, priceForClient));
            emptyProduct.updateProductFile(products);
        }

        EmployeeMenu.moveToEmployeeMenu();
    }

    public static void printProducts() throws IOException, InvalidFormatException {
        Utils.clearConsole();

        Product emptyProduct = new Product();
        ArrayList<Product> products = emptyProduct.getProducts();

        for (Product product : products) {
            System.out.println(product.getId() + " " + product.getName() + " " + product.getPriceOfPurchasing() + " " + product.getPriceForClient());
        }

        EmployeeMenu.moveToEmployeeMenu();
    }
}
