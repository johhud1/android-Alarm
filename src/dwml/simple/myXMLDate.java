package dwml.simple;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleframework.xml.transform.Transform;

import alarm.jack.LFnC;



public class myXMLDate implements Transform<Date> {

    @Override
    public Date read(String strDate) throws Exception {
        SimpleDateFormat parser = new SimpleDateFormat(LFnC.xml_date_format_string);
        Date myDate = parser.parse(strDate);
        return myDate;
    }


    @Override
    public String write(Date date) throws Exception {
        SimpleDateFormat parser = new SimpleDateFormat(LFnC.xml_date_format_string);
        String strDate = parser.format(date);
        return strDate;
    }

    // @Override
    // public Date read(InputNode in) throws Exception {
    // SimpleDateFormat parser = new
    // SimpleDateFormat(LFnC.xml_date_format_string);
    // Date myDate = parser.parse(in.getValue());
    // return myDate;
    // }
    //
    // @Override
    // public void write(OutputNode on, Date date) throws Exception {
    // SimpleDateFormat parser = new
    // SimpleDateFormat(LFnC.xml_date_format_string);
    // String strDate = parser.format(date);
    // on.setValue(strDate);
    // }

}
