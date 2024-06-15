import java.util.ArrayList;
import java.util.Scanner;

class Car{
   private String carModel;
   private String carId;

   private String carBrand;
   private double carBasePriceperDay;

   private boolean isAvailabeForRent;


   public Car(String carModel, String carId, String carBrand, double carBasePriceperDay){
       this.carModel = carModel;
       this.carId = carId;
       this.carBrand = carBrand;
       this.carBasePriceperDay = carBasePriceperDay;
       this.isAvailabeForRent = true;
   }
   public String getCarModel(){
       return carModel;
   }
   public String getCarId(){
       return carId;
   }
   public String getCarBrand(){
       return carBrand;
   }

   public boolean isAvailabeForRent(){
       return isAvailabeForRent;
   }
   public double calculatePrice(int rentaldays){
       return carBasePriceperDay*rentaldays;
   }
   public void rent(){
        isAvailabeForRent = false;
   }
   public void returnCar(){
       isAvailabeForRent = true;
   }

}

class Customer{
    private String customerName;
    private String customerId;

    public Customer(String customerId,String customerName){
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public String getCustomerName(){
        return customerName;
    }
    public String getCustomerId(){
        return customerId;
    }

}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem{
    private ArrayList<Car> carList;
    private ArrayList<Customer>customerList;
    private ArrayList<Rental>rentalList;

    public CarRentalSystem(){
        carList = new ArrayList<>();
        customerList = new ArrayList<>();
        rentalList = new ArrayList<>();
    }

    public void addCar(Car car){
        carList.add(car);
    }

    public void addCustomer(Customer customer){
        customerList.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailabeForRent()){
            car.rent();
            rentalList.add(new Rental(car,customer,days));
        }
        else{
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car){
        Rental rentalToRemove = null;
        for(Rental rental: rentalList){
            if(rental.getCar()==car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove!=null){
            rentalList.remove(rentalToRemove);
            car.returnCar();
        }
        else{
            System.out.println("Car was not rented");
        }
    }

   public void menu(){
       Scanner sc = new Scanner(System.in);
       while(true){
           System.out.println("==== Car Rental System ====");
           System.out.println("1. Rent a Car");
           System.out.println("2. Return a Car");
           System.out.println("3. Exit");
           System.out.println("Enter your choice");

           int choice = sc.nextInt();
           sc.nextLine();

           if(choice==1){
               System.out.println("== Rent a Car ==");
               System.out.println("Enter your name: ");
               String customerName = sc.nextLine();
               System.out.println("Availabel cars:");
               for(Car car:carList){
                   if(car.isAvailabeForRent()){
                       System.out.println(car.getCarId()+" - "+car.getCarBrand()+" - "+car.getCarModel());
                   }
               }

               System.out.println("Enter the car id that you want for rent");
               String carId = sc.nextLine();
               System.out.println("Enter the number of days for rent");
               int rentaldays = sc.nextInt();
               sc.nextLine();

               Customer newCustomer = new Customer("cus"+customerList.size()+1,customerName);
               addCustomer(newCustomer);

               Car selectedCar = null;
               for(Car car:carList){
                   if(car.isAvailabeForRent() && car.getCarId().equalsIgnoreCase(carId)){
                       selectedCar = car;
                       break;
                   }
               }

               if(selectedCar!=null){
                   double totalPrice = selectedCar.calculatePrice(rentaldays);
                   System.out.println("=== Rental Information ===");
                   System.out.println("Customer Id: "+newCustomer.getCustomerId());
                   System.out.println("Customer Name: "+newCustomer.getCustomerName());
                   System.out.println("Car: "+selectedCar.getCarBrand()+" "+selectedCar.getCarModel());
                   System.out.println("Rent Days: "+rentaldays);
                   System.out.println("Total Price: Rs/- "+totalPrice);

                   System.out.println("Confirm Rental (Y/N): ");
                   String confirm = sc.nextLine();

                   if(confirm.equalsIgnoreCase("Y")){
                       rentCar(selectedCar,newCustomer,rentaldays);
                       System.out.println("Car rented Successfully");
                   }
                   else{
                       System.out.println("Rental Cancelled");
                   }
               }
               else{
                   System.out.println("Invalid car Selection or car not available for rent");
               }
           }else if(choice==2){
               System.out.println("== Return a Car ==");
               System.out.println("Enter the CarId you want to return: ");
               String carId = sc.nextLine();

               Car returningCar = null;
               for(Car car: carList){
                   if(car.getCarId().equalsIgnoreCase(carId) && !car.isAvailabeForRent()){
                       returningCar = car;
                       break;
                   }
               }
               if(returningCar!=null){
                   Customer customer = null;
                   for(Rental rental : rentalList){
                       if(rental.getCar() == returningCar){
                           customer = rental.getCustomer();
                           break;
                       }
                   }
                   if(customer!=null){
                       returnCar(returningCar);
                       System.out.println("Car Returned Sucessfully by"+customer.getCustomerName());
                   }
                   else{
                       System.out.println("Car was not rented or rental information missing.");
                   }
               }
               else{
                   System.out.println("Invalid car Id");
               }

           }
           else if(choice==3){
               break;
           }
           else {
               System.out.println("Invalid choice. Please enter a valid option");
           }
       }
       System.out.println("Thank you for using the Car Rental System!");

   }
}



public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("camry","C001","Tyota",500);
        Car car2 = new Car("Accord","C002","Honda",600);
        Car car3 = new Car("Swift","C003","Maruthi Suzuki",650);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}