package co.clubmahindra.dailogcalendar1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarDialogActivity extends FragmentActivity implements GridFragment.GridFragmentCallback, View.OnClickListener {

    private ViewPager mPager;
    private GridAdapterAdapter mAdapter;
    ArrayList<CalenderDayModel> calenderData=new ArrayList<CalenderDayModel>();
    ArrayList<CalenderMonthModel> listMonthData = new ArrayList<CalenderMonthModel>();
    private TextView tvPreMonth;
    private TextView tvCurrentMonth;
    private TextView tvNextMonth;
    private int selectedPagePosition=0;
    public static final int MONTH_LIMIT_FIXED=4;
    public static int MONTH_LIMIT=4;
    private SimpleDateFormat dFCurrentMonth = new SimpleDateFormat("MMMM yyyy", Locale.US);
    private SimpleDateFormat dfNextPreMonth = new SimpleDateFormat("MMM", Locale.US);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_dialog);

        mPager = (ViewPager)findViewById(R.id.pager);
        tvPreMonth = (TextView)findViewById(R.id.tv_pre_month);
        tvCurrentMonth = (TextView)findViewById(R.id.tv_current_month);
        tvNextMonth = (TextView)findViewById(R.id.tv_next_month);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(CalendarConstant.CALENDAR_DATA) && bundle.containsKey(CalendarConstant.PAGE_TITLE)){
            calenderData=(ArrayList<CalenderDayModel>)bundle.getSerializable(CalendarConstant.CALENDAR_DATA);
            ((TextView)findViewById(R.id.tv_title)).setText(bundle.getString(CalendarConstant.PAGE_TITLE));
        }

        GregorianCalendar gcMonth = (GregorianCalendar) GregorianCalendar.getInstance();
        String currentDay=(new SimpleDateFormat("dd", Locale.US).format(gcMonth.getTime()));
        if(!currentDay.equals("01")){
            MONTH_LIMIT++;
        }

        for (int i = 0; i < MONTH_LIMIT; i++) {
            GregorianCalendar mGcMonth = (GregorianCalendar) GregorianCalendar.getInstance();
            mGcMonth = (GregorianCalendar) gcMonth.clone();

            listMonthData.add(new CalenderMonthModel(mGcMonth, calenderData));
            if (gcMonth.get(GregorianCalendar.MONTH) == gcMonth.getActualMaximum(GregorianCalendar.MONTH)) {
                gcMonth.set((gcMonth.get(GregorianCalendar.YEAR) + 1), gcMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
            } else {
                gcMonth.set(GregorianCalendar.MONTH, gcMonth.get(GregorianCalendar.MONTH) + 1);
            }
        }

        mPager = (ViewPager)findViewById(R.id.pager);
        tvPreMonth = (TextView)findViewById(R.id.tv_pre_month);
        tvCurrentMonth = (TextView)findViewById(R.id.tv_current_month);
        tvNextMonth = (TextView)findViewById(R.id.tv_next_month);
        mAdapter = new GridAdapterAdapter(getSupportFragmentManager(), listMonthData,this);
        mPager.setAdapter(mAdapter);

        setNextPrevious((GregorianCalendar)listMonthData.get(selectedPagePosition).gcMonth.clone());

        tvPreMonth.setOnClickListener(this);
        tvNextMonth.setOnClickListener(this);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPagePosition=position;
                setNextPrevious((GregorianCalendar)listMonthData.get(position).gcMonth.clone());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * set title of month on page change
     * @param gcMonth
     */
    private void setNextPrevious(GregorianCalendar gcMonth) {
        tvCurrentMonth.setText(dFCurrentMonth.format(gcMonth.getTime()));

        if (gcMonth.get(GregorianCalendar.MONTH) == gcMonth.getActualMaximum(GregorianCalendar.MONTH)) {
            gcMonth.set((gcMonth.get(GregorianCalendar.YEAR) + 1), gcMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
        } else {
            gcMonth.set(GregorianCalendar.MONTH, gcMonth.get(GregorianCalendar.MONTH) + 1);
        }
        tvNextMonth.setText(dfNextPreMonth.format(gcMonth.getTime()));

        if (gcMonth.get(GregorianCalendar.MONTH) == gcMonth.getActualMinimum(GregorianCalendar.MONTH)) {
            gcMonth.set((gcMonth.get(GregorianCalendar.YEAR) - 1),gcMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            gcMonth.set(GregorianCalendar.MONTH,gcMonth.get(GregorianCalendar.MONTH) - 1);
        }
        if (gcMonth.get(GregorianCalendar.MONTH) == gcMonth.getActualMinimum(GregorianCalendar.MONTH)) {
            gcMonth.set((gcMonth.get(GregorianCalendar.YEAR) - 1),gcMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            gcMonth.set(GregorianCalendar.MONTH,gcMonth.get(GregorianCalendar.MONTH) - 1);
        }
        tvPreMonth.setText(dfNextPreMonth.format(gcMonth.getTime()));

    }


    @Override
    public void selectedDate(String date) {
        Intent intent=new Intent();
        intent.putExtra(CalendarConstant.SELECTED_DATE,date);
        setResult(2, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
       if(view==tvPreMonth && (selectedPagePosition-1)>-1){
           selectedPagePosition--;
           mPager.setCurrentItem(selectedPagePosition);
           setNextPrevious((GregorianCalendar)listMonthData.get(selectedPagePosition).gcMonth.clone());
       }else if(view==tvNextMonth && (selectedPagePosition+1)<MONTH_LIMIT){
           selectedPagePosition++;
           mPager.setCurrentItem(selectedPagePosition);
           setNextPrevious((GregorianCalendar)listMonthData.get(selectedPagePosition).gcMonth.clone());
       }
    }
}
