package alarm.jack;

import alarm.jack.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.EditText;

public class PrefActivity extends Activity {
    private EditText nameText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);

        SharedPreferences prefs = getSharedPreferences(LFnC.KeyPreferences, MODE_PRIVATE);

        nameText  = (EditText) findViewById(R.id.prefs_name_EditText);
        nameText.setText(prefs.getString(LFnC.KeyUserName, ""));
    }

    public void onDestroy(){
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences(LFnC.KeyPreferences, MODE_PRIVATE).edit();
        editor.putString(LFnC.KeyUserName, nameText.getText().toString());
        editor.commit();
    }

}
