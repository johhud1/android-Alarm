package dwml.simple;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(strict=false)
public class Data {

    @ElementList(inline=true, name="time-layout")
    public List<TimeLayout> timeLayout;

    @Element(name="parameters")
    public Parameter parameter;
}
