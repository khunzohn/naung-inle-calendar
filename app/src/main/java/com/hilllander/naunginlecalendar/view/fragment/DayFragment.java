package com.hilllander.naunginlecalendar.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.hilllander.naunginlecalendar.R;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by khunzohn on 11/3/15.
 */
public class DayFragment extends Fragment {
    private static final String CURRENT_DAY = "current";
    private View myView;
    private FloatingActionButton fab;
    private SupportAnimator animator;
    private boolean omdShown = false;

    public DayFragment() {
    }

    public static Fragment getInstance(int current) {
        Fragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_DAY, current);
        fragment.setArguments(args);
        return fragment;
    }

    static float hypo(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
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
        int rotateValue = !omdShown ? 179 : 180;
        fab.animate()
                .rotationXBy(rotateValue)
                .setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                animateReavel((int) fab.getX(), 0, omdShown);
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
            float finalRadius = hypo(myView.getWidth(), myView.getHeight());

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
            animator.setDuration(500);
            animator.start();
        }

    }

}
