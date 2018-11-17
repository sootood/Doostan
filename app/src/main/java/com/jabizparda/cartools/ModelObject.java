package com.jabizparda.cartools;

/**
 * Created by 123 on 2/25/2018.
 */

public enum ModelObject {

    RED(R.string.red, R.layout.image_one),
    BLUE(R.string.blue, R.layout.image_two);
//    GREEN(R.string.green, R.layout.view_green);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
