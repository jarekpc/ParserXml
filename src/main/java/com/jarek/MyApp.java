package com.jarek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyApp {

    Document dom;
    List<Employee> myEmpls;
    Logger logger = LoggerFactory.getLogger(MyApp.class);


    MyApp(){
        myEmpls = new ArrayList<Employee>();
    }

    private void parseXmlFile(){
        logger.info("ParseXmlFile");
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("employees.xml").getFile());
        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse(file);


        }catch(ParserConfigurationException pce) {
            pce.printStackTrace();
            logger.error("ParserConfigurationException");
        }catch(SAXException se) {
            se.printStackTrace();
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private void parseDocument(){
        Element docEle = dom.getDocumentElement();
        //get a nodelist of <employee> elements
        NodeList nl = docEle.getElementsByTagName("Employee");
        Employee myEmployee = new Employee(5,"Jarek",37, "contract");
        myEmpls.add(myEmployee);
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {

                //get the employee element
                Element el = (Element)nl.item(i);

                //get the Employee object
                Employee e = getEmployee(el);

                //add it to list
                myEmpls.add(e);
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
        return Integer.parseInt(getTextValue(ele,tagName));
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
        for(Employee e: myEmpls){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        MyApp myApp =  new MyApp();
        myApp.parseXmlFile();
        myApp.parseDocument();
        myApp.printData();
        //
        XMLCreatorExample xmlCreatorExample = new XMLCreatorExample();


    }
}
