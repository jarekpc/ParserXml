package com.jarek;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XMLCreatorExample {

    private List<Employee> myEmpls;// =  new ArrayList<Employee>();
    private Document dom;
    private Logger logger = LoggerFactory.getLogger(XMLCreatorExample.class);
    DomParser domParser = new DomParser();

    XMLCreatorExample(){
        myEmpls = domParser.getMyEmpls();
        createDocument();
        createDOMTree();
        printToFile();
    }

    private void createDOMTree(){
        Element rootEle = dom.createElement("Personnel");
        dom.appendChild(rootEle);

        for(Employee e: myEmpls){
            Element elEmployee = createEmployeeElement(e);
            rootEle.appendChild(elEmployee);
        }
    }

    void createDocument() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

        }catch(ParserConfigurationException pce) {
            logger.warn("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }
    }

    private int getIntValue(Element ele, String tagName) {
        try {
            return Integer.parseInt(getTextValue(ele,tagName));
        }catch (NumberFormatException n){
            logger.warn("NumberFormatException from getIntValue");
            n.fillInStackTrace();
            return 0;
        }
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    private Element createEmployeeElement(Employee e){
        Element emplEle = dom.createElement("Employee");
        //
        Element bName = dom.createElement("Name");
        Text bNemaText = dom.createTextNode(e.getName());
        bName.appendChild(bNemaText);
        emplEle.appendChild(bName);
        //
        Element bId = dom.createElement("Id");
        Text iId = dom.createTextNode(new Integer(e.getId()).toString());
        bId.appendChild(iId);
        emplEle.appendChild(bId);
        //
        Element bAge = dom.createElement("Age");
        Text iAge = dom.createTextNode(new Integer(e.getAge()).toString());
        bAge.appendChild(iAge);
        emplEle.appendChild(bAge);
        
        return emplEle;

    }

    private void printToFile(){

        try
        {
            OutputFormat format = new OutputFormat(dom);
            format.setIndenting(true);

            XMLSerializer serializer = new XMLSerializer(
                    new FileOutputStream(new File("src/main/resources/export.xml")), format);

            serializer.serialize(dom);

        } catch(IOException ie) {
            logger.warn("IOException from printToFile");
            ie.printStackTrace();
        }
    }

}
