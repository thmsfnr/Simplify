package project.presentation.controller;

import project.business.models.User;

import java.io.*;

public class Localstorage {

    private static String filename = "localstorage.txt";

    public static void write(User user) throws IOException {

        // open the file
        File file = new File(filename);
        // if the file doesn't exist create it
        if (!file.exists()) {
            file.createNewFile();
        }
        // write the user informations in the file
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        // write the user in the file
        bw.write(user.getId().toString());

        // close the file
        bw.close();
    }

    public static int load() throws IOException {
        // open the file localstorage.txt
        File file = new File(filename);

        // if the file doesn't exist create it
        if (!file.exists()) {
            file.createNewFile();
            throw new IOException("The file doesn't exist");
        }

        // read the file
        Reader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        String line = reader.readLine();
        reader.close();

        int id;
        // get the user
        if (line != null) {
            id = Integer.parseInt(line);
        } else {
            throw new IOException("The file is empty");
        }
        return id;
    }
}
