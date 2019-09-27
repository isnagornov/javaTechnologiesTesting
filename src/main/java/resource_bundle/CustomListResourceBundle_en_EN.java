package resource_bundle;

import java.util.ListResourceBundle;

public class CustomListResourceBundle_en_EN extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"key1", "value1"},
                {"key2", "value2"},
                {"key3", "value3"},
        };
    }
}
