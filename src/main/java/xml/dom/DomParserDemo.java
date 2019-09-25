package xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.Employee;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class DomParserDemo {

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream("xml/employees.xml"));

        List<Employee> employeeList = new ArrayList<>();

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node instanceof Element) {
                Employee employee = new Employee();

                employee.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));

                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);

                    if (childNode instanceof Element) {
                        String content = childNode.getTextContent();

                        switch (childNode.getNodeName()) {
                            case "firstName":
                                employee.setFirstName(content);
                                break;
                            case "lastName":
                                employee.setLastName(content);
                                break;
                            case "middleName":
                                employee.setMiddleName(content);
                                break;
                        }
                    }
                }

                employeeList.add(employee);

            }

        }

        employeeList.forEach(System.out::println);
    }
}
