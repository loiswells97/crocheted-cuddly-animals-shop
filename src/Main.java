import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {

    /*int state is used to control the flow of the program to the different elements of the shop, with states
    representing the following:
    * 1: Welcome (initial state)
    * 2: Viewing item list
    * 3: Viewing previous orders
    * 4: Viewing the basket
    * 5: Checkout
    * 6: Confirm exit?
    * 7: Exit confirmed
    * Input from the menu is used to change state at the end of methods, and main then calls the relevant method next.*/
    private static int state=1;

    /*Item[] itemList is an array containing one of each of the Item implementations available in the shop for
    * future reference. This avoids the creation of a large number of objects later, which could become confusing.*/
    private static final Item[] itemList = {
            new AnglerFish(), new Chameleon(), new Elephant(), new Giraffe(), new Lion(),
            new Monkey(), new Octopus(), new Owl(), new Starfish(), new Whale()};

    /*Basket basket is a new Basket object that will store the items that the user adds to their basket. By default it
    * is initiated as being empty when the user enters the shop.*/
    private static Basket basket=new Basket();

    /*Menu menu of type "shop" holds the questions and options for each menu call that is presented to the user. The
    * menu.runMenu(key) method is used to call the relevant menu options at different points in the program and return
    * the user's response, with the following keys calling the following:
     * "welcome":        welcome menu used in runWelcome
     * "list":           after viewing list of available items in runList
     * "previous":       after viewing previous orders in runPrevious
     * "empty":          after viewing an empty basket in runBasket
     * "non-empty":      after viewing a non-empty basket in runBasket
     * "non-confirmed":  after non-confirmed order in runCheckout
     * "confirmed":      after confirmed order in runCheckout
     * "confirm":         confirm order in runCheckout/confirm exit in runExit*/
    private static final Menu shopMenu = new Menu("shop");

    /*Method printItemList is used to print the list of items available in the shop (stored in itemList) to the
    * console in the runList method so that the user can choose which items they wish to add to their basket.*/
    public static void printItemList(){
        int i=1;
        for (Item item:itemList){
            System.out.println(i+": "+item.toString()+" ("+item.getStock()+" in stock)");
            i++;
        }
    }

    /*A getter for itemList allowing itemList to be accessed by the Basket class whilst maintaining encapsulation.*/
    public static Item[] getItemList(){
        return itemList;
    }

    /*Method readOrder takes in the customer's name and searches the external file Orders.txt for the user's previous
    * orders and then prints them to the console. If the user has no previous orders, they are instead informed of this.
    * This method is called by runPrevious, which asks for the user's name and passes the response into this method.*/
    public static void readOrder(String name){
        Scanner reader = null;
        boolean success = false;
        try{
            reader=new Scanner(new BufferedReader(new FileReader("Orders.txt")));
            while (reader.hasNextLine()){
                if (reader.nextLine().equalsIgnoreCase(name)){
                    if(!success){
                        System.out.println("\nWelcome back "+name+"! Here are your previous orders:");
                    }
                    success=true;
                    System.out.print("\n");
                    String nextLine = reader.nextLine();
                    while (!nextLine.isBlank()){
                        System.out.println(nextLine);
                        nextLine = reader.nextLine();
                    }
                }
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }finally{
            reader.close();
        }
        if (!success){
            System.out.println("\nYou have not made any previous orders.");
        }
    }

    /*Method runWelcome (runs when state=1) allows the user to decide whether they would like to view the item list,
     * view their previous orders, view the basket or exit the shop (see "welcome" in Menu("shop")) and changes the
     * state accordingly.*/
    public static void runWelcome(){
        state=shopMenu.runMenu("welcome");
    }

    /*Method runList (runs when state=2) prints the itemList and the deal to the console and then allows the user to add
    * an item to the basket, view previous orders, view basket or exit the shop (see "list" in Menu("shop")) and
    * responds accordingly. If the user wishes to add an item to the basket, the user is asked which item and
    * basket.addItem is called. Otherwise, the state is changed as appropriate.*/
    public static void runList(){
        printItemList();
        System.out.println("\n*** DEAL ***\n*** Get 2 Starfish for £9.99! ***");
        int option=shopMenu.runMenu("list");
            if (option==0) {
                System.out.println("\nPlease enter the code for the item you wish to add from the list above");
                Scanner optionsScanner = new Scanner(System.in);
                int itemOption = optionsScanner.nextInt();
                while (itemOption < 1 || itemOption > 10) {
                    System.out.println("\nPlease enter a number between 1 and 10.");
                    itemOption = optionsScanner.nextInt();
                }
                basket.addItem(itemOption - 1);
                System.out.println("\nHere is the updated item list:");
            }else{
                state=option;
            }
    }

    /*runPrevious (runs when state=3) asks for the user's name and looks for previous purchases in the external file
    * Orders.txt by calling readOrder. If this is successful, each previous order will be printed to the console, and if
    * not a message will be printed telling the user that they have not made any previous orders. The user can then
    * choose to re-enter their name (if they mis-spelt it for example), continue shopping, view their basket, or exit
    * the shop (see "previous" in Menu("shop")) and the state is changed accordingly. */
    public static void runPrevious(){
        Scanner stringScan = new Scanner(System.in);
        System.out.println("\nPlease enter your full name.");
        String name = stringScan.nextLine();
        readOrder(name);
        state=shopMenu.runMenu("previous");
    }


    /*Method runBasket (runs when state=4) prints the user's basket to the console and if they are saving money due to
    * the 2 Starfish for £9.99 deal they are informed of the size of their saving. The user's options then depend on
    * whether the basket is empty or not, since it does not make sense to allow the user to remove an item from the
    * basket or proceed to checkout if the basket is empty. If the basket is empty, the user is allowed to continue
    * shopping, view previous orders or exit the shop (see "empty" in Menu("shop")), and the state is changed
    * accordingly. If the basket is non-empty, the user is allowed to remove an item from the basket, continue shopping,
    * view previous orders, proceed to checkout or exit the shop (see "non-empty" in Menu("shop")). If they choose to
    * remove an item from the basket, the code entered by the user removes an item of type itemList[code-1] for a more
    * natural user interface. Otherwise the state is changed as appropriate. If the state is unchanged (i.e. when an
    * item is removed from the basket), the main method will call runBasket again and the user's updated basket will be
    * printed to the console to avoid confusion. */
    public static void runBasket() {
        System.out.println("\n"+basket.toString());
        if (basket.getSaving()>0){
            System.out.println("\nYou're saving £"+basket.getSaving()/100+"."+basket.getSaving()%100+"!");
        }
        if (basket.getBasketTotal()==0){
            state=shopMenu.runMenu("empty");
        }else{
            int option=shopMenu.runMenu("non-empty");
            if (option==0) {
                System.out.println("\nPlease enter the code for the item you wish to remove from the basket above");
                Scanner optionsScanner = new Scanner(System.in);
                int itemOption = optionsScanner.nextInt();
                basket.removeItem(itemOption - 1);
            }else{
                state=option;
            }
        }
    }

    /*Method runCheckout (runs when state=5) creates a new Order object with Basket basket and collects user details
    * (payment type, name, house number and postcode) via the Order constructor. The order is then printed to the
    * console and the user asked for confirmation (see "confirm" in Menu("shop")). If the user confirms their details,
    * their order is written to Orders.txt via writeOrder, their basket is reset to empty and the user can choose to
    * start over or exit the shop (see "confirmed" in Menu("shop")). If they do not confirm their order, the user can
    * re-enter their details if they made a mistake (remaining in state 5 to recall runCheckout), or return to the shop
    * (see "non-confirmed" in Menu("shop")). */
    public static void runCheckout() {
        Order order = new Order(basket);
        System.out.println("\nHere is your order:");
        System.out.println(order.toString());
        int option=shopMenu.runMenu("confirm");
        if (option ==1){
            order.writeOrder();
            System.out.println("\nOrder confirmed. Thank you shopping with us.");
            basket= new Basket();
            option=shopMenu.runMenu("confirmed");
            if (option==1){
                System.out.println("\nWelcome to the Crocheted Cuddly Animals Shop!");
            }
            state=option;
        }else {
            state=shopMenu.runMenu("non-confirmed");
            }
        }


    /*runExit (runs when state=6) gives the user the opportunity to change their mind if they choose to exit the shop by
    * mistake. If they confirm their exit then the state is set to 7, and the main method will print a thank you message
    * and then terminate. Otherwise the state is set to 1 (welcome state) and the main method will call runWelcome.*/
    public static void runExit(){
        int option=shopMenu.runMenu("exit");
        if (option==1){
            state = 7;
        }else{
            state=1;
        }
    }

/*The main method prints a welcome message when the customer enters the shop, then calls runWelcome (since the initial
* state is 1) to determine which method to run next. While the program is not in state 7 (the confirmed exit state), the
* main method checks the state after each method call and then calls the appropriate method as per the descriptions of
* the states given above. Finally, a thank you message is printed to the console when exit is confirmed and then the
* program terminates.*/
    public static void main(String[] args) {
        System.out.println("\nWelcome to the Crocheted Cuddly Animals Shop!");
        while (state!=7){
            switch(state){
                case 1: runWelcome();
                        break;
                case 2: runList();
                        break;
                case 3: runPrevious();
                        break;
                case 4: runBasket();
                        break;
                case 5: runCheckout();
                        break;
                case 6: runExit();
            }
        }
        System.out.println("\nThank you for visiting the Crocheted Cuddly Animals Shop. We hope to see you again soon!");
    }
}