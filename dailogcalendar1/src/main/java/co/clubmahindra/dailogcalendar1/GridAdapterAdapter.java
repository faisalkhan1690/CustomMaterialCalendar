package co.clubmahindra.dailogcalendar1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by faisal pc on 8/13/2015.
 */
public class GridAdapterAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CalenderMonthModel> listMonthData;
    private Context context;

    public GridAdapterAdapter(FragmentManager fragmentManager, ArrayList<CalenderMonthModel> listMonthData,Context context) {
        super(fragmentManager);
        this.listMonthData=listMonthData;
        this.context=context;
    }

    @Override
    public int getCount() {
        return listMonthData.size();
    }


    @Override
    public Fragment getItem(int position) {
        GridFragment fragment =new GridFragment();
        fragment.setData(listMonthData.get(position),context,position);
        return fragment;
    }
}
