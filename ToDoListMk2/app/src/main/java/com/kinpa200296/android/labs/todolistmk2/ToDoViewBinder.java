package com.kinpa200296.android.labs.todolistmk2;

import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class ToDoViewBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        switch (view.getId()) {
            case R.id.toDoState:
                if (((Boolean) data)) {
                    ((ImageView) view).setImageResource(R.drawable.star_done);
                } else {
                    ((ImageView) view).setImageResource(R.drawable.star_not_done);
                }
                break;
            default:
                return false;
        }
        return true;
    }
}
