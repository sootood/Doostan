package com.jabizparda.cartools.adapter;

/**
 * Created by 123 on 11/27/2017.
 */

public class Model {

    private String text;
    private boolean isSelected = false;

    public Model(String text) {
        this.text = text;
        isSelected = false;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {

        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
