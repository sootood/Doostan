package com.jabizparda.cartools;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HappyCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontUtil.setDefaultFont(this, "SERIF", "IRANYekanMobileLight.ttf");
        FontUtil.setDefaultFont(this, "YEKAN", "IRANYekanMobileLight.ttf");
        FontUtil.setDefaultFont(this, "DEFAULT", "IRANYekanMobileLight.ttf");
        FontUtil.setDefaultFont(this, "MONOSPACE", "IRANYekanMobileLight.ttf");
        FontUtil.setDefaultFont(this, "SERIF", "IRANYekanMobileLight.ttf");
        FontUtil.setDefaultFont(this, "SANS_SERIF", "IRANYekanMobileLight.ttf");
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {

        final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Map<String, Typeface> newMap = new HashMap<String, Typeface>();
            newMap.put("YEKAN", customFontTypeface);
            try {
                final Field staticField = Typeface.class
                        .getDeclaredField("sSystemFontMap");
                staticField.setAccessible(true);
                staticField.set(null, newMap);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
                defaultFontTypefaceField.setAccessible(true);
                defaultFontTypefaceField.set(null, customFontTypeface);
            } catch (Exception e) {

            }
        }
    }
//
    AlertDialog alertDialog;
    public void showProgress(boolean bool) {
        if(bool) {
            if(alertDialog != null){
                alertDialog.dismiss();
            }
            View view = getLayoutInflater().inflate(R.layout.dialog_progress_one, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
            builder.setView(view);
            view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
        } else {
            if(alertDialog != null)
                alertDialog.dismiss();
        }
    }
    public void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
