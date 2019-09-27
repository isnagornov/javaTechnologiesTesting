package resource_bundle;

import java.util.Locale;
import java.util.ResourceBundle;

// to work with utf-8 values encoding set in IDEA "Transparent native-to-ASCII conversion" to TRUE
public class ResourceBundleExample {

    public static void main(String[] args) {

        System.out.println("Current Locale - " + Locale.getDefault());

        ResourceBundle defResourceBundle = ResourceBundle.getBundle("messages");
        ResourceBundle ruResourceBundle = ResourceBundle.getBundle("messages_ru_RU");
        ResourceBundle enResourceBundle = ResourceBundle.getBundle("messages_en_EN");

        System.out.println("-----------------Getting values from properties bundle");

        System.out.println("Message via default locale - " + defResourceBundle.getString("test-message"));
        System.out.println("Message via ru locale - " + ruResourceBundle.getString("test-message"));
        System.out.println("Message via ru locale - " + enResourceBundle.getString("test-message"));

        System.out.println();

        System.out.println("-----------------Getting values from java bundle");

        ResourceBundle listResourceBundle = ResourceBundle.getBundle("resource_bundle.CustomListResourceBundle");
        ResourceBundle listResourceBundleRU = ResourceBundle.getBundle("resource_bundle.CustomListResourceBundle_ru_RU");
        ResourceBundle listResourceBundleEN = ResourceBundle.getBundle("resource_bundle.CustomListResourceBundle_en_EN");

        System.out.println("Message via default locale - " + listResourceBundle.getString("key1"));
        System.out.println("Message via ru locale - " + listResourceBundleRU.getString("key1"));
        System.out.println("Message via ru locale - " + listResourceBundleEN.getString("key1"));


    }
}
