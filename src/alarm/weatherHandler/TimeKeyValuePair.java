package alarm.weatherHandler;

import java.util.ArrayList;
import java.util.Date;

public class TimeKeyValuePair<T> {
    public TimeKeyValuePair(String _layoutKey, ArrayList<T> _times){
        layoutKey = _layoutKey;
        mTimes = _times;
    }
    private String layoutKey;
    private ArrayList<T> mTimes;

    public String getLayoutKey(){
        return layoutKey;
    }
    public ArrayList<T> getTimeList(){
        return mTimes;
    }

    public void addTimes(ArrayList<T> dates){
        mTimes = dates;
    }
    public void addTime(T date){
        mTimes.add(date);
    }


}
