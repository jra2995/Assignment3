/**
 * Classes to model a shopping cart and items of different types
 * Solves EE422C Programming Assignment #3
 * @author Jett Anderson and Brandon Arrindell 
 * eid: jra2995, bja733
 * @version 1.00 2016-02-21
 */

package Assignment3;

public class Grocery extends Item {
    private boolean perishable;

    //Constructor for Grocery
    public Grocery(String name, double price, int quantity, int weight, boolean perishable) {
        super(name, price, quantity, weight);
        this.perishable = perishable;
    }

    //Getter for Grocery's additional param
    public boolean isPerishable() {
        return perishable;
    }

    /******************************************************************************
     * Method Name: toString                                                      *
     * Purpose: Override of toString to add category to output and additional     *
     *          params.                                                           *
     * Returns: output String                                                     *
     ******************************************************************************/
    @Override
    public String toString() {
        return "Category: Grocery, " +
                super.toString() +
                ", Perishable = " + perishable;
    }

    /******************************************************************************
     * Method Name: calculatePrice                                                *
     * Purpose: Override of Item's calculatePrice method based on additional      *
     *          shipping rules that apply to grocery items                        *
     * Returns: float final_price                                                 *
     ******************************************************************************/
    @Override
    double calculatePrice() {
        double final_price = 0;
        double shippingCost = (20 * this.getWeight()) * this.getQuantity();

        if (this.isPerishable()) {
            shippingCost = shippingCost + shippingCost * .2;
        }


        final_price = this.getPrice() * this.getQuantity() + shippingCost;
        return final_price;
    }
}
