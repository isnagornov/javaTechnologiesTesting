package resource_bundle;

import java.util.ListResourceBundle;

public class CustomListResourceBundle_ru_RU extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"key1", "значение1"},
                {"key2", "значение2"},
                {"key3", "значение3"},
        };
    }
}
