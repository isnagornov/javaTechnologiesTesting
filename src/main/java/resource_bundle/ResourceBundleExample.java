package resource_bundle;

import java.util.Locale;
import java.util.ResourceBundle;

// to work with utf-8 values encoding set in IDEA "Transparent native-to-ASCII conversion" to TRUE
public class ResourceBundleExample {

    public static void main(String[] args) {

        Locale ruLocale = new Locale("ru", "RU");
        Locale enLocale = new Locale("en", "EN");

        System.out.println("Current Locale - " + Locale.getDefault());

        ResourceBundle propBundle = ResourceBundle.getBundle("messages");

        System.out.println("-----------------Getting values from properties bundle");

        System.out.println("Message via default locale - " + propBundle.getString("test-message"));

        System.out.println("Message via ru locale - " + propBundle.getString("test-message"));

        Locale.setDefault(enLocale);
        propBundle = ResourceBundle.getBundle("messages");

        System.out.println("Message via en locale - " + propBundle.getString("test-message"));

        //get value that exist only in common messages.properties
        System.out.println("Message from common messages - " + propBundle.getString("common-message"));

        System.out.println();

        System.out.println("-----------------Getting values from java bundle");

        ResourceBundle listResourceBundleRU = ResourceBundle.getBundle("resource_bundle.CustomListResourceBundle_ru_RU");
        ResourceBundle listResourceBundleEN = ResourceBundle.getBundle("resource_bundle.CustomListResourceBundle_en_EN");

        System.out.println("Message via ru locale - " + listResourceBundleRU.getString("key1"));
        System.out.println("Message via en locale - " + listResourceBundleEN.getString("key1"));

    }
}
