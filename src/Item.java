/*Item is the abstract superclass for the items sold in the shop.*/
public abstract class Item {

    /*Every subclass of Item must have a name and price specified in order to be instantiated, hence the only
    * constructor is the all-argument one. Note that the price is given as an int in pence rather than a double in
    * pounds to avoid rounding errors in the basket total and only one decimal place being given when the pence are a
    * multiple of 10. The approach used of using integer division and modular arithmetic to display the price to the
    * user (as in toString below) would result in only one decimal place if the total was a full number of pounds, but
    * given the prices chosen and that there are fewer than 100 items in stock throughout the entire shop, this is not a
    * problem.*/
    private final String name;
    private final int price;

    public Item(String name, int price){
        this.name=name;
        this.price=price;
    }

    /*Getters for the name and price for accessing elsewhere (but no setters as name and price are final) and a toString
    * method allowing the details to be printed to the console or incorporated into other Strings.*/
    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public String toString(){
        return "[Item name: "+name+", Price: £"+price/100+"."+price%100+", Description: "+getDescription()+"]";
    }

    /* Abstract methods declared here so they can be called on entries of itemList, an arrayList of Items in the Main
    * method which is referenced throughout. */
    public abstract String getDescription();

    /*Although implemented in the same way in each subclass, getStock, incrementStock and decrementStock are declared
     * abstract here because they deal with the stock, which is a static field in each subclass of Item. They are
     * included here as per the comment above.*/
    public abstract int getStock();
    public abstract void incrementStock();
    public abstract void decrementStock();

}
