/*The Basket class is used to store the numbers of each item that are in the user's basket and the total cost of the
* items in the basket. It has a number of methods for accessing or manipulating the basket*/

public class Basket {

    /*basketItems is an array of ints that stores the number of each item that is in the basket. The indices in
    * basketItems correspond to those in Main.itemList, such that the ith entry in basketItems denotes how many of
    * itemList[i] are in the basket. The int basketTotal (in pence) gives the total cost of the items in the basket.
    * Since the customer always enters the shop with an empty basket, these fields are given the initial values shown
    * below and the default no-argument constructor is used. */
    private int[]basketItems = {0,0,0,0,0,0,0,0,0,0};

    /*Below is a toString method for the basket, called when the basket is viewed by the user and when reviewing the
    * order at the checkout. For each item of which there is a non-zero number in the basket, the result returned
    * includes the item code (1+(its index in itemList) for a more natural user interface), the result of calling
    * toString on the item and the number of that item in the basket. This is achieved through loop concatenation. The
    * result also includes the total cost of the items in the basket, converted into pounds and pence. If the basket is
    * empty, the above is bypassed and toString returns "The basket is empty.". */
    public String toString(){
        String basketString="Basket:\n";
        if (getBasketTotal()>0){
            for (int i=0; i<10; i++){
                if (basketItems[i]!=0) {
                    basketString+="Item code "+(i+1)+": "+Main.getItemList()[i].toString()+"(x"+basketItems[i]+")\n";
                }
            }
            basketString+="Total cost: £"+ getBasketTotal()/100+"."+ getBasketTotal()%100;
        }else {
            basketString = "The basket is empty.";
        }
        return basketString;
    }

    /*getBasketTotal returns the total cost of items in the basket, taking into account the 2 Starfish for £9.99 deal
    * via modular arithmetic. This method is used in toString above as well as being called in Main in order to check
    * whether the basket is empty. */
    public int getBasketTotal(){
        int basketTotal=0;
        for (int i=0; i<10; i++){
            if (i==8){
                basketTotal+=(basketItems[i]/2)*999+basketItems[i]%2*Main.getItemList()[i].getPrice();
            }else {
                basketTotal += basketItems[i] * Main.getItemList()[i].getPrice();
            }
        }
        return basketTotal;
    }

    /*Method getSaving returns the user's saving due to the 2 Starfish for £9.99 deal. This is printed to the console by
    * runBasket in Main if the saving is non-zero to congratulate the user on saving money and avoid confusion if the
    * basket total is lower than expected.*/
    public int getSaving(){
        int totalRRP=0;
        for (int i=0; i<10; i++){
                totalRRP += basketItems[i] * Main.getItemList()[i].getPrice();
            }
        return totalRRP-getBasketTotal();
    }

    /*Method addItem is called in Main when items are added to the basket. If the given item is in stock, the relevant
    * index of basketItems is incremented, the price of the item is added to total and the item's stock is decremented.
    * This stops the user adding more of an item to the basket than the shop has in stock. If an item is out of stock, a
    * message to this effect is printed to the console. */
    public void addItem(int index){
        Item item = Main.getItemList()[index];
        if (item.getStock()>0){
            basketItems[index]++;
            item.decrementStock();
            System.out.println("Added 1 "+item.getName()+" to the basket.");
        }else{
            System.out.println("Sorry, "+item.getName()+" is out of stock.");
        }
    }

    /*Method removeItem is called in Main when items are removed from the basket. Items are removed by item code, which
    * is printed when the basket is viewed, and these correspond to the indices of Main.itemList. If the entry at the
    * relevant index of itemValues is non-zero, then the stock of the relevant item is incremented, the entry of
    * basketItems is decremented and the price of the item is subtracted from the total. If the relevant entry of
    * basketItems is zero or the index is out of range, an error message is printed to the console. */
    public void removeItem(int index) {
        if (index>=0 && index<10 && basketItems[index] > 0) {
            Item item = Main.getItemList()[index];
            item.incrementStock();
            basketItems[index]--;
            System.out.println("Removed 1 " + item.getName() + " from the basket.");
        } else {
            System.out.println("Error: incorrect item code");
        }
    }

}
