package alarm.jack;

import alarm.jack.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class onCBClickListener implements OnClickListener {
    private JackAlarmActivity activity;

    public onCBClickListener(JackAlarmActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        ToggleButton but = (ToggleButton) activity.findViewById(R.id.setAlarm);
        but.setChecked(false);
        activity.unSetAlarm();
    }

}
