package alarm.jack;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import alarm.weatherHandler.TimeRange;
import alarm.weatherHandler.XmlDOMParser;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

public class WeatherAndCalService extends IntentService {
    private static String tag = "WeatherAndCalService";
    private Location mLoc;
    private LocationManager mLocationManager;

    public WeatherAndCalService() {
        super("weatherService");
    }

    public void onHandleIntent(Intent intent){
        Log.d(tag, "onHandleIntent");
        mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        mLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        HashMap<String, ArrayList<Pair<TimeRange, Integer>>> weatherMap = new HashMap<String, ArrayList<Pair<TimeRange, Integer>>>();
        String weatherString = null;
        if(intent.getAction().equals(getString(R.string.GET_WEATHER_ACTION))){
            try {
                weatherMap = getTodaysWeather();
                weatherString = createWeatherString(weatherMap);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getTodaysCalender();
        launchAlarmActivity(weatherString);
        stopSelf();
    }

    private HashMap<String, ArrayList<Pair<TimeRange, Integer>>> getTodaysWeather() throws URISyntaxException, ClientProtocolException, IOException{
        //TODO: handle weather stuff better
        ArrayList<BasicNameValuePair> weatherParams = new ArrayList<BasicNameValuePair>();
        weatherParams.add(new BasicNameValuePair("mint", "mint"));
        weatherParams.add(new BasicNameValuePair("maxt", "maxt"));
        weatherParams.add(new BasicNameValuePair("temp", "temp"));

        DefaultHttpClient httpc = new DefaultHttpClient();

        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("lat", ""+mLoc.getLatitude()));
        qparams.add(new BasicNameValuePair("lon", ""+mLoc.getLongitude()));
        qparams.add(new BasicNameValuePair("product", "time-series"));
        qparams.add(new BasicNameValuePair("begin", "2004-01-01T00:00:00"));
        qparams.add(new BasicNameValuePair("end", "2024-01-01T00:00:00"));
        qparams.addAll(weatherParams);

        URI weatherURI = URIUtils.createURI("http", LFnC.weatherURL, -1, LFnC.weatherPath,
                                     URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet httpget = new HttpGet(weatherURI);
        Log.d(tag, "weatherURI: "+httpget.getURI());
        HttpResponse resp =  httpc.execute(httpget);
        XmlDOMParser parser = new XmlDOMParser(resp.getEntity().getContent());
        HashMap<String, ArrayList<Pair<TimeRange, Integer>>> xmlParse = new HashMap<String, ArrayList<Pair<TimeRange, Integer>>>();
        try {
            xmlParse = parser.Parse();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return xmlParse;
    }

    private void getTodaysCalender(){
        //TODO: get calender info
    }

    /*
     * @params  weatherString   The string describing the weather. This will be passed along to the AlarmActivity to be spoken to the user
     */
    private void launchAlarmActivity(String weatherString){
        //TODO: launch main activity with EXECUTE_ALARM_ACTION and weather info and calender info
        String intent_alarm_action = getString(R.string.ALARM_EXECUTE_INTENT);
        Intent alarm = new Intent(intent_alarm_action).setClass(this, JackAlarmActivity.class);
        alarm.putExtra(LFnC.weatherStringBundleKey, weatherString);

        //alarm.putExtra(LFnC.calenderStringBundleKey, calenderString);

        alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(alarm);
    }
    /*
     * create a string that will be included in the intent used to launch the alarm activity and spoken to the user
     * @params weatherMap   A hashMap of the weather info, to be parsed and transformed into an appropriate string for speech synthesis
     * @return              A string desciribing the weather that will be spoken to the user
     */
    private String createWeatherString(HashMap<String, ArrayList<Pair<TimeRange, Integer>>> weatherMap){
        //TODO: create a string to be spoken
        String retString = null;
        Iterator<String> keyIterator = weatherMap.keySet().iterator();
        while(keyIterator.hasNext()){
            String weatherType = keyIterator.next();
            if(weatherType.compareTo(LFnC.myMap_hourly_temp_key)==0){
                //retString =
            }
        }
        return retString;
    }

}
