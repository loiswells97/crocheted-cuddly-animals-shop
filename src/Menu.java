import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    /*HashMap<String, String[]> userOptions contains String arrays with the menu options for the user at various points
    * in the program along with a String key to access each one. It was decided to use a HashMap rather than an array of
    * String arrays for userOptions in order to make calls to runMenu in Main more comprehensible.*/
    public final HashMap<String, String[]> userOptions=new HashMap<>();

    /*HashMap<String, String> userPrompts contains the questions that go with each menu call, with the keys for the
    * questions matching the keys for the relevant options in userOptions (see constructor). */
    public final HashMap<String, String> userPrompts=new HashMap<>();

    /*The constructor for Menu takes in a type (either "shop" or "order") which corresponds to the two menus that are
    * instantiated in the program (one in Main and the other in Order). It was decided to have a separate menu for these
    * two classes so that the Order constructor can set the payment type without having to access the menu from Main
    * (thus maintaining encapsulation), whilst still being able to validate the response in the same way. */
    public Menu(String type){
        if (type.equals("shop")){
            userPrompts.put("welcome", "\nWhat would you like to do?");
            userOptions.put("welcome", new String[]{"2: View item list", "3: View previous orders", "4: View basket", "6: Exit the shop"});

            userPrompts.put("list", "\nWhat would you like to do next?");
            userOptions.put("list", new String[]{"0: Add an item to the basket", "3: View previous orders", "4: View basket", "6: Exit the shop"});

            userPrompts.put("previous", "\nWhat would you like to do next?");
            userOptions.put("previous", new String[]{"1: Continue shopping", "3: Re-enter name", "4: View basket", "6: Exit the shop"});

            userPrompts.put("empty", "\nWhat would you like to do next?");
            userOptions.put("empty", new String[]{"1: Continue shopping", "3: View previous orders", "6: Exit the shop"});

            userPrompts.put("non-empty", "\nWhat would you like to do next?");
            userOptions.put("non-empty", new String[]{"0: Remove an item from the basket", "1: Continue shopping", "3: View previous orders", "5: Proceed to checkout", "6: Exit the shop"});

            userPrompts.put("confirm", "\nWould you like to confirm your order?");
            userOptions.put("confirm",new String[]{"0: No", "1: Yes"});

            userPrompts.put("non-confirmed", "\nWhat would you like to do next?");
            userOptions.put("non-confirmed", new String[]{"1: Cancel and return to shop", "5: Re-enter details"});

            userPrompts.put("confirmed", "\nWhat would you like to do next?");
            userOptions.put("confirmed", new String[]{"1: Start over", "6: Exit the shop"});

            userPrompts.put("exit", "\nAre you sure you want to exit the shop?");
            userOptions.put("exit",new String[]{"0: No", "1: Yes"});

        }else if (type.equals("order")){
            userPrompts.put("payment","\nSelect payment type");
            userOptions.put("payment", new String[]{"1: Paypal", "2: Credit card", "3: Debit card"});

        }
    }

    /*Method selectOption takes in a key and prints the question in userPrompts with that key to the console. It then
    * allows the user to select an option from the array of options with that key in userOptions and returns their
    * response. This method is declared private because it is used only by runMenu and so should never be accessed
    * outside this class.*/
    private int selectOption(String key){
        System.out.println(userPrompts.get(key));
        System.out.println(Arrays.toString(userOptions.get(key)));
        Scanner optionsScanner = new Scanner(System.in);
        return optionsScanner.nextInt();
    }

    /*Method optionIsValid takes in an int option and a key and checks whether the option is a valid response to the
    * question with that key. If so, true is returned, and otherwise false is returned. This method is declared private
    * because it is used only by runMenu and so should never be accessed outside this class.*/
    private boolean optionIsValid(int option, String key){
        boolean valid=false;
        for (String menuEntry:userOptions.get(key)){
            if (menuEntry.startsWith(""+option)){
                valid=true;
                break;
            }
        }
        return valid;
    }

    /*Method runMenu takes in a key and asks the user to answer the question with that key via selectOption. If their
    * response is invalid, they are asked to try again until they enter a valid option. The user's valid response is
    * the returned.*/
    public int runMenu(String key){
        int option=selectOption(key);
        while(!optionIsValid(option, key)){
            System.out.println("Please enter a number from the menu");
            option=selectOption(key);
        }
        return option;
    }
}