package dwml.simple;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

@Element
public class Temperature {
    @Attribute
    public String type;
    @Attribute
    public String units;

    @Attribute(name="time-layout")
    public String time_layout_key;

    @Element
    public String name;

    @ElementList(entry="value", inline=true)
    public List<Integer> values;

}
