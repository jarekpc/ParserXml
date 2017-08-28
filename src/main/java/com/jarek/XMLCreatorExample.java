package com.jarek;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLCreatorExample {

    List<Employee> myEmpls =  new ArrayList<Employee>();
    Document dom;

    XMLCreatorExample(){
        myEmpls.add(new Employee(1,"Aaa",12,"bb"));
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
        //get an instance of factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create an instance of DOM
            dom = db.newDocument();

        }catch(ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }
    }

    private Element createEmployeeElement(Employee e){
        Element bookEle = dom.createElement("Employee");
        bookEle.setAttribute("Type", e.getType());

        Element bName = dom.createElement("Name");
        Text bNemaText = dom.createTextNode(e.getName());
        bName.appendChild(bNemaText);
        bookEle.appendChild(bName);
        //create author element and author text node and attach it to bookElement
        Element authEle = dom.createElement("Type");
        Text authText = dom.createTextNode(e.getType());
        authEle.appendChild(authText);
        bookEle.appendChild(authEle);

        //create title element and title text node and attach it to bookElement
        /*
        Element titleEle = dom.createElement("Title");
        Text titleText = dom.createTextNode(b.getTitle());
        titleEle.appendChild(titleText);
        bookEle.appendChild(titleEle);
        */
        return bookEle;

    }

    private void printToFile(){

        try
        {
            //print
            OutputFormat format = new OutputFormat(dom);
            format.setIndenting(true);

            //to generate output to console use this serializer
            //XMLSerializer serializer = new XMLSerializer(System.out, format);


            //to generate a file output use fileoutputstream instead of system.out
            XMLSerializer serializer = new XMLSerializer(
                    new FileOutputStream(new File("book.xml")), format);

            serializer.serialize(dom);

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

}
