package alarm.jack;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;



public class PrefActivity extends Activity {
    private EditText nameText;


    // TODO: add preferences for what weather, and what range of forecast you
    // want.
    // TODO: add google calender integration, option for google cal integration
    // goes here.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefs);

        SharedPreferences prefs = getSharedPreferences(LFnC.KeyPreferences, MODE_PRIVATE);

        nameText = (EditText) findViewById(R.id.prefs_name_EditText);
        nameText.setText(prefs.getString(LFnC.KeyUserName, ""));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor =
            getSharedPreferences(LFnC.KeyPreferences, MODE_PRIVATE).edit();
        editor.putString(LFnC.KeyUserName, nameText.getText().toString());
        editor.commit();
    }

}
