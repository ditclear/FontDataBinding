package com.ditclear.sample;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 页面描述：
 * <p>
 * Created by ditclear on 2017/1/17.
 */

public class FontBinding {

    @BindingConversion
    public static Typeface convertStringToFace(String fontName){
        try {
            return FontCache.getTypeface(fontName,FontApp.getInstance());
        }catch (Exception e){
            throw e;
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView v, String s){
        v.setTypeface(convertStringToFace(FontApp.getInstance().getString(R.string.ruthie)));
        v.setText(s);
    }



}
