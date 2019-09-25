package xml.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

public class XpathDemo {

    public static void main(String[] args) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(Boolean.TRUE);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(ClassLoader.getSystemResourceAsStream("xml/employees.xml"));

        XPathFactory xPathFactory = XPathFactory.newInstance();

        XPath xPath = xPathFactory.newXPath();

        XPathExpression xPathExpression = xPath.compile("employees//employee[lastName='lastName2']/@id");

        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

        Long foundEmployeeId = Long.valueOf(String.valueOf(nodeList.item(0).getNodeValue()));

        System.out.println("Query result - " + foundEmployeeId);

    }
}
