package collections.apache_commons;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.TransformerUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapUtilsTest {

    public static void main(String[] args) {
        String[][] color2DArray = new String[][]{
                {"RED", "#FF0000"},
                {"GREEN", "#00FF00"},
                {"BLUE", "#0000FF"}
        };
        String[] color1DArray = new String[]{
                "RED", "#FF0000",
                "GREEN", "#00FF00",
                "BLUE", "#0000FF"
        };

        Map<String, String> colorMap = MapUtils.putAll(
                new HashMap<>(), color2DArray);

        assertThat(colorMap.size(), is(color2DArray.length));

        assertTrue("#FF0000".equals(colorMap.get("RED")));
        assertTrue("#00FF00".equals(colorMap.get("GREEN")));
        assertTrue("#0000FF".equals(colorMap.get("BLUE")));

        colorMap = MapUtils.putAll(new HashMap<>(), color1DArray);

        assertThat(colorMap.size(), is(color1DArray.length / 2));


        assertTrue("#FF0000".equals(colorMap.get("RED")));
        assertTrue("#00FF00".equals(colorMap.get("GREEN")));
        assertTrue("#0000FF".equals(colorMap.get("BLUE")));

        MapUtils.verbosePrint(System.out, "colorMap:", colorMap);

        String defaultColorStr = "COLOR_NOT_FOUND";
        String color = MapUtils
                .getString(colorMap, "BLACK", defaultColorStr);

        assertEquals(color, defaultColorStr);

        color = MapUtils.getString(null, "RED", defaultColorStr);

        assertEquals(color, defaultColorStr);

        Map<String, String> invColorMap = MapUtils.invertMap(colorMap);

        MapUtils.verbosePrint(System.out, "invColorMap:", invColorMap);

        try {
            colorMap = Map.of("RED", "#FF0000", "RED2", "#FF0000", "GREEN", "#00FF00",
                    "BLUE", "#0000FF");
            MapUtils.predicatedMap(colorMap, null, PredicateUtils.uniquePredicate());
        } catch (IllegalArgumentException e) {
            System.out.println("It's normal behavior");
        }

        colorMap = Map.of("RED", "#FF0000", "GREEN", "#00FF00",
                "BLUE", "#0000FF");
        Map<String, String> uniqValuesMap = MapUtils.predicatedMap(colorMap, null, PredicateUtils.uniquePredicate());

        MapUtils.verbosePrint(System.out, "uniqValuesMap:", uniqValuesMap);

        Map<Integer, String> intStrMap = MapUtils.lazyMap(
                new HashMap<>(),
                TransformerUtils.stringValueTransformer());

        assertTrue(intStrMap.isEmpty());

        intStrMap.get(1);
        intStrMap.get(2);
        intStrMap.get(3);

        assertTrue(intStrMap.size() == 3);


    }
}
