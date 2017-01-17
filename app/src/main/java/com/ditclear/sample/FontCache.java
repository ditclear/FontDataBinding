package com.ditclear.sample;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.ArrayMap;

/**
 * 页面描述：字体缓存，见stackoverflow http://stackoverflow.com/a/16902532/814353
 * <p>
 * Created by ditclear on 2017/1/17.
 */

public class FontCache {

    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface tf = fontCache.get(fontName);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), fontName);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(fontName, tf);
        }
        return tf;
    }
}