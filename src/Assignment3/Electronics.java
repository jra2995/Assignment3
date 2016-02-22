/**
 * Classes to model a shopping cart and items of different types
 * Solves EE422C Programming Assignment #3
 * @author Jett Anderson and Brandon Arrindell 
 * eid: jra2995, bja733
 * @version 1.00 2016-02-21
 */

package Assignment3;

public class Electronics extends Item {
    final double TAX_RATE = 0.1;
    // Variables, constructors etc. here.
    private String state;
    private boolean fragility;

    //Constructor for Electronics
    public Electronics(String name, double price, int quantity, int weight, String state, boolean fragility) {
        super(name, price, quantity, weight);
        this.state = state;
        this.fragility = fragility;
    }

    //Getters for Electronics additional params
    public String getState() {
        return state;
    }

    public boolean isFragility() {
        return fragility;
    }

    /******************************************************************************
     * Method Name: toString                                                      *
     * Purpose: Override of toString to add category to output.                   *
     * Returns: output String                                                     *
     ******************************************************************************/
    @Override
    public String toString() {
        return "Category: Electronics, " +
                super.toString() +
                ", State = \'" + state + '\'' +
                ", Fragility = " + fragility;
    }

    /******************************************************************************
     * Method Name: calculatePrice                                                *
     * Purpose: Override of Item's calculatePrice method based on additional tax  *
     *          and shipping rules that apply to electronics                      *
     * Returns: float final_price                                                 *
     ******************************************************************************/
    @Override
    double calculatePrice() {
        double final_price = 0;
        double salesTax = 0;
        double shippingCost = (20 * this.getWeight()) * this.getQuantity();

        if (this.isFragility()) {
            shippingCost = shippingCost + shippingCost * .2;
        }
        if (!this.getState().matches("TX|NM|VA|AZ|AK")) {
            salesTax = this.getPrice() * TAX_RATE;
        }
        final_price = (double) (this.getPrice() * this.getQuantity() + shippingCost + salesTax);
        return final_price;
    }
}

