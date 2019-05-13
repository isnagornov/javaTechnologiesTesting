package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    public static void main(String[] args) {
        String text = "Егор Алла Александр";

        System.out.println("жадный алгоритм");

        Pattern pattern1 = Pattern.compile("А.+а");
        Matcher matcher = pattern1.matcher(text);
        while (matcher.find()) {
            System.out.println(text.substring(matcher.start(), matcher.end()));
        }

        System.out.println();
        System.out.println("сверхжадный алгоритм");

        Pattern pattern3 = Pattern.compile("А.++а");
        matcher = pattern3.matcher(text);
        while (matcher.find()) {
            System.out.println(text.substring(matcher.start(), matcher.end()));
        }

        System.out.println();
        System.out.println("ленивый алгоритм");

        Pattern pattern2 = Pattern.compile("А.+?а");
        matcher = pattern2.matcher(text);
        while (matcher.find()) {
            System.out.println(text.substring(matcher.start(), matcher.end()));
        }

        System.out.println();
        System.out.println("Pattern.matches");

        String numberAndWordString = "1234 Test 56789";
        System.out.println(Pattern.matches("\\d{4}\\s\\w*\\s\\d*", numberAndWordString));

    }
}
