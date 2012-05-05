package alarm.jack;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class AlarmWakeLock {
    private static String tag = "alarmWakeLock";
    private static PowerManager.WakeLock mWakeLock;

    static void acquireLock(Context context){
        Log.d(tag, "acquiring wakelock");
        if(mWakeLock!=null){
            return;
        }
        PowerManager pwm = (PowerManager)context.getSystemService(context.POWER_SERVICE);
        mWakeLock =
            pwm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.ON_AFTER_RELEASE, "alarm wake lock");
        mWakeLock.acquire();
    }

    static void releaseLock(){
        Log.d(tag, "releasing wakelock");
        if(mWakeLock!= null){
            mWakeLock.release();
            mWakeLock=null;
        }
    }

}
