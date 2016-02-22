package Assignment3;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class A3Driver {

    public static void main(String[] args) 
    {
        // TODO Auto-generated method stub
 
        //Open file; file name specified in args (command line), and begin reading file if available
    	ArrayList<Item> shoppingCart = new ArrayList<Item>();
		File file = new File(args[0]);
	    try{
	    	Scanner scan = new Scanner(file);
	    	getInput(scan, shoppingCart);
	    	scan.close();
	    }
	    catch(FileNotFoundException f){
	     	f.printStackTrace();
	     	System.err.println("Error - File " + file + " was not found.");
	    }
    }
    
    /**
     * Processes input from file one line (transaction) at a time
     * @param scan is the Scanner that will read file lines one at a time
     */
    public static void getInput(Scanner scan, ArrayList<Item> shoppingCart){
        // Parse input, take appropriate actions based on transaction operation
        while(scan.hasNextLine()){
        	// Gets transaction line and splits into appropriate command Strings
    		String transaction = scan.nextLine();
    		String[] commands = transaction.split(" ");
			String operation = commands[0];
			
			// Checks for a legitmate operation, and then sends control to the appropriate transaction method 
    		if(!operation.equals("insert") && !operation.equals("search") && !operation.equals("delete") && 
    				!operation.equals("update") && !operation.equals("print")){
    			System.err.println("Error - The operation must be of form \"insert\", \"search\", \"delete\", " + 
    				"\"update\", or \"print\".\nMoving to next transaction.");
    		}
    		else{
    			if(operation.equals("print")){
    				processPrint(commands, shoppingCart);
    			}
    			else if(operation.equals("insert")){
    				processInsert(commands, shoppingCart);
    			}
    			else if(operation.equals("update")){
    				processUpdate(commands, shoppingCart);
    			}
    			else if(operation.equals("delete")){
    				processDelete(commands, shoppingCart);
    			}
    			else if(operation.equals("search")){
    				processSearch(commands, shoppingCart);
    			}
    		}
    	}
    }
    
    /**
     * If an insert command is necessary, this method reads all commands from an insert transaction line
     * and makes sure every field is filled correctly, and then creates an Item of the appropriate type with
     * information provided from the file and adds it to the shopping cart ArrayList
     * @param commands the command fields array of the insert transaction line
     * @param shoppingCart the arraylist shopping cart that items will be added to
     */
    public static void processInsert(String[] commands, ArrayList<Item> shoppingCart){
    	// Insert commands have to have at least 6 commands fields - 6 for clothing, 7 for groceries, and 
    	// 8 for electronics
    	if(commands.length < 6){
    		System.err.println("Error - All Insert commands must be of form <insert> <category> " + 
    				"<name> <price> <quantity> <weight> <optional field 1> <optional field 2>.\nMoving to " + 
    				"next transaction.");
    	}
    	else{
    		// Items can only be of type clothing, electronics, or groceries, case-sensitive
    		String category = commands[1];
    		if(!category.equals("clothing") && !category.equals("electronics") && !category.equals("groceries")){
    			System.err.println("Error - The category must be of form \"electronics\", \"groceries\", " + 
    					"or \"clothing\".\nMoving to next transaction.");
    		}
    		else{
    			// Prices have to be either non-negative integers, or double values with two decimal digits only
    			String name = commands[2];
    			String priceString = commands[3];
    			if(!priceString.matches("\\d+.\\d{2}") && !priceString.matches("\\d+")){
    				System.err.println("Error - The price must be of form <some number of digits 0-9>." + 
    						"<exactly 2 digits 0-9>, or of a non-negative integer.\nMoving to next transaction.");
    			}
    			else{
    				// Quantities have to be non-negative integers
    				double price = Double.parseDouble(priceString);
    				String quantityString = commands[4];
    				if(!quantityString.matches("\\d+")){
    					System.err.println("Error - The quantity must be a whole number that is " + 
    							"non-negative.\nMoving to next transaction.");
    				}
    				else{
    					// Weights have to be non-negative integers
    					int quantity = Integer.parseInt(quantityString);
    					String weightString = commands[5];
    					if(!weightString.matches("\\d+")){
    						System.err.println("Error - The weight must be a whole number that is " + 
    								"non-negative.\nMoving to next transaction.");
    					}
    					else{
    						// Based on the item category, checks for the presence of optional fields based on type
    						int weight = Integer.parseInt(weightString);
    						String optionalField1 = "";
    						String optionalField2 = "";
    						if(category.equals("groceries")){
    							// Groceries have 7 command fields per line, and it's either perishable or non-perishable
    							if(commands.length == 7){
    								optionalField1 = commands[6];
    								if(!optionalField1.equals("P") && !optionalField1.equals("NP")){
    									System.err.println("Error - Groceries must be marked either \"P\" " + 
    											"for perishable or \"NP\" for non-perishable.\nMoving to next transaction.");
    								}
    								else{
    									// Adds grocery to shopping cart alphabetically and prints success message
    									boolean perishable = false;
    									if(optionalField1.equals("P")){
    										perishable = true;
    									}
    									Grocery g = new Grocery(name, price, weight, quantity, perishable);
    									/*boolean alreadyExists = false;
    						    		for(int i = 0; i < shoppingCart.size(); i++){
    						    			if(shoppingCart.get(i).getName().equals(item.getName())){
    						    				System.err.println("Error - " + item.getName() + " already exists in the " + 
    						    						"shopping cart.\nMoving to next transaction.");
    						    				alreadyExists = true;
    						    			}
    						    		}
    						    		if(!alreadyExists){*/
    						    			insertAlphabetically(g, shoppingCart);
    						    			System.out.println(g.getQuantity() + " " + g.getName() + "(s) were added " + 
    						    					"to the shopping cart.");
    						    		//}
    								}
    							}
    							else{
    								System.err.println("Error - Groceries should be marked either \"P\" for perishable " + 
    										"or \"NP\" for non-perishable.\nMoving to next transaction\n");
    							}
    						}
    						else if(category.equals("electronics")){
    							// Electronics have to have 8 command fields, with the first being fragility status 
    							// fragile or nonfragile, and then what state the good will be shipped to
    							if(commands.length >= 7){
    								optionalField1 = commands[6];
    								if(!optionalField1.equals("F") && !optionalField1.equals("NF")){
    									System.err.println("Error - Electronics should be marked fragile \"F\" or " + 
    											"non-fragile \"NF\".\nMoving to next transaction.");
    								}
    								else{
    									boolean fragile = false;
    									if(optionalField1.equals("F")){
    										fragile = true;
    									}
    									if(commands.length == 8){
    										optionalField2 = commands[7];
    										if(!isState(optionalField2)){
    											System.err.println("Error - The state field is not an " + 
    													"actual state abbreviation.\nMoving to next transaction.");
    										}
    										else{
    											// Adds electronics to the shopping cart and prints success message
    											Electronics e = new Electronics(name, price, quantity, weight, optionalField2, fragile);
    											/*boolean alreadyExists = false;
    								    		for(int i = 0; i < shoppingCart.size(); i++){
    								    			if(shoppingCart.get(i).getName().equals(item.getName())){
    								    				System.err.println("Error - " + item.getName() + " already exists in the " + 
    								    						"shopping cart.\nMoving to next transaction.");
    								    				alreadyExists = true;
    								    			}
    								    		}*/
    								    		//if(!alreadyExists){
    								    			insertAlphabetically(e, shoppingCart);
    								    			System.out.println(e.getQuantity() + " " + e.getName() + "(s) were added " + 
    								    					"to the shopping cart.");
    								    		//}
    										}
    									}
    									else{
    										System.err.println("Error - extraneous information.\nMoving to next transaction.");
    									}
    								}
    							}
    							else{
    								System.err.println("Error, missing or extraneous information.\nMoving to next transaction.");
    							}
    						}
    						else if(category.equals("clothing")){
    							// Only 6 command fields for clothing, otherwise we have extra information
    							if(commands.length > 6){
    								System.err.println("Error, extraneous information.\nMoving to next transaction.");
    							}
    							else{
    								// Adds clothing to shopping cart and prints success message
    								Clothing c = new Clothing(name, price, quantity, weight);
        							/*boolean alreadyExists = false;
        				    		for(int i = 0; i < shoppingCart.size(); i++){
        				    			if(shoppingCart.get(i).getName().equals(item.getName())){
        				    				System.err.println("Error - " + item.getName() + " already exists in the " + 
        				    						"shopping cart.\nMoving to next transaction.");
        				    				alreadyExists = true;
        				    			}
        				    		}
        				    		if(!alreadyExists){*/
        				    			insertAlphabetically(c, shoppingCart);
        				    			System.out.println(c.getQuantity() + " " + c.getName() + "(s) were added " + 
        				    					"to the shopping cart.");
        				    		//}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    /**
     * If delete command is called for, will delete all occurrances of an item from the shopping cart
     * @param commands the command field string array from the input file
     * @param shoppingCart the arraylist containing all items processed
     */
    public static void processDelete(String[] commands, ArrayList<Item> shoppingCart){
    	// Delete commands only have 2 fields, delete, and the name of the item to be deleted
    	if(commands.length != 2){
    		System.err.println("Error - Delete command should be of form <delete> <name of item>.\n" + 
    				"Moving to next transaction.");
    	}
    	else{
    		// Find the item(s) to be deleted in the shopping cart and removes them, updating the total number of 
    		// items deleted
    		int numDeleted = 0;
    		String name = commands[1];
    		boolean itemExists = false;
    		for(int i = 0; i < shoppingCart.size(); i++){
    			if(shoppingCart.get(i).getName().equals(name)){
    				itemExists = true;
    				numDeleted++;
    				shoppingCart.remove(i);
    				i--;
    			}
    		}
    		
    		// If an item is deleted, prints success. Otherwise prints that no items of that name were found in the cart.
    		if(itemExists){
    			System.out.println(numDeleted + " " + name + "(s) were deleted from the shopping cart.");
    		}
    		else{
    			System.err.println("Error - " + name + " is not in shopping cart, so we couldn\'t delete any.");
    		}
    	}
    }
    
    /**
     * If update command is called for, allows for the first item in the shopping cart with the matching name
     * to have its quantity field updated
     * @param commands is the command field string array from the file
     * @param shoppingCart the arraylist of items processed
     */
    public static void processUpdate(String[] commands, ArrayList<Item> shoppingCart){
    	// Update commands have 3 fields to the transaction, update, name of item, and new quantity
    	if(commands.length != 3){
    		System.err.println("Error - Update command should be of form <update> <name of item> " + 
    				"<new quantity>.\nMoving to next transaction.");
    	}
    	else{
    		// Finds first occurrence of the item, if it exists, in the shopping cart
    		String name = commands[1];
    		int index = -1;
    		boolean itemExists = false;
    		for(int i = 0; i < shoppingCart.size(); i++){
    			if(shoppingCart.get(i).getName().equals(name)){
    				itemExists = true;
    				index = i;
    				break;
    			}
    		}
    		
    		// If item exists, changes quantity if the quantity is a non-negative integer and prints a success message
    		// Otherwise error messages for incompatible quantity type or item not found in cart appear
    		if(itemExists){
    			String updatedQuantityResponse = commands[2];
    			if(!updatedQuantityResponse.matches("\\d+")){
    				System.err.println("Error - Quantity must be a non-negative integer.\nMoving to next transaction.");
    			}
    			else{
    				int updatedQuantity = Integer.parseInt(updatedQuantityResponse);
    				shoppingCart.get(index).setQuantity(updatedQuantity);
    				System.out.println("There are now " + updatedQuantity + " " + shoppingCart.get(index).getName() + 
    						"(s) in the shopping cart.");
    			}
    		}
    		else{
    			System.err.println("Error - " + name + " is not in shopping cart, so we couldn\'t update the quantity " + 
    					"without more information.");
    		}
    	}
    }
    
    /**
     * If a search command is called for, searches the shopping cart for all instances of an item with the same name
     * and prints the number of the item to the screen (number of instances, not total quantity)
     * @param commands the string fields from the input transaction line
     * @param shoppingCart the list of items currently available to perform transactions on
     */
    public static void processSearch(String[] commands, ArrayList<Item> shoppingCart){
    	// Search commands are 2 fields, search, and name of item to be searched
    	if(commands.length != 2){
    		System.err.println("Error - Search command should be of the form <search> <item name>.\nMoving " + 
    				"to next transaction.");
    	}
    	else{
    		// Finds every instance of items with the same name as the search query in the cart
    		String name = commands[1];
    		int numItems = 0;
    		for(int i = 0; i < shoppingCart.size(); i++){
    			if(shoppingCart.get(i).getName().equals(name)){
    				numItems++;
    			}
    		}
    		
    		// If the item is found one or more times, print success and the number of times in the cart, otherwise
    		// print an error saying the item was not found in the cart
    		if(numItems == 0){
    			System.out.println("No " + name + "(s) were found in the shopping cart.");
    		}
    		else{
    			System.out.println(numItems + " different instances of " + name + "(s) are " + 
    					"currently in the shopping cart.");
    		}
    	}
    }
    
    /**
     * If a print command is called, then prints statistics on every item in the shopping cart (all instance fields for 
     * every item), and then prints the total price of everything in the shopping cart
     * @param the commands field string array from the input file
     * @param shoppingCart the list of items currently processed and to be printed out
     */
    public static void processPrint(String[] commands, ArrayList<Item> shoppingCart){
    	// Print commands only have one field - print, otherwise we have an invalid transaction line
    	if(commands.length != 1){
    		System.err.println("Error - The print command should be just of form <print> with no extra " + 
    				"information.\nMoving to next transaction.");
    	}
    	else{
    		// Updates total price of the cart as statistics about every item are printed out in 
    		// alphabetical order.
    		double runningTotal = 0.0;
    		for(int i = 0; i < shoppingCart.size(); i++){
    			shoppingCart.get(i).printItemAttributes();
    			runningTotal += shoppingCart.get(i).calculatePrice();
    		}
    		
    		// Prints out total price of the shopping cart
    		System.out.println("The total price of the shopping cart is $" + String.format("%.2f", runningTotal) + ".");
    	}
    }
    
    /**
     * Inserts an item into the shopping cart arraylist alphabetically by name
     * @param item is the item to be inserted into the shopping cart
     * @param shoppingCart the list of items currently processed that will be updated
     */
    public static void insertAlphabetically(Item item, ArrayList<Item> shoppingCart){
    	// Finds index in shoppingCart where the item will be placed, according to lexicographical order
    	int index = 0;
    	for(int i = 0; i < shoppingCart.size(); i++){
    		if(item.getName().compareTo(shoppingCart.get(i).getName()) < 0){
    			index = i;
    		}
    	}
    	
    	// Inserts proper item by proper type into the shopping cart array list
    	if(item instanceof Grocery){
    		shoppingCart.add(index, (Grocery)item);
    	}
    	else if(item instanceof Electronics){
    		shoppingCart.add(index, (Electronics)item);
    	}
    	else if(item instanceof Clothing){
    		shoppingCart.add(index, (Clothing)item);
    	}
    }
    
    /**
     * Given a state input string, checks to see if it is one of 50 state abbreviations in the United States
     * @param state is the input string to be checked if it is a state abbreviation
     * @return true if state is one of 50 state abbreviations, false if not
     */
    public static boolean isState(String state){
    	// State abbreviations can only have 2 letters
    	if(state.length() != 2){
    		return false;
    	}
    	else{
    		// State must match one of the 50 state abbreviations of the United States, by postal abbreviation
    		if(state.equals("AL") || state.equals("AK") || state.equals("AZ") || state.equals("AR") || 
    				state.equals("CA") || state.equals("CO") || state.equals("CT") || state.equals("DE") || 
    				state.equals("FL") || state.equals("GA") || state.equals("HI") || state.equals("ID") || 
    				state.equals("IL") || state.equals("IN") || state.equals("IA") || state.equals("KS") || 
    				state.equals("KY") || state.equals("LA") || state.equals("ME") || state.equals("MD") || 
    				state.equals("MA") || state.equals("MI") || state.equals("MN") || state.equals("MS") || 
    				state.equals("MO") || state.equals("MT") || state.equals("NE") || state.equals("NV") || 
    				state.equals("NH") || state.equals("NJ") || state.equals("NM") || state.equals("NY") || 
    				state.equals("NC") || state.equals("ND") || state.equals("OH") || state.equals("OK") || 
    				state.equals("OR") || state.equals("PA") || state.equals("RI") || state.equals("SC") || 
    				state.equals("SD") || state.equals("TN") || state.equals("TX") || state.equals("UT") || 
    				state.equals("VT") || state.equals("VA") || state.equals("WA") || state.equals("WV") || 
    				state.equals("WI") || state.equals("WY")){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}
    }
}