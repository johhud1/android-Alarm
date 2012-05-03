package jack.Alarm;

import android.content.Context;
import android.os.PowerManager;

public class AlarmWakeLock {
    private static PowerManager.WakeLock mWakeLock;

    static void acquireLock(Context context){
        if(mWakeLock!=null){
            return;
        }
        PowerManager pwm = (PowerManager)context.getSystemService(context.POWER_SERVICE);
        mWakeLock =
            pwm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.ON_AFTER_RELEASE, "alarm wake lock");
        mWakeLock.acquire();
    }

    static void releaseLock(){
        if(mWakeLock!= null){
            mWakeLock.release();
            mWakeLock=null;
        }
    }

}
