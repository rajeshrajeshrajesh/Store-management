package emp;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Employee {

    double hour;
    double hourlyWage;
    int id;
    String name;
    String aadhaar;
    int age;
    String type;
    public Employee(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        this.hour = hour;
        this.hourlyWage = hourlyWage;
        this.id = id;
        this.age = age;
        this.name = name;
        this.aadhaar = aNo;
    }
    public String toString(){
        String s = "";
        s += ("\n\n\t\t EMPLOYEE DETAILS \n");
        s+=("Name : \t"+this.name)+"\n";
        s+=("ID : \t"+this.id)+"\n";
        s+=("Type : \t"+this.type)+"\n";
        s+=("Age : \t"+ this.age)+"\n";
        s+=("Aadhaar : \t"+this.aadhaar)+"\n";
        s+=("Hours worked : \t"+this.hour)+"\n";
        s+=("Hourly wage : \t"+this.hourlyWage)+"\n";
        s+=("\n");
        return  s;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getHour() {
        return hour;
    }
    public static boolean hasAadhar(String aadhar) throws NullPointerException{
        if(aadhar.length() != 12 ||
                !aadhar.matches("[0-9]*")) {
            throw new NullPointerException();
        }
        return true;
    }
    public static Employee createNew(String type){

        Scanner s = new Scanner(System.in);
        System.out.println("\t\t New Employee Inputs \n");
        System.out.println("Name : ");

        String name = s.nextLine();
        System.out.println("ID : ");
        int id = s.nextInt();
        System.out.println("Aadhaar : ");
        String aadhar = s.next();
        try {
            hasAadhar(aadhar);
        }catch (NullPointerException e){
            System.out.println("Aadhar format mismatch ");
            System.out.println("Enter aadhar again : ");
            aadhar = s.next();
        }
        System.out.println("Age : ");
        int age = s.nextInt();
        System.out.println("Hourly wage : ");
        double wage = s.nextDouble();
        System.out.println("Enter hours worked");
        double hours = s.nextDouble();

        // T a = (T)(new Employee());
        if(type.equalsIgnoreCase("janitor"))
            return  new Janitor(hours, wage, id, aadhar, name, age);
        else if(type.equalsIgnoreCase("cashier"))
            return new Cashier(hours,wage,id,aadhar,name,age);
        else if(type.equalsIgnoreCase("aisleworker"))
            return new AisleWorkers(hours,wage,id,aadhar,name,age);
        else if(type.equalsIgnoreCase("manager"))
            return new Manager(hours,wage,id,aadhar,name,age);
        return  new Employee(hours,wage,id,aadhar,name,age);
    }
    public static void employeemanage(){
        try {
            FileWriter fileWriter = new FileWriter("empList.txt",true);
            Scanner s = new Scanner(System.in);
            Employee[] employees = new Employee[20];
            int size = 0;
            String opt;
            do {
                System.out.println(" Add Cashier ");
                System.out.println(" Add Janitor ");
                System.out.println(" Add Aisle Worker ");
                System.out.println(" Add Manager ");
                System.out.println(" Display records : ");
                System.out.println(" Exit ");
                System.out.println("Enter you choice in words as mentioned above: ");
                opt = s.nextLine();
                if (opt.equalsIgnoreCase("add cashier")) {
                    String type = "cashier";
                    employees[size] = createNew(type);
                    fileWriter.write(""+employees[size].name+","+employees[size].aadhaar+","+employees[size].type+","+employees[size].age+","+employees[size].id+","+employees[size].hourlyWage+","+employees[size].hour+","+employees[size].age+"\n");
                    size++;
                }
                if (opt.equalsIgnoreCase("add janitor")) {
                    String type = "janitor";
                    employees[size] = createNew(type);
                    fileWriter.write(""+employees[size].name+","+employees[size].aadhaar+","+employees[size].type+","+employees[size].age+","+employees[size].id+","+employees[size].hourlyWage+","+employees[size].hour+","+employees[size].age+"\n");

                    size++;
                }
                if (opt.equalsIgnoreCase("add manager")) {
                    String type = "manager";

                    employees[size] = createNew(type);
                    fileWriter.write(""+employees[size].name+","+employees[size].aadhaar+","+employees[size].type+","+employees[size].age+","+employees[size].id+","+employees[size].hourlyWage+","+employees[size].hour+","+employees[size].age+"\n");

                }
                if (opt.equalsIgnoreCase("add aisleworker")) {
                    String type = "aisleworker";
                    employees[size] = createNew(type);
                    fileWriter.write(""+employees[size].name+","+employees[size].aadhaar+","+employees[size].type+","+employees[size].age+","+employees[size].id+","+employees[size].hourlyWage+","+employees[size].hour+","+employees[size].age+"\n");
                    size++;
                }
                if (opt.equalsIgnoreCase("display records")) {
                    try {
                        //for (Employee e : employees)
                            //System.out.println(e.toString());
                            System.out.println(readObjects());
                            System.out.println();
                    } catch (NullPointerException e) {
                        System.out.println();
                    }

                }
            } while (!opt.equalsIgnoreCase("exit"));
            fileWriter.close();
        } catch (IOException e) {
            //
        }
    }

    public static ArrayList<Employee> readObjects(){
        ArrayList<Employee> reads = new ArrayList<Employee>();
        try {
            Scanner s = new Scanner(new File("empList.txt"));
            while(s.hasNextLine()){
                String str = s.nextLine();
                String[] tokens = str.split(",");
                if(tokens[2].equalsIgnoreCase("janitor"))
                    reads.add(new Janitor(Double.parseDouble(tokens[6].strip()),Double.parseDouble(tokens[5].strip()),Integer.parseInt(tokens[4].strip()),tokens[1].strip(),tokens[0].strip(),Integer.parseInt(tokens[3].strip())));
                else if (tokens[2].equalsIgnoreCase("cashier"))
                    reads.add(new Cashier(Double.parseDouble(tokens[6].strip()),Double.parseDouble(tokens[5].strip()),Integer.parseInt(tokens[4].strip()),tokens[1].strip(),tokens[0].strip(),Integer.parseInt(tokens[3].strip())));
                else if(tokens[2].equalsIgnoreCase("manager"))
                    reads.add(new Manager(Double.parseDouble(tokens[6].strip()),Double.parseDouble(tokens[5].strip()),Integer.parseInt(tokens[4].strip()),tokens[1].strip(),tokens[0].strip(),Integer.parseInt(tokens[3].strip())));
                else if(tokens[2].equalsIgnoreCase("aisleworker"))
                    reads.add(new AisleWorkers(Double.parseDouble(tokens[6].strip()),Double.parseDouble(tokens[5].strip()),Integer.parseInt(tokens[4].strip()),tokens[1].strip(),tokens[0].strip(),Integer.parseInt(tokens[3].strip())));
                //hours, wage, id, aadhar, name, age
                //hours, wage, id, aadhar, name, age//hours, wage, id, aadhar, name, age//hours, wage, id, aadhar, name, age
                else reads.add(new Employee(Double.parseDouble(tokens[6].strip()),Double.parseDouble(tokens[5].strip()),Integer.parseInt(tokens[4].strip()),tokens[1].strip(),tokens[0].strip(),Integer.parseInt(tokens[3].strip())));
                        //hours, wage, id, aadhar, name, age
            }
            //System.out.println(" "+reads.toString());
        }catch (IOException e){
            //
        }
        return reads;
    }


}

class Cashier extends Employee{
    public Cashier(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
        super.type = "Cashier";
    }
}
class Janitor extends Employee{
    public Janitor(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
        super.type = "Janitor";
    }
}
class Manager extends Employee{
    public Manager(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
        super.type = "Manager";
    }
}
class AisleWorkers extends Employee{
    public AisleWorkers(double hour, double hourlyWage, int id, String aNo, String name, int age) {
        super(hour, hourlyWage, id, aNo, name, age);
        super.type = "Aisle Worker";
    }
}
