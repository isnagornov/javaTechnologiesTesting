package xml.jaxb;

import xml.Employee;
import xml.EmployeeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.Arrays;

public class JaxbParserDemo {

    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeList.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        EmployeeList employeeListToMarshal = new EmployeeList();
        Employee employee1 = new Employee(1L, "fn1", "mn1", "ln1");
        Employee employee2 = new Employee(2L, "fn2", "mn2", "ln2");
        Employee employee3 = new Employee(3L, "fn3", "mn3", "ln3");

        employeeListToMarshal.setEmployees(Arrays.asList(employee1, employee2, employee3));

        marshaller.marshal(employeeListToMarshal, System.out);

        EmployeeList employeeList = (EmployeeList) unmarshaller.unmarshal(ClassLoader.getSystemResourceAsStream("xml/employees.xml"));

        employeeList.getEmployees().forEach(System.out::println);

    }
}
