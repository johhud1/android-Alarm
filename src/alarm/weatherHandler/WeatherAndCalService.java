package alarm.weatherHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

import alarm.jack.JackAlarmActivity;
import alarm.jack.LFnC;
import alarm.jack.R;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import dwml.simple.Dwml;
import dwml.simple.Temperature;
import dwml.simple.TimeLayout;



public class WeatherAndCalService extends IntentService {
    private static String tag = "WeatherAndCalService";
    private Location mLoc;
    private LocationManager mLocationManager;


    public WeatherAndCalService() {
        super("weatherService");
    }


    @Override
    public void onHandleIntent(Intent intent) {
        Log.d(tag, "onHandleIntent");
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        String weatherString = null;
        if (intent.getAction().equals(getString(R.string.GET_WEATHER_ACTION))) {
            try {
                Dwml dwml = getTodaysWeather();
                weatherString = createWeatherString(dwml);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getTodaysCalender();
        String calenderString = null;
        launchAlarmActivity(weatherString, calenderString);
        stopSelf();
    }


    private Dwml getTodaysWeather() throws URISyntaxException, ClientProtocolException, IOException {
        // TODO: handle weather stuff better
        ArrayList<BasicNameValuePair> weatherParams = new ArrayList<BasicNameValuePair>();
        weatherParams.add(new BasicNameValuePair("mint", "mint"));
        weatherParams.add(new BasicNameValuePair("maxt", "maxt"));
        weatherParams.add(new BasicNameValuePair("temp", "temp"));

        DefaultHttpClient httpc = new DefaultHttpClient();

        SimpleDateFormat sdf = new SimpleDateFormat(LFnC.url_date_format);
        Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.DAY_OF_MONTH, endTime.get(Calendar.DAY_OF_MONTH)+2);
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("lat", "" + mLoc.getLatitude()));
        qparams.add(new BasicNameValuePair("lon", "" + mLoc.getLongitude()));
        qparams.add(new BasicNameValuePair("product", "time-series"));
        qparams.add(new BasicNameValuePair("begin", "2004-01-01T00:00:00"));
        qparams.add(new BasicNameValuePair("end", sdf.format(endTime.getTime())));
        qparams.addAll(weatherParams);

        URI weatherURI =
            URIUtils.createURI("http", LFnC.weatherURL, -1, LFnC.weatherPath,
                               URLEncodedUtils.format(qparams, "UTF-8"), null);
        HttpGet httpget = new HttpGet(weatherURI);
        Log.d(tag, "weatherURI: " + httpget.getURI());
        HttpResponse resp = httpc.execute(httpget);
        XmlDOMParser parser = new XmlDOMParser(resp.getEntity().getContent());
        Dwml dwml = null;
        try {
            dwml = parser.Parse();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return dwml;
    }


    private void getTodaysCalender() {
        // TODO: get calender info
    }


    /*
     * @params weatherString The string describing the weather. This will be
     * passed along to the AlarmActivity to be spoken to the user
     */
    private void launchAlarmActivity(String weatherString, String calenderString) {
        // TODO: launch main activity with EXECUTE_ALARM_ACTION and weather info
        // and calender info
        String intent_alarm_action = getString(R.string.ALARM_EXECUTE_INTENT);
        Intent alarm = new Intent(intent_alarm_action).setClass(this, JackAlarmActivity.class);
        alarm.putExtra(LFnC.weatherStringBundleKey, weatherString);
        alarm.putExtra(LFnC.calenderStringBundleKey, calenderString);

        alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(alarm);
    }


    /*
     * create a string that will be included in the intent used to launch the
     * alarm activity and spoken to the user
     *
     * @params weatherMap A hashMap of the weather info, to be parsed and
     * transformed into an appropriate string for speech synthesis
     *
     * @return A string describing the weather that will be spoken to the user
     */
    private String createWeatherString(Dwml dwml) {
        // TODO: check end-time against now+1day, thereby only including todays forecast.

        Log.d(tag, "createWeatherString");
        StringBuilder retString = new StringBuilder(LFnC.speechWeatherIntroString);
        Formatter formatter = new Formatter(retString);
        List<TimeLayout> timelayouts = dwml.data.timeLayouts;
        List<Temperature> temperatures = dwml.data.parameter.temperatures;
        for (TimeLayout tl : timelayouts) {
            for (Temperature tmp : temperatures) {
                if (tl.layout_key.equals(tmp.time_layout_key)) {
                    retString.append(" " + tmp.type + ": ");
                    for (int i = 0; i < tmp.values.size(); i++) {
                        if (tl.end_times != null) {
                            formatter.format(LFnC.speechWeatherTimeRangeTempFormat, tl.start_times
                                .get(i), tl.end_times.get(i), tmp.values.get(i).intValue(), tmp.units);
                        } else if (i != tmp.values.size() - 1) {
                            formatter.format(LFnC.speechWeatherTimeRangeTempFormat,
                                             tl.start_times.get(i), tl.start_times.get(i + 1),
                                             tmp.values.get(i).intValue(), tmp.units);
                        }
                    }
                }
            }
        }
        Log.d(tag, "return weatherString: " + retString.toString());
        return retString.toString();
    }
}
