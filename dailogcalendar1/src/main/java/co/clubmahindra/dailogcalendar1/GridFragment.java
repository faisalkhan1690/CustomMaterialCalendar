package co.clubmahindra.dailogcalendar1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class GridFragment extends Fragment implements AdapterView.OnItemClickListener {

    private CalendarAdapter adapter;
    private GridView gCalender;
    private GregorianCalendar gcMonth;
    private int viewPagerPosition;
    private ArrayList<CalenderDayModel> calenderDayModel;
    private GridFragmentCallback gridFragmentCallback;



    public void setData(CalenderMonthModel calenderMonthModel,Context context,int mViewPagerPositiont) {
        calenderDayModel=calenderMonthModel.calenderDayModel;
        gcMonth = calenderMonthModel.gcMonth;
        viewPagerPosition=mViewPagerPositiont;

        if(context instanceof GridFragmentCallback){
            gridFragmentCallback=(GridFragmentCallback)context;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        gCalender = (GridView)view.findViewById(R.id.gridview);
        gCalender.setOnItemClickListener(this);
        Locale.setDefault(Locale.US);


        adapter = new CalendarAdapter(getActivity(), gcMonth,calenderDayModel,viewPagerPosition);
        gCalender.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {


        String selectedGridDate = adapter.getItem(position);
        String[] separatedTime = selectedGridDate.split("/");
        String gridvalueString = separatedTime[0].replaceFirst("^0*", "");
        int gridvalue = Integer.parseInt(gridvalueString);
        if (((gridvalue > 10) && (position < 8)) ||((gridvalue < 7) && (position > 28))) {
            return;
        }
        Calendar c=Calendar.getInstance();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(c.getTime());
        String[] separatedTimeCurrent = currentDate.split("/");
        String gridValueCurrent = separatedTimeCurrent[0].replaceFirst("^0*", "");
        if(separatedTimeCurrent[1].equals(separatedTime[1]) && separatedTimeCurrent[2].equals(separatedTime[2]) && gridvalue < Integer.parseInt(gridValueCurrent)){
            return;
        }

        if(CalendarDialogActivity.MONTH_LIMIT!=CalendarDialogActivity.MONTH_LIMIT_FIXED &&
                viewPagerPosition+1==CalendarDialogActivity.MONTH_LIMIT && gridvalue >= Integer.parseInt(gridValueCurrent)){
            return;
        }

        ((CalendarAdapter) parent.getAdapter()).setSelected(view);

        if(gridFragmentCallback!=null){
            gridFragmentCallback.selectedDate(selectedGridDate+"");
        }


    }

    public interface GridFragmentCallback{
        void selectedDate(String date);
    }
}
