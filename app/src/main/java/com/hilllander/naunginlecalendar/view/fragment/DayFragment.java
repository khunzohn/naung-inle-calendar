package com.hilllander.naunginlecalendar.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.calendar_api.util.DateFormatter;
import com.hilllander.naunginlecalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/3/15.
 */
public class DayFragment extends Fragment {
    private static final String M_WEEKDAY = "myanmar weekday";
    private static final String M_DATE = "myanmar date";
    private static final String E_DAY = "english day";
    private static final String E_DATE = "english date";
    private static final String M_YEAR = "myanmar year";
    private static final String B_YEAR = "buddha year";
    private static final String ASTRO_DETAIL = "astro detail list";
    private static final String MARKETDAYS = "market day list";
    private static final String HOLIDAYS = "holidays";
    private View myView;
    private FloatingActionButton fab;
    private SupportAnimator animator;
    private boolean omdShown = false;

    public DayFragment() {
    }

    public static Fragment getInstance(GregorianCalendar greCal, Context context) {
        Fragment fragment = new DayFragment();
        MyanmarCalendar mCal = MyanmarCalendar.getInstance(greCal);
        Bundle args = new Bundle();
        args.putStringArray(ASTRO_DETAIL, mCal.getAstroDetialList(context));
        args.putString(M_WEEKDAY, mCal.getWeekDayInMyanmar(MyanmarCalendar.LONG_DAY, context));
        args.putString(M_DATE, mCal.getMyanmarDate(context));
        args.putString(E_DAY, String.valueOf(greCal.get(Calendar.DAY_OF_MONTH)));
        args.putString(E_DATE, DateFormatter.getMonthAsString(greCal.get(Calendar.MONTH) + 1) + " " + greCal.get(Calendar.YEAR));
        args.putString(M_YEAR, mCal.getYearInMyanmar());
        args.putString(B_YEAR, mCal.getBuddhaYearInMyanmar());
        args.putStringArrayList(MARKETDAYS, mCal.getMarketDayList());
        args.putStringArray(HOLIDAYS, mCal.getHolidays(context));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myView = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        myView = view.findViewById(R.id.ll_reveal);
        FrameLayout background = (FrameLayout) view.findViewById(R.id.marketday_background);
        int backId = new Random().nextInt(6);
        int backResId = getResId(backId);
        background.setBackgroundResource(backResId);

        Bundle args = getArguments();
        MMTextView myaDate = (MMTextView) view.findViewById(R.id.myaDate);
        MMTextView myaWeekDay = (MMTextView) view.findViewById(R.id.myaWeekDay);
        TextView engDate = (TextView) view.findViewById(R.id.engDate);
        TextView engDay = (TextView) view.findViewById(R.id.engDay);
        MMTextView bdhaYear = (MMTextView) view.findViewById(R.id.bdhaYear);
        MMTextView astroList = (MMTextView) view.findViewById(R.id.astroList);
        MMTextView dragonHead = (MMTextView) view.findViewById(R.id.dragonHead);
        MMTextView mainMarketday = (MMTextView) view.findViewById(R.id.mainMarketday);
        MMTextView holidays = (MMTextView) view.findViewById(R.id.holidays);
        final ListView otherMarketDaysList = (ListView) view.findViewById(R.id.otherMarketDaysList);

        String[] holList = args.getStringArray(HOLIDAYS);
        String holText = "";
        for (String hol : holList) {
            if (hol != null && !hol.isEmpty())
                holText += hol + "\n";
        }
        holidays.setMyanmarText(holText);
        ArrayList<String> marList = args.getStringArrayList(MARKETDAYS);

        mainMarketday.setMyanmarText(marList.get(0));
        marList.remove(0);
        otherMarketDaysList.setAdapter(new OMDListAdapter(marList));
        fab = (FloatingActionButton) view.findViewById(R.id.fab_day);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!omdShown) {
                    showOtherMarketDays(otherMarketDaysList);
                } else {
                    hideOtherMarketDays(otherMarketDaysList);
                }
            }
        });

        String[] asList = args.getStringArray(ASTRO_DETAIL);
        dragonHead.setMyanmarText(asList[0]);
        String asText = "";
        for (int i = 1; i < asList.length; i++) {
            asText += " " + asList[i] + "\n";
        }
        astroList.setMyanmarText(asText);
        bdhaYear.setMyanmarText(getContext().getString(R.string.sasana_year) + args.getString(B_YEAR) + " ။");
        myaDate.setMyanmarText(args.getString(M_DATE));
        myaWeekDay.setMyanmarText(args.getString(M_WEEKDAY));
        engDay.setText(args.getString(E_DAY));
        engDate.setText(args.getString(E_DATE));
        return view;
    }

    private int getResId(int backId) {
        switch (backId) {
            case 0:
                return R.drawable.m_1;
            case 1:
                return R.drawable.m_2;
            case 2:
                return R.drawable.m_3;
            case 3:
                return R.drawable.m_4;
            case 4:
                return R.drawable.m_5;
            case 5:
                return R.drawable.m_6;
            default:
                return R.drawable.m_5;
        }
    }

    private void hideOtherMarketDays(ListView list) {
        toggleOtherMarketDays(omdShown, list);
        omdShown = false;

    }

    private void showOtherMarketDays(ListView list) {
        toggleOtherMarketDays(omdShown, list);
        omdShown = true;
    }

    private void toggleOtherMarketDays(final boolean omdShown, final ListView listView) {
        int rotateValue = !omdShown ? 180 : -180;
        fab.animate()
                .rotationXBy(rotateValue)
                .setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                animateReavel((int) fab.getX() + 56, 0, omdShown, listView);
            }
        });
    }

    private void animateReavel(int cx, int cy, boolean omdShown, final ListView listView) {

        if (omdShown) {
            animator = animator.reverse();
            animator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {
                    listView.animate().setDuration(50).alpha(0);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.list_other_market_days));
                }

                @Override
                public void onAnimationEnd() {
                    myView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel() {

                }

                @Override
                public void onAnimationRepeat() {

                }
            });
            animator.start();
        } else {
            // get the final radius for the clipping full_moon
            float finalRadius = (float) Math.hypot(myView.getWidth(), myView.getHeight());
            animator = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            animator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {
                    listView.setAlpha(0); // initialise its alpha to 0
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.close));
                    myView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd() {
                    listView.animate().setDuration(200).alpha(1);
                }

                @Override
                public void onAnimationCancel() {

                }

                @Override
                public void onAnimationRepeat() {

                }
            });
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(100);
            animator.start();
        }

    }

    public static class ViewHolder {
        MMTextView marItem;

        public ViewHolder(View view) {
            marItem = (MMTextView) view.findViewById(R.id.marketday_item);
        }

        public void setMarItemText(String text) {
            marItem.setMyanmarText(text);
        }
    }

    private class OMDListAdapter extends BaseAdapter {
        ArrayList<String> marList;

        public OMDListAdapter(ArrayList<String> marList) {
            this.marList = marList;
        }

        @Override
        public int getCount() {
            return marList.size();
        }

        @Override
        public Object getItem(int i) {
            return marList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view == null) {
                view = inflater.inflate(R.layout.market_days_list_item, viewGroup, false);
            }
            ViewHolder holder = new ViewHolder(view);
            holder.setMarItemText(marList.get(i));
            return view;
        }

    }
}
