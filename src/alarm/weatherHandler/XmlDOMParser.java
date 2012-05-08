package alarm.weatherHandler;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.transform.RegistryMatcher;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dwml.simple.Dwml;
import dwml.simple.myXMLDate;

import alarm.jack.LFnC;
import android.util.Log;
import android.util.Pair;

/*
 *
 */
public class XmlDOMParser{

    private static String tag = "XmlDOMParser";
    private InputStream mIs;

    /*
     * class constructor, idk why I made this a class =/
     * @param   is  an InputStream that the xmlDOMParser will parse
     */
    public XmlDOMParser(InputStream is){
        Log.d(tag, "constructor: InputStream("+is.toString()+")");
        mIs = is;
    }

    /*inspects the xml file and parses for my Weather speaking alarm clock
    @return     returns a hashmap with the key's being the type of weather data, and values of an arraylist containing pair<timeRange, weatherInfo>
    */
    public HashMap<String, ArrayList<Pair<TimeRange, Integer>>> Parse() throws SAXException, IOException{
        Log.d(tag, "Parse");
        HashMap<String, ArrayList<Pair<TimeRange, Integer>>> retMap = new HashMap<String, ArrayList<Pair<TimeRange, Integer>>>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        RegistryMatcher rm = new RegistryMatcher();
        rm.bind(Date.class, myXMLDate.class);
        Serializer serializer = new Persister(rm);

        DocumentBuilder db = null;
        Dwml mDwml = null;
        try{
            mDwml = serializer.read(Dwml.class, mIs);
            db = dbf.newDocumentBuilder();
        }
        catch(ParserConfigurationException e){
            Log.d(tag, e.toString());
        }catch (Exception e1) {
            e1.printStackTrace();
        }

        Document doc =null;
        if(mIs != null) doc = db.parse(mIs);
        else Log.d(tag, "error in XMLDOMParser, inputsource is null");

        NodeList timeLayouts = doc.getElementsByTagName(LFnC.timeLayout_tag);
        HashMap<String, ArrayList<TimeRange>> keyTimeRangeMap = new HashMap<String, ArrayList<TimeRange>>();

        //setup arrayList of time-keys and time-ranges
        for(int i=0; i<timeLayouts.getLength(); i++){
            NodeList timeLayoutChildren = timeLayouts.item(i).getChildNodes();
            Element layoutKey = (Element)timeLayoutChildren.item(1);
            ArrayList<TimeRange> times = new ArrayList<TimeRange>();
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-d'T'HH:mm:ssz");
            for(int k=2; k<timeLayoutChildren.getLength(); k++){
                if(timeLayoutChildren.item(k).getNodeType()==Node.ELEMENT_NODE)
                try {
                    TimeRange timeRange = new TimeRange();
                    if(timeLayoutChildren.item(k).getNodeName().equals(LFnC.startTime_nodeName)){
                        Date date = parser.parse(timeLayoutChildren.item(k).getTextContent());
                        timeRange.setStartDate(date);
                        k=k+2;
                        if(timeLayoutChildren.item(k).getNodeName().equals(LFnC.endTime_nodeName)){
                            date = parser.parse(timeLayoutChildren.item(k).getTextContent());
                            timeRange.setEndDate(date);
                        }
                        times.add(timeRange);
                    }

                } catch (DOMException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            keyTimeRangeMap.put(layoutKey.getChildNodes().item(0).getNodeValue(), times);
        }

        NodeList temperatureList = doc.getElementsByTagName(LFnC.hourly_temp_tag);
        for(int i=0; i<temperatureList.getLength(); i++){
            Element tempItem = (Element)temperatureList.item(i);
            //Log.d(tag, "temperature tag has attributes: "+tempItem.hasAttributes());
            String tempType = tempItem.getAttribute("type");

            //if the temperature node is of type hourly, construct a arrayList of pairs of temps and timeRanges, then add to retMap
            if(tempType.compareTo("hourly")==0){
                String timeKey = tempItem.getAttribute(LFnC.timeLayout_tag);
                ArrayList<Pair<TimeRange, Integer>> timeTempPairList = new ArrayList<Pair<TimeRange, Integer>>();
                ArrayList<TimeRange> tempTimeRange = keyTimeRangeMap.get(timeKey);
                NodeList hourlyTempNL = tempItem.getChildNodes();
                for(int k=0; k<hourlyTempNL.getLength();k++){
                    int l=0;
                    if(hourlyTempNL.item(k).getNodeName().compareTo(LFnC.temp_value_tag)==0){
                        String nv = hourlyTempNL.item(k).getFirstChild().getNodeValue();
                        timeTempPairList.add(new Pair<TimeRange, Integer>(tempTimeRange.get(k), Integer.getInteger(nv)));
                    }
                }
                retMap.put(LFnC.myMap_hourly_temp_key, timeTempPairList);
//                Log.d(tag, "temp type is: "+tempType);
//                tempItem.getElementsByTagName(LFnC.temp_value_tag);
            }
        }
        return retMap;
    }
}
