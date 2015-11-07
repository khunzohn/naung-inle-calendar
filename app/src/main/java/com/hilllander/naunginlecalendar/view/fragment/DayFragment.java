package com.hilllander.naunginlecalendar.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.calendar_api.util.DateFormatter;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.FontHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

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
        args.putStringArray(MARKETDAYS, mCal.getMarketDayList());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        myView = view.findViewById(R.id.ll_reveal);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_day);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!omdShown) {
                    showOtherMarketDays();
                } else {
                    hideOtherMarketDays();
                }
            }
        });
        Typeface zaw = FontHelper.getZawgyi(getContext());
        Bundle args = getArguments();
        TextView myaDate = (TextView) view.findViewById(R.id.myaDate);
        TextView myaWeekDay = (TextView) view.findViewById(R.id.myaWeekDay);
        TextView engDate = (TextView) view.findViewById(R.id.engDate);
        TextView engDay = (TextView) view.findViewById(R.id.engDay);
        TextView bdhaYear = (TextView) view.findViewById(R.id.bdhaYear);
        TextView astroList = (TextView) view.findViewById(R.id.astroList);
        TextView dragonHead = (TextView) view.findViewById(R.id.dragonHead);
        TextView otherMarketDays = (TextView) view.findViewById(R.id.otherMarketDays);
        TextView mainMarketday = (TextView) view.findViewById(R.id.mainMarketday);
        mainMarketday.setTypeface(zaw);
        otherMarketDays.setTypeface(zaw);
        astroList.setTypeface(zaw);
        dragonHead.setTypeface(zaw);
        String[] marList = args.getStringArray(MARKETDAYS);
        String marText = "";
        mainMarketday.setText(marList[0]);
        for (int i = 1; i < marList.length; i++) {
            marText += " " + marList[i] + "\n";
        }
        otherMarketDays.setText(marText);
        String[] asList = args.getStringArray(ASTRO_DETAIL);
        dragonHead.setText(asList[0]);
        String asText = "";
        for (int i = 1; i < asList.length; i++) {
            asText += " " + asList[i] + "\n";
        }
        astroList.setText(asText);
        bdhaYear.setTypeface(zaw);
        bdhaYear.setText("သာသနာနစ္ " + args.getString(B_YEAR) + " ။");
        myaDate.setTypeface(zaw);
        myaDate.setText(args.getString(M_DATE));
        myaWeekDay.setTypeface(zaw);
        myaWeekDay.setText(args.getString(M_WEEKDAY));
        engDay.setText(args.getString(E_DAY));
        engDate.setText(args.getString(E_DATE));
        return view;
    }

    private void hideOtherMarketDays() {
        toggleOtherMarketDays(omdShown);
        omdShown = false;

    }

    private void showOtherMarketDays() {
        toggleOtherMarketDays(omdShown);
        omdShown = true;
    }

    private void toggleOtherMarketDays(final boolean omdShown) {
        int rotateValue = !omdShown ? 180 : -180;
        fab.animate()
                .rotationXBy(rotateValue)
                .setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                animateReavel((int) fab.getX() + 56, 0, omdShown);
            }
        });
    }

    private void animateReavel(int cx, int cy, boolean omdShown) {

        if (omdShown) {
            animator = animator.reverse();
            animator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {
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
            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(myView.getWidth(), myView.getHeight());
            animator = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            animator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.close));
                    myView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd() {

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

}
