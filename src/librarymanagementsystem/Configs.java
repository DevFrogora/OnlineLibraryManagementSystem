/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author root
 */
public class Configs {

    public static Properties prop = new Properties();

    public void storeProp(String filename, String key, String value) {

        try {
            prop.setProperty(key, value);
            prop.store(new FileOutputStream(filename), null);
        } catch (IOException e) {

        }
    }

    public String getProp(String filename, String key) {
        String value = "";
        try {
            prop.load(new FileInputStream(filename));
            value = prop.getProperty(key);

        } catch (IOException e) {

        }
        return value;
    }

}
