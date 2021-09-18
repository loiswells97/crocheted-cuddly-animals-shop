/*Monkey is a concrete subclass of the abstract class Item, with name and price as given in the constructor and
 * stock as shown below.*/
public class Monkey extends Item {

    private static int stock = 8;

    public Monkey(){
        super("Monkey", 999);
    }

    /*Implements the getDescription method from Item. */
    public String getDescription(){
        return "50 cm tall with long arms and legs for swinging in trees";
    }

    /*Implements the abstract methods getStock, incrementStock and decrementStock from Item. Whilst these methods only
     * deal with the static variable stock, they are not declared static so that they can be declared in Item and
     * therefore used on entries of arrayList<Item> itemList in which polymorphism is used. */
    public int getStock(){
        return stock;
    }
    public void incrementStock(){
        stock++;
    }
    public void decrementStock(){
        stock--;
    }

}
