package dwml.simple;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name="time-layout")
public class TimeLayout {

    @Attribute(name="time-coordinate")
    public String time_coord;

    @Attribute
    public String summarization;

    @Element(name="layout-key")
    public String layout_key;

    @ElementList(entry="start-valid-time", inline=true)
    public List<Date> start_times;

    //@Convert (myXMLDate.class)

    @ElementList(entry="end-valid-time", required=false, inline=true)
    public List<Date> end_times;
}
