package com.ditclear.sample;

import android.databinding.BindingConversion;
import android.graphics.Typeface;

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

}
