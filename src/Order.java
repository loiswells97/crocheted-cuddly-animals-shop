import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*Order stores the details of the user's order in Main.runCheckout and has methods toString to return the order details
* and writeOrder to write the order to an external file.*/
public class Order {

    /*Fields basket, paymentType, name, houseNumber and postcode store the user's details*/
    private final Basket basket;
    private String paymentType;
    private final String name;
    private final String houseNumber;
    private final String postcode;

    /*Menu orderMenu is a Menu of type "order" with one key "payment", and is used to allow the uer to choose their
    * payment type and to validate their input via the Menu methods. */
    private static final Menu orderMenu = new Menu("order");

    /*The Order constructor takes in the basket and sets the user's payment type, name, house number and postcode by
    asking them to enter their details via the command line. */
    public Order(Basket basket){
        this.basket=basket;
        int option=orderMenu.runMenu("payment");
        switch (option) {
            case 1:
                paymentType = "Paypal";
                break;
            case 2:
                paymentType = "Credit card";
                break;
            case 3:
                paymentType = "Debit card";
                break;
        }
        Scanner stringScan = new Scanner(System.in);
        System.out.println("\nPlease enter your full name.");
        name = stringScan.nextLine();
        System.out.println("\nPlease enter your house number");
        houseNumber = stringScan.nextLine();
        System.out.println("\nPlease enter your postcode");
        postcode = stringScan.nextLine();
    }

    /*Method toString returns a String of the user's basket and details.*/
    public String toString(){
        return basket.toString()+"\nPayment Type: "+paymentType+", Name: "+name+", House number: "+houseNumber+", Postcode: "+postcode;
    }

    /*Method writeOrder writes name, a formatted date and the current basket to the external file Orders.txt for future
    * reference. The date is included to make the order information more useful to the user when accessed via readOrder.
    * This method is called in runCheckout once the user has confirmed their order. */
    public void writeOrder() {
        try {
            FileWriter writer = new FileWriter("Orders.txt", true);
            PrintWriter printer = new PrintWriter(writer);
            printer.println(name);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime date = LocalDateTime.now();
            printer.println(formatter.format(date));
            printer.println(basket.toString() + "\n");
            writer.close();
            printer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


}
