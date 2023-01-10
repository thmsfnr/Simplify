package project.utilities;

import project.business.facade.MealFacade;
import project.business.models.Meal;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is used to save the cart in the file and to load it
 */
public class CartStorage {
    /**
     * This method is used to save the cart in a file
     *
     * @param key   the string which indicates the variable
     * @param value the value of the variable
     * @return
     * @throws IOException the exception
     */
    public static void write(String key, Object value) throws IOException {
        File file;
        // write the user in the file
        if(key.equals("cartStorage")){
            file = new File(key+"LS.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            String cartStorage = (String) value;
            bw.write(cartStorage);
            bw.close();
        }
    }
    /**
     * This method is used to get the meals and theirs quatities stored in the file
     * @return the value of the variable
     * @throws IOException if the file is not found
     */
    public static Object load(String key) throws IOException {
        // open the file localstorage.txt
        File file = new File(key+"LS.txt");

        // if the file doesn't exist create it
        if (!file.exists()) {
            file.createNewFile();
            throw new IOException("The file doesn't exist");
        }

        // read the file
        Reader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        String line =  reader.readLine();
        if(line != null) {
            if(line.contains("cart_meal")){
                //Split the string on the spaces
                String[] cartItems = line.split("\\s+");
                Map<Meal, Integer> cartMap = new HashMap<>();

                for (String cartItem : cartItems) {
                    //Split the string on the comma
                    String[] cartItemDetails = cartItem.split(",");
                    //Get the meal id
                    int mealId = Integer.parseInt(cartItemDetails[0].split("=")[1]);
                    //Get the meal quantity
                    int mealQte = Integer.parseInt(cartItemDetails[1].split("=")[1]);
                    //Get the meal object
                    MealFacade mealFacade = MealFacade.getInstance();
                    Meal meal = mealFacade.getById(mealId);
                    //Add the meal and quantity to the map
                    cartMap.put(meal, mealQte);
                }
                    return cartMap;
            }
        }
        else {
            throw new IOException("The file is empty");
        }
        reader.close();
        return null;
    }
}
