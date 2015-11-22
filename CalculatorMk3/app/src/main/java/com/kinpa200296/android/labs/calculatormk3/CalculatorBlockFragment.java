package com.kinpa200296.android.labs.calculatormk3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pavel on 11/22/2015.
 */
public class CalculatorBlockFragment extends Fragment {

    public static final int basicBlockId = 0;
    public static final int advancedBlockId = 1;

    public static final String BLOCK_ID = "blockId";

    private int blockId;

    public static Fragment newInstance(int blockId) {
        CalculatorBlockFragment fragment = new CalculatorBlockFragment();
        Bundle args = new Bundle();
        args.putInt(BLOCK_ID, blockId);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getTitle(Context context, int blockId) {

        switch (blockId) {
            case basicBlockId:
                return context.getString(R.string.tab_title_basic_block);
            case advancedBlockId:
                return context.getString(R.string.tab_title_advanced_block);
            default:
                Log.d(MainActivity.LOG_TAG, "getTitle(): blockId = " + String.valueOf(blockId) + " is invalid");
                return "";
        }
    }

    public CalculatorBlockFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blockId = getArguments() != null ? getArguments().getInt(BLOCK_ID) : 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = null;

        switch (blockId) {
            case basicBlockId:
                result = inflater.inflate(R.layout.basic_block, container, false);
                break;
            case advancedBlockId:
                result = inflater.inflate(R.layout.advanced_block, container, false);
                break;
            default:
                Log.d(MainActivity.LOG_TAG, "onCreateView(): blockId = " + String.valueOf(blockId) + " is invalid");
                break;
        }

        return result;
    }
}
