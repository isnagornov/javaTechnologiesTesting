package collections.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TableTesting {

    public static void main(String[] args) {
        Table<String, String, Integer> universityCourseSeatTable = initTable();
        System.out.println(universityCourseSeatTable);

        int seatCount = universityCourseSeatTable.get("Mumbai", "IT");
        Integer seatCountForNoEntry = universityCourseSeatTable.get("Oxford", "IT");

        assertEquals(seatCount, 60);
        assertNull(seatCountForNoEntry);

        boolean entryIsPresent = universityCourseSeatTable.contains("Mumbai", "IT");
        boolean courseIsPresent = universityCourseSeatTable.containsColumn("IT");
        boolean universityIsPresent = universityCourseSeatTable.containsRow("Mumbai");
        boolean seatCountIsPresent = universityCourseSeatTable.containsValue(60);

        assertTrue(entryIsPresent);
        assertTrue(courseIsPresent);
        assertTrue(universityIsPresent);
        assertTrue(seatCountIsPresent);

        seatCount = universityCourseSeatTable.remove("Mumbai", "IT");
        assertEquals(seatCount, 60);
        assertNull(universityCourseSeatTable.get("Mumbai", "IT"));
        System.out.println(universityCourseSeatTable);
        System.out.println("--------------------------------------");

        universityCourseSeatTable = initTable();
        System.out.println(universityCourseSeatTable);

        Map<String, Integer> universitySeatMap = universityCourseSeatTable.column("IT");

        assertTrue(universitySeatMap.size() == 2);
        assertEquals(Integer.valueOf(60), universitySeatMap.get("Mumbai"));
        assertEquals(Integer.valueOf(120), universitySeatMap.get("Harvard"));

        Map<String, Map<String, Integer>> courseKeyUniversitySeatMap = universityCourseSeatTable.columnMap();
        System.out.println(courseKeyUniversitySeatMap);

        Map<String, Integer> courseSeatMap = universityCourseSeatTable.row("Mumbai");
        System.out.println(courseSeatMap);

        Set<String> universitySet = universityCourseSeatTable.rowKeySet();
        System.out.println(universitySet);

        Set<String> courseKeySet = universityCourseSeatTable.columnKeySet();
        System.out.println(courseKeySet);

    }

    private static Table<String, String, Integer> initTable() {
        Table<String, String, Integer> universityCourseSeatTable
                = HashBasedTable.create();
        universityCourseSeatTable.put("Mumbai", "Chemical", 120);
        universityCourseSeatTable.put("Mumbai", "IT", 60);
        universityCourseSeatTable.put("Harvard", "Electrical", 60);
        universityCourseSeatTable.put("Harvard", "IT", 120);

        return universityCourseSeatTable;
    }
}
