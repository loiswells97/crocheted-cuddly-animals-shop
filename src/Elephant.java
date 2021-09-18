/*Elephant is a concrete subclass of the abstract class Item, with name and price as given in the constructor and
 * stock as shown below.*/
public class Elephant extends Item {

    private static int stock = 4;

    public Elephant(){
        super("Elephant", 1399);
    }

    /*Implements the getDescription method from Item. */
    public String getDescription(){
        return "30cm tall with floppy ears and a long trunk";
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
