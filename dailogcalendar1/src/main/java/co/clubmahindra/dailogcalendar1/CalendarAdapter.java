package co.clubmahindra.dailogcalendar1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author faisal
 *
 */
public class CalendarAdapter extends BaseAdapter {
	private Context context;
	private java.util.Calendar monthCalender;
	private GregorianCalendar previousMonth;
	private List<String> allDayList;
	private View previousView;
	private int firstDay;

	private DateFormat dateFormat;
	private ArrayList<CalenderDayModel> dotDayList;
	private String currentDate;
	private int viewPagerPosition;

	public CalendarAdapter(Context context, GregorianCalendar monthCalendar,ArrayList<CalenderDayModel> dotDayList,int viewPagerPosition) {
		this.monthCalender = monthCalendar;
		this.dotDayList=dotDayList;
		this.context = context;
		this.viewPagerPosition=viewPagerPosition;
		allDayList = new ArrayList<String>();

		monthCalender.set(GregorianCalendar.DAY_OF_MONTH, 1);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		Calendar c=Calendar.getInstance();
		currentDate= dateFormat.format(c.getTime());
		refreshDays();
	}


	public int getCount() {
		return allDayList.size();
	}

	public String getItem(int position) {
		return allDayList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.calendar_item, parent,false);
		}

		for(CalenderDayModel calenderDayModel:dotDayList){
			if(calenderDayModel.days.trim().equals(allDayList.get(position))){
				switch(calenderDayModel.dot) {
					case 0:
						((ImageView)(convertView.findViewById(R.id.date_icon))).setImageResource(R.drawable.bluedot);
						(convertView.findViewById(R.id.date_icon)).setVisibility(View.VISIBLE);
						break;
					case 1:
						((ImageView)(convertView.findViewById(R.id.date_icon))).setImageResource(R.drawable.purpledot);
						(convertView.findViewById(R.id.date_icon)).setVisibility(View.VISIBLE);
						break;
					case 2:
						((ImageView)(convertView.findViewById(R.id.date_icon))).setImageResource(R.drawable.reddot);
						(convertView.findViewById(R.id.date_icon)).setVisibility(View.VISIBLE);
						break;
					case 3:
						((ImageView)(convertView.findViewById(R.id.date_icon))).setImageResource(R.drawable.whitedot);
						(convertView.findViewById(R.id.date_icon)).setVisibility(View.VISIBLE);
						break;
					default:
						break;

				}
				break;
			}
		}

		TextView dayView= (TextView) convertView.findViewById(R.id.date);


		String[] separatedTime = allDayList.get(position).split("/");
		String gridValue = separatedTime[0].replaceFirst("^0*", "");
		if ((Integer.parseInt(gridValue) > 1) && (position < firstDay)) {
			dayView.setTextColor(context.getResources().getColor(R.color.grayLightFont));
			(convertView.findViewById(R.id.date_icon)).setVisibility(View.INVISIBLE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		} else if ((Integer.parseInt(gridValue) < 7) && (position > 28)) {
			dayView.setTextColor(context.getResources().getColor(R.color.grayLightFont));
			(convertView.findViewById(R.id.date_icon)).setVisibility(View.INVISIBLE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}

		String[] separatedTimeCurrent = currentDate.split("/");
		String gridValueCurrent = separatedTimeCurrent[0].replaceFirst("^0*", "");
		if(separatedTimeCurrent[1].equals(separatedTime[1]) && separatedTimeCurrent[2].equals(separatedTime[2]) && (Integer.parseInt(gridValue) < Integer.parseInt(gridValueCurrent))){
			dayView.setTextColor(context.getResources().getColor(R.color.grayLightFont));
			(convertView.findViewById(R.id.date_icon)).setVisibility(View.INVISIBLE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}

		if(CalendarDialogActivity.MONTH_LIMIT!=CalendarDialogActivity.MONTH_LIMIT_FIXED &&
				viewPagerPosition+1==CalendarDialogActivity.MONTH_LIMIT && (Integer.parseInt(gridValue) >= Integer.parseInt(gridValueCurrent))){
			dayView.setTextColor(context.getResources().getColor(R.color.grayLightFont));
			(convertView.findViewById(R.id.date_icon)).setVisibility(View.INVISIBLE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}


		if (allDayList.get(position).equals(currentDate)) {
			((TextView) convertView.findViewById(R.id.date)).setTypeface(Typeface.DEFAULT_BOLD);
			setSelected(convertView);
			previousView = convertView;
		}

		dayView.setText(gridValue);

		return convertView;
	}

	public View setSelected(View view) {

		if (previousView != null) {
			((TextView) previousView.findViewById(R.id.date)).setTextColor(context.getResources().getColor(R.color.blackFont));
			previousView.findViewById(R.id.date_icon).setVisibility(View.VISIBLE);
			previousView.findViewById(R.id.date_background).setVisibility(View.INVISIBLE);

		}
		previousView = view;
		((TextView) view.findViewById(R.id.date)).setTextColor(context.getResources().getColor(R.color.white));
		view.findViewById(R.id.date_icon).setVisibility(View.INVISIBLE);
		view.findViewById(R.id.date_background).setVisibility(View.VISIBLE);
		return view;
	}

	public void refreshDays() {
		allDayList.clear();
		previousMonth = (GregorianCalendar) monthCalender.clone();
		firstDay = monthCalender.get(GregorianCalendar.DAY_OF_WEEK);
		int maxWeekNumber = monthCalender.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		int monthLength = maxWeekNumber * 7;
		int maxPreviousDays = getMaxPreviousDays();
		int calMaxPreviousDays = maxPreviousDays - (firstDay - 1);
		GregorianCalendar previousMonthMaxSet = (GregorianCalendar) previousMonth.clone();
		previousMonthMaxSet.set(GregorianCalendar.DAY_OF_MONTH, calMaxPreviousDays + 1);

		for (int n = 0; n < monthLength; n++) {
			String itemValue = dateFormat.format(previousMonthMaxSet.getTime());
			previousMonthMaxSet.add(GregorianCalendar.DATE, 1);
			allDayList.add(itemValue);
		}
	}

	private int getMaxPreviousDays() {
		if (monthCalender.get(GregorianCalendar.MONTH) == monthCalender.getActualMinimum(GregorianCalendar.MONTH)) {
			previousMonth.set((monthCalender.get(GregorianCalendar.YEAR) - 1), monthCalender.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			previousMonth.set(GregorianCalendar.MONTH, monthCalender.get(GregorianCalendar.MONTH) - 1);
		}
		return previousMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	}
}