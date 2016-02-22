/**
 * Classes to model a shopping cart and items of different types
 * Solves EE422C Programming Assignment #3
 * @author Jett Anderson and Brandon Arrindell 
 * eid: jra2995, bja733
 * @version 1.00 2016-02-21
 */

package Assignment3;

public class Clothing extends Item {
    final double TAX_RATE = 0.1;
    private String state;

    //Constructor for Clothing
    public Clothing(String name, double price, int quantity, int weight) {
        super(name, price, quantity, weight);
    }

    /******************************************************************************
     * Method Name: toString                                                      *
     * Purpose: Override of toString to add category to output.                   *
     * Returns: output String                                                     *
     ******************************************************************************/
    @Override
    public String toString() {
        return "Category: Clothing, " +
                super.toString();
    }

    /******************************************************************************
     * Method Name: calculatePrice                                                *
     * Purpose: Override of Item's calculatePrice method based on additional tax  *
     * rules that apply to clothing                                      *
     * Returns: float final_price                                                 *
     ******************************************************************************/
    double calculatePrice() {
        double final_price = 0;
        final_price = (double) (super.calculatePrice() + super.calculatePrice() * TAX_RATE);
        return final_price;
    }
}
