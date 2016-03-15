package faisal.calenderfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import co.clubmahindra.dailogcalendar1.CalendarConstant;
import co.clubmahindra.dailogcalendar1.CalendarDialogActivity;
import co.clubmahindra.dailogcalendar1.CalenderDayModel;

public class MainActivity extends AppCompatActivity  {

    ArrayList<CalenderDayModel> calenderData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calenderData=new ArrayList<CalenderDayModel>();
        //0=blue
        //1=purple
        //2=red
        //3=white
        calenderData.add(new CalenderDayModel("13/03/2016",0));
        calenderData.add(new CalenderDayModel("12/03/2016",0));
        calenderData.add(new CalenderDayModel("14/03/2016",1));
        calenderData.add(new CalenderDayModel("15/03/2016",3));
        calenderData.add(new CalenderDayModel("16/03/2016",0));
        calenderData.add(new CalenderDayModel("17/03/2016",1));
        calenderData.add(new CalenderDayModel("18/03/2016",2));
        calenderData.add(new CalenderDayModel("19/03/2016",3));
        calenderData.add(new CalenderDayModel("20/03/2016",0));
        calenderData.add(new CalenderDayModel("21/03/2016", 1));
        calenderData.add(new CalenderDayModel("22/03/2016", 2));
        calenderData.add(new CalenderDayModel("23/03/2016", 3));



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CalendarConstant.REQUEST_CODE && data!=null && data.getExtras().containsKey(CalendarConstant.SELECTED_DATE)){
            String selectedDate=data.getExtras().getString(CalendarConstant.SELECTED_DATE);
            Toast.makeText(MainActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
        }
    }

    public void openCalendar(View view){
        Intent intent=new Intent(MainActivity.this,CalendarDialogActivity.class);
        intent.putExtra(CalendarConstant.CALENDAR_DATA,calenderData);
        intent.putExtra(CalendarConstant.PAGE_TITLE,"Select check in date");
        startActivityForResult(intent, CalendarConstant.REQUEST_CODE);
    }

}
