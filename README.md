CustomMaterialCalendar
===================================

> A Custom Material Calendar on the view pager
![calender](https://cloud.githubusercontent.com/assets/7554816/13777918/ecedac0a-ead8-11e5-82ba-a4bec9affd5c.png)

How to use
--------------
```java
Intent intent=new Intent(MainActivity.this,CalendarDialogActivity.class);
intent.putExtra(CalendarConstant.CALENDAR_DATA,calenderData);
intent.putExtra(CalendarConstant.PAGE_TITLE,"Select check in date");
startActivityForResult(intent, CalendarConstant.REQUEST_CODE);
```

calenderDatais a array list for dot blow date. if dots are not requried then no need to send this parameter in intent.

```java
List<CalenderDayModel> alenderData=new ArrayList<>();
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
```

And for receving clicked date 
```java
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CalendarConstant.REQUEST_CODE && data!=null && data.getExtras().containsKey(CalendarConstant.SELECTED_DATE)){
            String selectedDate=data.getExtras().getString(CalendarConstant.SELECTED_DATE);
            Toast.makeText(MainActivity.this, selectedDate, Toast.LENGTH_SHORT).show();
        }
    }
```


