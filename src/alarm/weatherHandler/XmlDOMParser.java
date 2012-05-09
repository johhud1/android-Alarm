package alarm.weatherHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.xml.sax.SAXException;

import android.util.Log;
import dwml.simple.Dwml;
import dwml.simple.myXMLDate;



/*
 *
 */
public class XmlDOMParser {

    private static String tag = "XmlDOMParser";
    private InputStream mIs;


    /*
     * class constructor, idk why I made this a class =/
     * 
     * @param is an InputStream that the xmlDOMParser will parse
     */
    public XmlDOMParser(InputStream is) {
        Log.d(tag, "constructor: InputStream(" + is.toString() + ")");
        mIs = is;
    }


    /*
     * inspects the xml file and parses for my Weather speaking alarm clock
     * 
     * @return returns a hashmap with the key's being the type of weather data,
     * and values of an arraylist containing pair<timeRange, weatherInfo>
     */
    public Dwml Parse() throws SAXException, IOException {
        Log.d(tag, "Parse");

        RegistryMatcher rm = new RegistryMatcher();
        rm.bind(Date.class, myXMLDate.class);
        Serializer serializer = new Persister(rm);

        Dwml mDwml = null;
        try {
            mDwml = serializer.read(Dwml.class, mIs);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return mDwml;
    }
}
