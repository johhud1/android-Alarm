package alarm.weatherHandler;

import java.util.Date;

public class TimeRange {
    private Date startDate;
    private Date endDate;

    public TimeRange(){}

    public TimeRange(Date _startDate, Date _endDate){
        startDate = _startDate;
        endDate = _endDate;
    }
    public void setStartDate(Date date){
        startDate=date;
    }
    public void setEndDate(Date date){
        endDate = date;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getEndDate(){
        return endDate;
    }
}
