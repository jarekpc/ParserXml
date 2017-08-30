package com.jarek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    private Document dom;
    private List<Employee> myEmpls;
    private Logger logger = LoggerFactory.getLogger(DomParser.class);


    public List<Employee> getMyEmpls() {
        return myEmpls;
    }


    public void setMyEmpls(List<Employee> myEmpls) {
        this.myEmpls = myEmpls;
    }


    DomParser(){
        myEmpls = new ArrayList<>();
        parseXmlFile();
        parseDocument();

    }
    //
    public boolean validateXMLSchema(String xsdPath, String xmlPath){
        System.out.println("Uruchomienie ");
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            logger.warn("PIOException | SAXException");
            return false;
        }
        return true;
    }

    private void parseXmlFile(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("employees.xml").getFile());

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(file);
            //validation xml
            System.out.println("employee.xml validates against Employee.xsd? "+validateXMLSchema("src/main/resources/Employee.xsd", "src/main/resources/employees.xml"));

        }catch(ParserConfigurationException pce) {
            logger.warn("ParserConfigurationException");
            pce.printStackTrace();
        }catch(SAXException se) {
            logger.warn("SAXException ");
            se.printStackTrace();
        }catch(IOException ioe) {
            logger.warn("IOException");
            ioe.printStackTrace();
        }



    }

    private void parseDocument(){
        Element docEle = dom.getDocumentElement();
        NodeList nl = docEle.getElementsByTagName("Employee");
        List<Employee> l = new ArrayList<Employee>();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {
                Element el = (Element)nl.item(i);
                Employee e = getEmployee(el);
                l.add(e);
                myEmpls.add(e);
                //this.setMyEmpls(l);
            }
        }
    }

    private Employee getEmployee(Element emplEl){
        String name = getTextValue(emplEl,"Name");
        int id = getIntValue(emplEl,"Id");
        int age = getIntValue(emplEl,"Age");
        String type = emplEl.getAttribute("type");

        Employee e = new Employee(id,name,age,type);
        return e;
    }

    private int getIntValue(Element ele, String tagName){
        try {
            return Integer.parseInt(getTextValue(ele, tagName));
        }catch (NumberFormatException n){
            logger.warn("NumberFormatException from getIntValue");
            n.fillInStackTrace();
        }
        return 0;
    }

    private String getTextValue(Element ele, String tagName){
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0){
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    private void printData(){
        System.out.println(myEmpls.size());
        /*
        for(Employee e: myEmpls){
            System.out.println(e);
        }
        */

    }

}
