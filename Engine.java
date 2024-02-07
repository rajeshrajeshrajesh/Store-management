import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import Inventory.*;
import Product.*;
import emp.*;
import Customer.*;
import Finance.Finance;

public class Engine {
  public static void main(String[] args) {
    ArrayList<Customer> customers = readCustomersFromFile("customer_data.txt");

    Inventory inventory = new Inventory("products.csv", "restock.csv");
    Finance finance = new Finance("balance.txt");
    ArrayList<Product> restock = inventory.restock();
    inventory.makeArray("products.csv");
    finance.purchaseStock(restock);
    Scanner parser = new Scanner(System.in);

    System.out.println("\t\t\tStore Management System\n\n");
    boolean end = true;
    while (end) {
      System.out.println("\nWhat are all the options you need to perform?");
      System.out.println("1. Display Inventory");
      System.out.println("2. Manage Employee");
      System.out.println("3. Display Employee");
      System.out.println("4. Add Inventory to the store ");
      System.out.println("5. Customer to purchase product");
      System.out.println("6. Display people who have purchased more in the shop");
      System.out.println("7. Exit and view profit or loss");

      System.out.println("Enter your choice: ");
      int choice = parser.nextInt();
      parser.nextLine(); // Consume the newline character

      if (choice == 1) {
        inventory.displayInventory();
      } else if (choice == 2) {
        System.out.println("\t\t\tEmployee Management System\n\n");
        Employee.employeemanage();
        System.out.println("Employee management system terminated.\n");
      } else if (choice == 3) {
        System.out.println(Employee.readObjects());
        System.out.println();
      } else if (choice == 4) {
        ADD_PRODUCT();
        inventory.makeArray("products.csv");
      } else if (choice == 5) {
        System.out.println("Press 1 for customers who have already purchased in the shop");
        System.out.println("Press 2 for customers who have not purchased once in the shop");
        int choice2 = parser.nextInt();
        parser.nextLine(); // Consume the newline character

        if (choice2 == 1) {
          String phoneno;
          System.out.println("Enter the phone number of the customer: ");
          phoneno = parser.nextLine().toLowerCase(); // Convert to lowercase
          for (Customer customer : customers) {

            if (customer.getPhoneNumber().toLowerCase().equals(phoneno)) {
              System.out.println("You are a regular customer,");
              System.out.println("Enter items: ");
              String item = "";
              boolean done = false;
              ArrayList<Product> cart = new ArrayList<Product>();
              while (!done) {
                item = parser.nextLine();
                if (item.strip().equalsIgnoreCase("buy")) {
                  done = true;
                } else if (item.matches(".* [0-9]*")) {
                  String[] tokens = item.strip().toLowerCase().split(" ");
                  Product product = inventory.getProduct(tokens[0]);
                  if (product != null) {
                    Product temp = new Product(product.getName(), product.getId(),
                        Integer.parseInt(tokens[1].strip()), product.getPrice(), 0, 0);
                    cart.add(temp);
                  }
                }
              }
              System.out.printf("\n\n\nBill of %d items\n", cart.size());
              System.out.println("---------------------------------------------");
              System.out.printf("%-25s|%-5s|%-12s|\n", "Item name", "QTY", "Price");
              System.out.println("---------------------------------------------");
              float total = 0;
              for (int i = 0; i < cart.size(); i++) {
                int purchase = purchaseProduct(inventory, finance, cart.get(i),
                    cart.get(i).getNumber());
                double price = purchase * cart.get(i).getPrice();
                total += price;
                System.out.printf("%-25s|%5d|%12.2f|\n", cart.get(i).getName(), purchase, price);
              }
              System.out.println("---------------------------------------------");
              System.out.printf("%31s| %11.2f|\n", "Total", total);
              System.out.println("---------------------------------------------");
              System.out.println("Thank you for shopping with us.");
              System.out.println("Please visit us again.");
              customer.setAmount((double) total);
            }
          }
        } else if (choice2 == 2) {
          String Name;
          String Phone_Number;
          String Address;
          double amount;

          System.out.println("Enter your personal information");
          System.out.println("Enter your name");
          Name = parser.nextLine();
          System.out.println("Enter your phone number");
          Phone_Number = parser.nextLine();
          System.out.println("Enter your address");
          Address = parser.nextLine();
          System.out.println("Enter items: ");
          String item = "";
          boolean done = false;
          ArrayList<Product> cart = new ArrayList<Product>();
          while (!done) {
            item = parser.nextLine();
            if (item.strip().equalsIgnoreCase("buy")) {
              done = true;
            } else if (item.matches(".* [0-9]*")) {
              String[] tokens = item.strip().toLowerCase().split(" ");
              Product product = inventory.getProduct(tokens[0]);
              if (product != null) {
                Product temp = new Product(product.getName(), product.getId(),
                    Integer.parseInt(tokens[1].strip()), product.getPrice(), 0, 0);
                cart.add(temp);
              }
            }
          }
          System.out.printf("\n\n\nBill of %d items\n", cart.size());
          System.out.println("---------------------------------------------");
          System.out.printf("%-25s|%-5s|%-12s|\n", "Item name", "QTY", "Price");
          System.out.println("---------------------------------------------");
          float total = 0;
          for (int i = 0; i < cart.size(); i++) {
            int purchase = purchaseProduct(inventory, finance, cart.get(i), cart.get(i).getNumber());
            double price = purchase * cart.get(i).getPrice();
            total += price;
            System.out.printf("%-25s|%5d|%12.2f|\n", cart.get(i).getName(), purchase, price);
          }
          System.out.println("---------------------------------------------");
          System.out.printf("%31s| %11.2f|\n", "Total", total);
          System.out.println("---------------------------------------------");
          System.out.println("Thank you for shopping with us.");
          System.out.println("Please visit us again.");
          customers.add(new Customer(Phone_Number, Name, Address, total));
        }
      } else if (choice == 6) {
        writeCustomersToFile(customers, "customer_data.txt");
        displayCustomersInOrder(customers);
      } else if (choice == 7) {
        finance.saveBalance();
        inventory.saveArray("products.csv");
        double profit = finance.calculateProfit();
        if (profit > 0) {
          System.out.println("Today's profit was " + profit + "/-");
        } else {
          System.out.println("Today's loss was " + profit * -1 + "/-");
        }
        end = false;
      }


    }
  }

  public static void ADD_PRODUCT() {
    Scanner parser = new Scanner(System.in);

    ArrayList<Product> pro1 = new ArrayList<Product>();

    try {
      File file2 = new File("products.csv");
      Scanner parser2 = new Scanner(file2);

      while (parser2.hasNextLine()) {
        String productLine1 = parser2.nextLine();

        if (!productLine1.matches(".*,[0-9]*,[0-9]*,[0-9]*.[0-9]*,.*")) {
          continue;
        }

        String[] tokens = productLine1.split(",");
        String name = tokens[0].strip();
        int id = Integer.parseInt(tokens[1].strip());
        int number = Integer.parseInt(tokens[2].strip());
        double price = Double.parseDouble(tokens[3].strip());
        int restockAmt = Integer.parseInt(tokens[4].strip());
        double costPrice = Double.parseDouble(tokens[5].strip());

        pro1.add(new Product(name, id, number, price, restockAmt, costPrice));
      }

      parser2.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: File not found!");
    }

    int items1;
    System.out.println("How many items do you want to add?");
    items1 = parser.nextInt();
    parser.nextLine();

    for (int i = 0; i < items1; i++) {
      System.out.println("Enter product name: ");
      String name1 = parser.nextLine();
      System.out.println("Enter the ID: ");
      int id1 = parser.nextInt();
      System.out.println("Enter the sell price: ");
      double price1 = parser.nextDouble();
      System.out.println("Enter the quantity: ");
      int number1 = parser.nextInt();
      System.out.println("Enter the restock quantity: ");
      int restock_number = parser.nextInt();
      System.out.println("Enter the buy price by supplier: ");
      double price2 = parser.nextDouble();

      pro1.add(new Product(name1, id1, number1, price1, restock_number, price2));
      parser.nextLine();
    }

    try {
      FileWriter writer1 = new FileWriter(new File("products.csv"));

      for (int i = 0; i < pro1.size(); i++) {
        String toFile2 = String.format("%s,%d,%d,%.2f,%d,%.2f\n",
            pro1.get(i).getName(), pro1.get(i).getId(), pro1.get(i).getNumber(),
            pro1.get(i).getPrice(), pro1.get(i).getRestockAmt(), pro1.get(i).getCostPrice());

        writer1.write(toFile2);
      }

      writer1.close();
      System.out.println("Inventory added successfully!");
    } catch (IOException e) {
      System.out.println("Error: File operation failed.");
    }
  }

  public static void writeCustomersToFile(ArrayList<Customer> customers, String filename) {
    try {
      FileWriter writer = new FileWriter(new File(filename));

      for (int i = 0; i < customers.size(); i++) {
        String toFile1 = String.format("%s,%s,%s,%.2f\n", customers.get(i).getPhoneNumber(),
            customers.get(i).getName(),
            customers.get(i).getAddress(), customers.get(i).getAmountSpent());
        writer.write(toFile1);
      }
      writer.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("File permission denied");
    }
  }

  public static ArrayList<Customer> readCustomersFromFile(String filename) {
    ArrayList<Customer> cus = new ArrayList<Customer>();
    try {
      File file1 = new File(filename);
      Scanner parser1 = new Scanner(file1);
      while (parser1.hasNextLine()) {
        String productLine = parser1.nextLine();
        if (!productLine.matches(".*,.*,.*,[0-9]*.[0-9]*")) {
          continue;
        }
        String[] tokens = productLine.split(",");
        String phno = tokens[0].strip();
        String names = tokens[1].strip();
        String adress = tokens[2].strip();
        double price = Double.parseDouble(tokens[3].strip());
        cus.add(new Customer(phno, names, adress, price));
      }
      parser1.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    }
    return cus;
  }

  public static void displayCustomersInOrder(ArrayList<Customer> customers) {
    Collections.sort(customers, Comparator.comparingDouble(Customer::getAmountSpent).reversed());

    System.out.println("Customers in order of amount spent:");
    System.out.println("------------------------------------------------------------------------------------");
    System.out.printf("%-20s%-15s%-30s%-15s\n", "Phone Number ", "Name", "Address", " Purchased Amount ");
    System.out.println("------------------------------------------------------------------------------------");

    for (Customer customer : customers) {
      System.out.printf("%-20s%-15s%-30s%-15.2f\n",
          customer.getPhoneNumber(), customer.getName(), customer.getAddress(), customer.getAmountSpent());
    }

    System.out.println("------------------------------------------------------------------------------------");
  }

  // Other methods
  // ...
  public String help() {
    String helpMessage = String.format("\t\tCommands\n");
    helpMessage += String.format("\t\t%20s\n");
    return helpMessage;
  }

  public Product parseAndGetProduct(Inventory inventory, Finance finance, String item) {
    if (item.matches(".* [0-9]*")) {
      String[] tokens = item.strip().toLowerCase().split(" ");
      Product product = inventory.getProduct(tokens[0]);
      return product;
    }
    return null;
  }

  public static int purchaseProduct(Inventory inventory, Finance finance, Product product, int number) {
    if (product != null) {
      int available = inventory.removeStock(product.getName(), number);
      finance.sellItem(product, available);
      return available;
    }
    return 0;
  }
}
  