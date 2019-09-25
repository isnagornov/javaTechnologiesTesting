package xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import xml.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSAXHandler extends DefaultHandler {

    private List<Employee> employeeList;
    private Employee currentEmployee;
    private String currentTagContent;

    public EmployeeSAXHandler() {
        employeeList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("employee".equals(qName)) {
            currentEmployee = new Employee();
            currentEmployee.setId(Long.valueOf(attributes.getValue("id")));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        switch (qName) {
            case "employee":
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
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        currentTagContent = String.copyValueOf(ch, start, length);
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }
}
