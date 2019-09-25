package xml.sax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParserDemo {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        SAXParser saxParser = saxParserFactory.newSAXParser();

        EmployeeSAXHandler employeeSAXHandler = new EmployeeSAXHandler();

        saxParser.parse(ClassLoader.getSystemResourceAsStream("xml/employees.xml"), employeeSAXHandler);

        employeeSAXHandler.getEmployeeList().forEach(System.out::println);

    }
}
