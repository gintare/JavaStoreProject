package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product{
    int id;
    String name;
    Double price;
    int quantity;

    @Override
    public String toString() {
        return "id =" + id +
                ", pavadinimas ='" + name + '\'' +
                ", kaina ='" + price + '\'' +
                ", kiekis =" + quantity;
    }
}

public class Main {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        List<Product> productsCart = new ArrayList<>();

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("produktai.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            String[] productsProps = line.split(";");
            Product product = new Product();
            product.id = Integer.parseInt(productsProps[0].trim());
            product.name = productsProps[1];
            product.price = Double.parseDouble(productsProps[2]);
            products.add(product);
            // product.quantity++; // neteisingai
        }


        System.out.println("Visi produktai :");
        products.forEach(System.out::println); //

        Scanner sc = new Scanner(System.in);
        System.out.println("Iveskite producto id ");
        while (true) {
            System.out.println("Iveskite produkto id, kuri norite prideti i krepseli. Iseiti 0.");
            int productId = sc.nextInt();
            if (productId == 0) {
                break;
            }
            Product product = products.stream().filter(i -> i.id == productId).findFirst().orElse(new Product());
            productsCart.add(product);
            product.quantity++;


            System.out.println("Jusu krepselyje yra tokie produktai:");
            productsCart.forEach(System.out::println);

        }

        System.out.println(" Prekes trinimas spaukite 00, jei norite pabaigti spauskite 0");
        Scanner sc1= new Scanner(System.in);

        String delete = sc1.nextLine();

        if (delete.equals("00")){
            System.out.println("Iveskite prekes Id, kuria norite istrinti: ");
            int removeId = sc1.nextInt();
            System.out.println("iveskite kieki: ");
            int removeQuantity = sc1.nextInt();

            removeFromCart(removeId, removeQuantity, productsCart);

            System.out.println("Jusu krepselyje yra tokie produktai:");
            productsCart.forEach(System.out::println);
        }




        printTotalQuantity(productsCart);
        printTotalCost(productsCart);
    }


    public static void printTotalQuantity(List<Product> productsCart) {
    }


    public static void printTotalCost(List<Product> productsCart) {
        double totalCost = 0;
        for (Product product : productsCart) {
            totalCost += product.price * product.quantity;
        }
        System.out.println("Produktu krepselis: " + productsCart);
        System.out.println("produktu krepselyje suma yra : " + totalCost);
    }


    public static void removeFromCart(int id, int quantity, List<Product> productsCart) {
        for (int i = 0; i < productsCart.size(); i++) {
            Product product = productsCart.get(i);
            if (product.id == id) {
                product.quantity -= quantity;
                if (product.quantity <= 0) {
                    productsCart.remove(product);
                    System.out.println("Produktas istrintas is krepselio: " + product.name);
                } else {
                    System.out.println("Produkt krepselyje: " + product.name + " Quantity: " + product.quantity);
                }
                if (product.quantity - quantity < 0) {
                    product.quantity = 0;
                } else {
                    product.quantity -= quantity;
                }

            }
            if (quantity == 0) {
                break;
            }

        }
    }
}