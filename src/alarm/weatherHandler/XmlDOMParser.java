package alarm.weatherHandler;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlDOMParser {

    public void Parse() throws SAXException, IOException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try{
            db = dbf.newDocumentBuilder();
        }
        catch(ParserConfigurationException e){
            System.out.println(e.toString());
        }

        String filename="";
        Document doc = db.parse(new File(filename));

    }
}
