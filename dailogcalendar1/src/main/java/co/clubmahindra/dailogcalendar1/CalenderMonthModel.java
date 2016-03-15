package co.clubmahindra.dailogcalendar1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by faisal khan on 3/10/2016.
 */
public class CalenderMonthModel implements Serializable{

    public GregorianCalendar gcMonth;
    public ArrayList<CalenderDayModel> calenderDayModel;

    public CalenderMonthModel(GregorianCalendar gcMonth, ArrayList<CalenderDayModel> calenderDayModel) {
        this.gcMonth = gcMonth;
        this.calenderDayModel = calenderDayModel;
    }
}
