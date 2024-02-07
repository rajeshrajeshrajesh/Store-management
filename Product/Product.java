package Product;
public class Product {
    private String name;
    private double price;
    private int number;
    private int id;
    private int restockAmt;
    private double costPrice;

    public Product(String name, int id, int number, double price, int restockAmt, double costPrice) {
        this.name = name;
        this.number = number;
        this.id = id;
        this.price = price;
        this.restockAmt = restockAmt;
        this.costPrice = costPrice;
    }

    // gets the restock amount of the product
    public int getRestockAmt() {
        return this.restockAmt;
    }

    // gets the cost price of the product
    public double getCostPrice() {
        return this.costPrice;
    }

    // Gets the name of the product
    public String getName() {
        return name;
    }

    // Gets the price of the product
    public double getPrice() {
        return price;
    }

    // Gets the number of the product
    public int getNumber() {
        return number;
    }

    // Gets the id of the product
    public int getId() {
        return id;
    }

    // Sets the name of the product
    public void setName(String name) {
        this.name = name;
    }

    // Sets the price of the product
    public void setPrice(double price) {
        this.price = price;
    }

    // Sets the price of the product
    public void setNumber(int number) {
        this.number = number;
    }

    // Sets the price of the product
    public void setId(int id) {
        this.id = id;
    }


    public String toString() {
        return String.format("%d %s: %d - %.2f/-", id, name, number, price);
    }
}
/*
class Produce extends Product{
    private Date expiry;
    public Produce(String name, double price, long expiry) {
        super(name, price);
        Calendar calendar = new GregorianCalendar();
        Date temp = new Date();
        calendar.setTime(temp);
        this.expiry = new Date(temp.getTime()+expiry);
    }

    public Date getDate() {
        return this.expiry;
    }
}*/
