package dwml.simple;

import org.simpleframework.xml.*;

@Root(strict=false)
public class Dwml {

    @Attribute
    public String noNamespaceSchemaLocation;

    @Attribute
    public double version;

    //@Element
    //public Head head;

    @Element(name="data")
    public Data data;

}
