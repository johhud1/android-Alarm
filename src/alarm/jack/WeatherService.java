package alarm.jack;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;

public class WeatherService extends IntentService {
    private Location mLoc;
    private LocationManager mLocationManager;

    public WeatherService(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onHandleIntent(Intent intent){
        mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        mLoc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(intent.getAction().equals(getString(R.string.GET_WEATHER_ACTION))){
            getWeather();
            launchAlarmActivity();
        }

        stopSelf();
    }

    private void getWeather(){

    }

    private void launchAlarmActivity(){

    }

}
