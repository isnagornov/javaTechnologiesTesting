package ext_inc;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ExistingIncomingFilterTest {

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {

//        testFilterInteger();
        testFilterString();

    }

    private static void testFilterInteger() {
        List<Integer> incoming = new ArrayList<>();
        List<Integer> existing = new ArrayList<>();
        List<Integer> newRoles;
        List<Integer> rolesToRemove;
        List<Integer> commonRoles;

//        existing.add(1);
//        existing.add(2);
//        existing.add(3);
        existing.add(4);
        existing.add(5);

        incoming.add(1);
        incoming.add(2);
        incoming.add(3);
        incoming.add(4);
        incoming.add(5);

        newRoles = new ArrayList<>(incoming);
        newRoles.removeAll(existing);
        rolesToRemove = new ArrayList<>(existing);
        rolesToRemove.removeAll(incoming);
        commonRoles = new ArrayList<>(existing);
        commonRoles.retainAll(incoming);

        System.out.println("existing - " + existing.toString());
        System.out.println();
        System.out.println("incoming - " + incoming.toString());
        System.out.println();
        System.out.println("newRoles - " + newRoles.toString());
        System.out.println();
        System.out.println("rolesToRemove - " + rolesToRemove.toString());
        System.out.println();
        System.out.println("commonRoles - " + commonRoles.toString());
    }

    private static void testFilterString() {
        List<String> incoming = new ArrayList<>();
        List<String> existing = new ArrayList<>();
        List<String> newRoles;
        List<String> rolesToRemove;
        List<String> commonRoles;

        existing.add("1");
        existing.add("2");
        existing.add("3");
//        existing.add("4");
//        existing.add("5");

        incoming.add("1");
        incoming.add("2");
        incoming.add("3");
        incoming.add("4");
        incoming.add("5");

        newRoles = new ArrayList<>(incoming);
        newRoles.removeAll(existing);
        rolesToRemove = new ArrayList<>(existing);
        rolesToRemove.removeAll(incoming);
        commonRoles = new ArrayList<>(existing);
        commonRoles.retainAll(incoming);

        System.out.println("existing - " + existing.toString());
        System.out.println();
        System.out.println("incoming - " + incoming.toString());
        System.out.println();
        System.out.println("newRoles - " + newRoles.toString());
        System.out.println();
        System.out.println("rolesToRemove - " + rolesToRemove.toString());
        System.out.println();
        System.out.println("commonRoles - " + commonRoles.toString());

    }

}
