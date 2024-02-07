package Customer;

import java.io.*;

import java.util.Scanner;

public class Customer {

  public String phoneNumber;
  public String name;
  public String address;
  private double amountSpent;

  public Customer(String phoneNumber, String name, String address, double amountSpent) {
    this.phoneNumber = phoneNumber;
    this.name = name;
    this.address = address;
    this.amountSpent = amountSpent;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public double getAmountSpent() {
    return amountSpent;
  }

  public void setAmount(double amount) {
    this.amountSpent += amount;
  }

  public String toString() {
    return String.format("Name: %s, Phone: %s, Address: %s, Amount Spent: %.2f", name, phoneNumber, address,
        amountSpent);
  }

}