package jack.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class alarmReciever extends BroadcastReceiver {
    private String tag = "alarmReciever";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag, "onRecieve");
        ((JackAlarmActivity)context).mWakeLock.acquire();
        String intent_alarm_action = context.getString(R.string.ALARM_EXECUTE_INTENT);
       // if(intent.getAction().equals(intent_alarm_action)){
            Intent alarm = new Intent(intent_alarm_action).setClass(context, JackAlarmActivity.class);
            alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarm);
       // }
    }

}
