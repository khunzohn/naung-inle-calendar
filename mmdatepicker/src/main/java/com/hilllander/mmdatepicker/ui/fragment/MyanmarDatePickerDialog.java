package com.hilllander.mmdatepicker.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.mmdatepicker.R;
import com.hilllander.mmdatepicker.util.adapter.YearChooserAdapter;
import com.hilllander.mmdatepicker.util.listener.OnDatePickedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import mm.technomation.mmtext.MMButtonView;
import mm.technomation.mmtext.MMTextView;

public class MyanmarDatePickerDialog extends DialogFragment implements OnDatePickedListener {

    private static final String EYEAR = "english year";
    private static final String EMONTH = "english month";
    private static final String EDAY = "english day";
    private static final int START = 1277;
    private static final int END = 1477;
    private final int DAY_MONTH = 0;
    private final int YEAR = 1;
    private MMTextView mtvWeekday;
    private MMTextView mtvMonth;
    private MMTextView mtvDay;
    private MMTextView mtvYear;
    private RecyclerView recyDatePicker;
    private MMButtonView mbtCandel;
    private MMButtonView mbtOk;
    private LinearLayout dayMonthLayout;
    private int chooserContext = YEAR;
    private AppCompatDialog dialog;
    private int eYear, eMonth, eDay;
    private MyanmarCalendar mCal;
    private MMButtonView selected;
    private int mYear;


    public MyanmarDatePickerDialog() {
        // Required empty public constructor
    }

    public static MyanmarDatePickerDialog newInstance(GregorianCalendar cal) {
        MyanmarDatePickerDialog fragment = new MyanmarDatePickerDialog();
        Bundle args = new Bundle();
        args.putInt(EYEAR, cal.get(Calendar.YEAR));
        args.putInt(EMONTH, cal.get(Calendar.MONTH));
        args.putInt(EDAY, cal.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        eYear = args.getInt(EYEAR);
        eMonth = args.getInt(EMONTH);
        eDay = args.getInt(EDAY);
        mCal = MyanmarCalendar.getInstance(new GregorianCalendar(eYear, eMonth, eDay));
        mYear = mCal.getYear();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.dialog_mdate_picker);
        initView(dialog, chooserContext);
        dayMonthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooserContext != DAY_MONTH) {
                    chooserContext = DAY_MONTH;
                    animateChooser(chooserContext);
                    inflatePicker(chooserContext);
                } else {
                    animateChooser(chooserContext);
                }
            }
        });
        mtvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooserContext != YEAR) {
                    chooserContext = YEAR;
                    animateChooser(chooserContext);
                    inflatePicker(chooserContext);
                } else {
                    animateChooser(chooserContext);
                }
            }
        });
        return dialog;
    }

    private void inflatePicker(int chooserContext) {
        if (chooserContext == YEAR) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyDatePicker.setHasFixedSize(true);
            recyDatePicker.setLayoutManager(layoutManager);
            ArrayList<String> years = getYears();
            YearChooserAdapter adapter = new YearChooserAdapter(this, years);
            recyDatePicker.setAdapter(adapter);
            Toast.makeText(getContext(), String.valueOf(recyDatePicker.getChildCount()), Toast.LENGTH_SHORT).show();
            recyDatePicker.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    float X = e.getX();
                    float Y = e.getY();
                    MMButtonView child = (MMButtonView) rv.findChildViewUnder(X, Y);
                    if (child != null) {
                        select(child);
                        mtvYear.setMyanmarText(child.getMyanmarText().toString());
                        Toast.makeText(getContext(), "onTouch Intercepted", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
        } else {

        }
    }

    private void select(MMButtonView year) {
        if (selected == null) {
            selected = year;
            year.setSelected(true);
            year.setTextColor(getContext().getResources().getColor(R.color.white));
        } else if (!selected.equals(year)) {
            unSelect(selected);
            selected = year;
            year.setSelected(true);
            year.setTextColor(getContext().getResources().getColor(R.color.white));
        } else if (selected.equals(year)) {
            //do nothing on clicking selected item
        }

    }

    private void unSelect(MMButtonView year) {
        year.setSelected(false);
        year.setTextColor(getContext().getResources().getColor(R.color.accent_dark));
    }

    private ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<>();
        for (int i = START; i <= END; i++) { // for 200 year range
            years.add(eDigitToMDigit(i));
        }
        return years;
    }

    private String eDigitToMDigit(int eDigit) {
        StringBuilder builder = new StringBuilder();
        Map<Integer, String> digitMap = new HashMap<>();
        digitMap.put(0, "၀");
        digitMap.put(1, "၁");
        digitMap.put(2, "၂");
        digitMap.put(3, "၃");
        digitMap.put(4, "၄");
        digitMap.put(5, "၅");
        digitMap.put(6, "၆");
        digitMap.put(7, "၇");
        digitMap.put(8, "၈");
        digitMap.put(9, "၉");

        String[] digits = String.valueOf(eDigit).split("");
        for (int i = 1; i < digits.length; i++) {
            builder.append(digitMap.get(Integer.parseInt(digits[i])));
        }
        return builder.toString();
    }

    private void animateChooser(int chooserContext) {
        if (chooserContext == DAY_MONTH) {
            for (int i = 0; i < dayMonthLayout.getChildCount(); i++) {
                ((MMTextView) dayMonthLayout.getChildAt(i)).setTextColor(getContext().getResources().getColor(R.color.white));
            }
            mtvYear.setTextColor(getContext().getResources().getColor(R.color.grey));
        } else {
            for (int i = 0; i < dayMonthLayout.getChildCount(); i++) {
                ((MMTextView) dayMonthLayout.getChildAt(i)).setTextColor(getContext().getResources().getColor(R.color.grey));
            }
            mtvYear.setTextColor(getContext().getResources().getColor(R.color.white));
        }
    }

    private void initView(final AppCompatDialog dialog, final int chooserContext) {
        dayMonthLayout = (LinearLayout) dialog.findViewById(R.id.dayMonthLayout);
        mtvWeekday = (MMTextView) dialog.findViewById(R.id.mtvWeekday);
        mtvMonth = (MMTextView) dialog.findViewById(R.id.mtvMonth);
        mtvDay = (MMTextView) dialog.findViewById(R.id.mtvDay);
        mtvYear = (MMTextView) dialog.findViewById(R.id.mtvYear);
        recyDatePicker = (RecyclerView) dialog.findViewById(R.id.recyDatePicker);
        mbtCandel = (MMButtonView) dialog.findViewById(R.id.mbtCancel);

        mbtOk = (MMButtonView) dialog.findViewById(R.id.mbtOk);
        mbtCandel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mbtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Choosed : " + mtvYear.getText() + " "
                        + mtvMonth.getText() + " " + mtvDay.getText(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        mtvWeekday.setMyanmarText(mCal.getWeekDayInMyanmar(MyanmarCalendar.LONG_DAY, getContext()));
        mtvMonth.setMyanmarText(mCal.getMonthInMyanmar(getContext()));
        mtvDay.setMyanmarText(mCal.getDayInMyanmnar());
        mtvYear.setMyanmarText(mCal.getYearInMyanmar());
        animateChooser(chooserContext);
        inflatePicker(chooserContext);
    }

    @Override
    public void onYearPickedListener(MMButtonView selectedYear) {
    }

    @Override
    public void onMonthDayPickedListener(int month, int day) {

    }
}
