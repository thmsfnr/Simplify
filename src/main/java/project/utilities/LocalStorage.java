package project.utilities;

import project.business.models.Meal;
import project.business.models.Restaurant;
import project.business.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalStorage {
    public static void write(String key,Object value) throws IOException {
        File file;
        // write the user in the file
        if (key.equals("user_id")){
            file = new File(key+"LS.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            User user = (User) value;
            bw.write(key+"="+user.getId().toString());
            // close the file
            bw.close();
        }
        else if (key.equals("meal_id")){
            file = new File(key+"LS.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            int idMeal = (Integer) value;
            bw.write(key+"="+idMeal);
            // close the file
            bw.close();
        }
        else if (key.equals("restaurant_id")){
            file = new File(key+"LS.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            int restaurant = (Integer) value;
            bw.write(key+"="+restaurant);
            // close the file
            bw.close();
        }
        else if(key.equals("isUpdate")){
            file = new File(key+"LS.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            Boolean isUpdate = (Boolean) value;
            bw.write(key+"="+isUpdate);
            // close the file
            bw.close();
        }
    }
    /**
     * This method is used to get the user in the local storage
     * @return the user
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
            System.out.println(line);
            if (line.contains("user_id")){
               return Integer.parseInt(line.substring(8));
            }
            else if (line.contains("restaurant_id")){
                return Integer.parseInt(line.substring(14));
            }
            else if (line.contains("meal_id")){
                return Integer.parseInt(line.substring(8));
            }
            else if (line.contains("isUpdate")){
               return Boolean.parseBoolean(line.substring(9));
            }
        }
        else {
            throw new IOException("The file is empty");
        }
        reader.close();
        return null;
    }
}
