package co.clubmahindra.dailogcalendar1;

import java.io.Serializable;

/**
 * Created by faisal khan on 3/10/2016.
 */
public class CalenderDayModel implements Serializable{
    public String days;
    public int dot;

    public CalenderDayModel(String days, int dot) {
        this.days = days;
        this.dot = dot;
    }

    @Override
    public String toString() {
        return days;
    }
}
