package xml.stax;

import xml.Employee;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.stream.XMLStreamConstants.*;

public class StaxParserDemo {

    public static void main(String[] args) throws XMLStreamException {

        List<Employee> employeeList = null;
        Employee currentEmployee = null;
        String currentTagContent = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        XMLStreamReader xmlEventReader = xmlInputFactory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream("xml/employees.xml"));

        while (xmlEventReader.hasNext()) {

            int event = xmlEventReader.next();

            switch (event) {
                case START_DOCUMENT:
                    employeeList = new ArrayList<>();
                    break;
                case START_ELEMENT:
                    if ("employee".equals(xmlEventReader.getLocalName())) {
                        currentEmployee = new Employee();
                        currentEmployee.setId(Long.valueOf(xmlEventReader.getAttributeValue(0)));
                    }
                    break;
                case END_ELEMENT:
                    switch (xmlEventReader.getLocalName()) {
                        case "employee":
                            if (employeeList == null) {
                                employeeList = new ArrayList<>();
                            }
                            employeeList.add(currentEmployee);
                            break;
                        case "firstName":
                            currentEmployee.setFirstName(currentTagContent);
                            break;
                        case "middleName":
                            currentEmployee.setMiddleName(currentTagContent);
                            break;
                        case "lastName":
                            currentEmployee.setLastName(currentTagContent);
                            break;
                    }
                    break;
                case CHARACTERS:
                    currentTagContent = xmlEventReader.getText();
                    break;
            }


        }


        employeeList.forEach(System.out::println);

    }
}
