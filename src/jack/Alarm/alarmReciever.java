package jack.Alarm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.WindowManager;

public class alarmReciever extends BroadcastReceiver{
    private String tag = "alarmReciever";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag, "onRecieve");
        //(context.getApplicationContext()).mWakeLock.acquire();
        //((JackAlarmActivity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
        //                                                  WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        AlarmWakeLock.acquireLock(context);
        String intent_alarm_action = context.getString(R.string.ALARM_EXECUTE_INTENT);
        Intent alarm = new Intent(intent_alarm_action).setClass(context, JackAlarmActivity.class);
        alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarm);
    }
}
