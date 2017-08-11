package Gestion;

import java.io.IOException;
import Servlets.Useless;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyManagement {

    Useless prop = new Useless();

    public static void writer(String key, String value) throws FileNotFoundException, IOException {
        Useless us = new Useless();

        String path = us.getClass().getResource("conf.properties").getPath();
        FileInputStream in = new FileInputStream(path);
        Properties prop = new Properties();
        prop.load(in);
        in.close();
        OutputStream output = null;
        
        try {

            output = new FileOutputStream(path);

            // set the properties value
            prop.setProperty(key, value);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String reader(String key) {
        
        Useless us = new Useless();
        Properties prop = new Properties();
        InputStream input = null;
        String path = us.getClass().getResource("conf.properties").getPath();
        
        try {

            input = new FileInputStream(path);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return (prop.getProperty(key));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
