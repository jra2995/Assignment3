package Assignment3;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private int weight;

    //Default Contructor for Item class
    public Item(String name, double price, int quantity, int weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
    }

    //Getters for parameters in Item
    public int getWeight() {
        return weight;
    }
    public int getQuantity() {return quantity;}
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    
    //Setters for parameters in Item
    public void setWeight(int w){
    	weight = w;
    }
    public void setQuantity(int q){
    	quantity = q;
    }
    public void setPrice(double p){
    	price = p;
    }
    public void setName(String n){
    	name = n;
    }

    /******************************************************************************
     * Method Name: calculatePrice                                               *
     * Purpose: Calculate the final price of the Item based on added Shippig and *
     *          Tax Costs                                                        *
     * Returns: float final_price                                                *
     ******************************************************************************/
    double calculatePrice() {
        double final_price = 0;

        final_price = (double) (this.price + (20 * this.weight) * this.quantity);

        return final_price;
    }

    /******************************************************************************
     * Method Name: toString                                                      *
     * Purpose: Override of java toString to create a string later used to output *
     * class attributes                                                           *
     * Returns: String for output                                                 *
     ******************************************************************************/
    @java.lang.Override
    public java.lang.String toString() {
        return "Name = \'" + name + '\'' +
                ", Price = $" + String.format("%.2f", price) +
                ", Quantity = " + quantity +
                ", Weight = " + weight +
                ", Final Price: $" + calculatePrice();
    }

    /******************************************************************************
     * Method Name: printItemAttributes                                           *
     * Purpose: Call and output toString()                                        *
     * Returns: String for output                                                 *
     ******************************************************************************/
    void printItemAttributes() {
        //Print all applicable attributes of this class
        System.out.println(this.toString());
    }
}
