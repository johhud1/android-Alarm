package jack.Alarm;

import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.ToggleButton;


public class jackAlarmTimePickedListener implements OnTimeSetListener {
    private String tag = "TimePickedListener";
    private JackAlarmActivity activity;

    public jackAlarmTimePickedListener(JackAlarmActivity activity){
        super();
        this.activity = activity;
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        Log.d(tag, "time picked was "+hour+":"+minute);
        activity.setTime(hour, minute);
        ToggleButton but = (ToggleButton) activity.findViewById(R.id.setAlarm);
        but.setChecked(false);
        activity.unSetAlarm();
    }

}
