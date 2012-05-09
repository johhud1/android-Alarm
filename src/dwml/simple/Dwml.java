package dwml.simple;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;



@Root(strict = false)
public class Dwml {

    @Attribute
    public String noNamespaceSchemaLocation;

    @Attribute
    public double version;

    // @Element
    // public Head head;

    @Element(name = "data")
    public Data data;

}
