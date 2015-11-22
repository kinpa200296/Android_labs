package com.kinpa200296.android.labs.calculatormk3;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

/**
 * Created by Pavel on 11/22/2015.
 */
public class TabScrollListener implements ViewPager.OnPageChangeListener {

    private Activity activity;
    private PagerTabStrip tabStrip;

    public TabScrollListener(Activity activity, PagerTabStrip tabStrip) {
        this.activity = activity;
        this.tabStrip = tabStrip;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int darker_gray = activity.getResources().getColor(R.color.darker_gray);
        int pseudo_cyan = activity.getResources().getColor(R.color.pseudo_cyan);
        int white = activity.getResources().getColor(R.color.white);
        int black = activity.getResources().getColor(R.color.black);

        positionOffset += position;

        int backgroundAlpha = Color.alpha(darker_gray) - (int)((Color.alpha(darker_gray) - Color.alpha(pseudo_cyan))*positionOffset);
        int backgroundRed = Color.red(darker_gray) - (int)((Color.red(darker_gray) - Color.red(pseudo_cyan))*positionOffset);
        int backgroundGreen = Color.green(darker_gray) - (int)((Color.green(darker_gray) - Color.green(pseudo_cyan))*positionOffset);
        int backgroundBlue = Color.blue(darker_gray) - (int)((Color.blue(darker_gray) - Color.blue(pseudo_cyan))*positionOffset);

        int textColorAlpha = Color.alpha(white) - (int)((Color.alpha(white) - Color.alpha(black))*positionOffset);
        int textColorRed = Color.red(white) - (int)((Color.red(white) - Color.red(black))*positionOffset);
        int textColorGreen = Color.green(white) - (int)((Color.green(white) - Color.green(black))*positionOffset);
        int textColorBlue = Color.blue(white) - (int)((Color.blue(white) - Color.blue(black))*positionOffset);

        int background = Color.argb(backgroundAlpha, backgroundRed, backgroundGreen, backgroundBlue);
        int textColor = Color.argb(textColorAlpha, textColorRed, textColorGreen, textColorBlue);

        tabStrip.setBackgroundColor(background);
        tabStrip.setTextColor(textColor);
        tabStrip.setTabIndicatorColor(textColor);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
