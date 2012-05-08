package dwml.simple;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Parameter {

    @Attribute(name="applicable-location")
    public String applicableLoc;

    @ElementList(name="temperature", inline=true)
    public List<Temperature> temperatures;

}
