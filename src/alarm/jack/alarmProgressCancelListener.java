package alarm.jack;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;



public class alarmProgressCancelListener implements OnCancelListener {
    private JackAlarmActivity activity;
    private String tag = "alarmProgressCancelListener";


    public alarmProgressCancelListener(JackAlarmActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onCancel(DialogInterface dialog) {

        activity.stopSpeaking();
    }

}
