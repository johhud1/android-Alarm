package alarm.jack;

import alarm.weatherHandler.WeatherAndCalService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class alarmReciever extends BroadcastReceiver {
    private String tag = "alarmReciever";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag, "onRecieve");
        // (context.getApplicationContext()).mWakeLock.acquire();
        // ((JackAlarmActivity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        // |
        // WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        AlarmWakeLock.acquireLock(context);

        Intent weatherServiceIntent =
            new Intent((context).getString(R.string.GET_WEATHER_ACTION))
                .setClass(context, WeatherAndCalService.class);
        context.startService(weatherServiceIntent);

        // String intent_alarm_action =
        // context.getString(R.string.ALARM_EXECUTE_INTENT);
        // Intent alarm = new Intent(intent_alarm_action).setClass(context,
        // JackAlarmActivity.class);
        // alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(alarm);
    }
}
