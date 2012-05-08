package alarm.jack;

public class LFnC {

    public static final String KeyAlarmSetTimeString = "alarm_set_time_tv";
    public static final String KeyCBBoolForDay = "cb_bool_day";
    public static final String KeyHoursVar = "hours";
    public static final String KeyMinutesVar = "minutes";
    public static final String KeyAlarmSetToggled = "alarm_toggle_button_state";
    public static final String KeyUserName = "username_key";
    public static final String KeyPreferences = "my_shared_prefs";
    public static final String weatherURL = "graphical.weather.gov";
    public static final String weatherPath = "/xml/sample_products/browser_interface/ndfdXMLclient.php";
    public static final String timeLayoutKey_tag = "layout-key"; //tag for the key to a time particular time layout
    public static final String timeLayout_tag = "time-layout"; //tag for the a time-layout
    public static final String startTime_nodeName = "start-valid-time";
    public static final String endTime_nodeName = "end-valid-time";
    public static final String temp_value_tag = "value";
    public static final String hourly_temp_tag = "temperature";
    public static final String myMap_hourly_temp_key = "hourly_temp";
    public static final String weatherStringBundleKey = "alarm.jack.weather_bundle_key";
    public static final String calenderStringBundleKey = "alarm.jack.calender_bundle_key";
    public static final String network_provider_disabled_toast = "network location provider disabled. Enabling..";

    public static final String xml_date_format_string = "yyyy-MM-d'T'HH:mm:ssz";
    public static final String alarm_time_format_main = "HH:mmz";
    //TODO: get these speech format strings right *********
    public static final String speechTheDateIntroFormatString = " the date is %a, %B %e ";
    public static final String speechWeatherTimeRangeTempFormat = "from %1$tl:%1$tM %1$tp to %2$tl:%2$tM %2$tp, "
                                +"%2$d degrees %3$s. ";
    public static final String speechWeatherIntroString =
        " the forecast for today is: ";
    //public static final String threeHourTempRecurringString = " from %s to %s, %d degrees fahrenheit. ";

}
