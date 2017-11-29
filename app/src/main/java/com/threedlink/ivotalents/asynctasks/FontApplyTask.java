package com.threedlink.ivotalents.asynctasks;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

import java.io.IOException;

/**
 * Created by diiaz94 on 18-05-2017.
 */
public class FontApplyTask extends AsyncTask<Void, Void, Void> {


    private Context ctx;
    View parent;
    private final Handler handler;
    public FontApplyTask(Context ctx, View parent) {
        this.ctx = ctx;
        this.parent = parent;
        handler = new Handler(ctx.getMainLooper());
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (parent instanceof LinearLayout)
            setFontsOnLinear((LinearLayout) parent);
        if (parent instanceof RelativeLayout)
            setFontsOnRelative((RelativeLayout) parent);
        if (parent instanceof ScrollView)
            setFontsOnScroll((ScrollView) parent);
        return null;
    }


    @Override
    protected void onCancelled() {
    }


    public static Typeface getFont(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(),"fonts/Exo2-Regular.otf");
    }
    public static Typeface getFontBold(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(),"fonts/Exo2-Bold.otf");
    }
    public Typeface getFontSemiBold(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(),"fonts/Exo2-SemiBold.otf");
    }
    public static Typeface getFontSemiBoldItalic(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(),"fonts/Exo2-SemiBoldItalic.otf");
    }
    public static Typeface getFontLight(Context ctx){
        return Typeface.createFromAsset(ctx.getAssets(),"fonts/Exo2-Light.otf");
    }

    public void setFontsOnRelative(RelativeLayout layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    setTypeFace(textView,getFontApply(textView.getTag().toString()));
                }else{
                    setTypeFace(textView,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    setTypeFace(editText,getFontApply(editText.getTag().toString()));
                }else{
                    setTypeFace(editText,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof CheckBox){
                CheckBox checkText= (CheckBox) layout.getChildAt(i);
                if(checkText.getTag()!=null){
                    setTypeFace(checkText,getFontApply(checkText.getTag().toString()));
                }else{
                    setTypeFace(checkText,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }

    }
    public void setFontsOnLinear(LinearLayout layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    setTypeFace(textView,getFontApply(textView.getTag().toString()));
                }else{
                    setTypeFace(textView,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    setTypeFace(editText,getFontApply(editText.getTag().toString()));
                }else{
                    setTypeFace(editText,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }
    }

    public void setFontsOnScroll(ScrollView layout) {
        for( int i = 0; i < layout.getChildCount(); i++ ) {
            if (layout.getChildAt(i) instanceof TextView){
                TextView textView= (TextView) layout.getChildAt(i);
                if(textView.getTag()!=null){
                    setTypeFace(textView,getFontApply(textView.getTag().toString()));
                }else{
                    setTypeFace(textView,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof EditText){
                EditText editText= (EditText) layout.getChildAt(i);
                if(editText.getTag()!=null){
                    setTypeFace(editText,getFontApply(editText.getTag().toString()));
                }else{
                    setTypeFace(editText,getFont(ctx));
                }
            }else if(layout.getChildAt(i) instanceof LinearLayout){
                LinearLayout linear= (LinearLayout) layout.getChildAt(i);
                setFontsOnLinear(linear);
            }else if(layout.getChildAt(i) instanceof RelativeLayout){
                RelativeLayout relative= (RelativeLayout) layout.getChildAt(i);
                setFontsOnRelative(relative);
            }else if(layout.getChildAt(i) instanceof ScrollView){
                ScrollView scroll= (ScrollView) layout.getChildAt(i);
                setFontsOnScroll(scroll);
            }
        }

    }

    private void setTypeFace(final View v, final Typeface fontApply) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(v instanceof TextView){
                    ((TextView) v).setTypeface(fontApply);
                }
                if(v instanceof EditText){
                    ((EditText) v).setTypeface(fontApply);
                }
                if(v instanceof CheckBox){
                    ((CheckBox) v).setTypeface(fontApply);
                }
            }
        });

    }

    public Typeface getFontApply(String tag){
        if(tag.indexOf("normal")!=-1){
            return getFont(ctx);
        }
        if(tag.indexOf("semibolditalic")!=-1) {
            return getFontSemiBoldItalic(ctx);
        }
        if(tag.indexOf("semibold")!=-1){
            return getFontSemiBold(ctx);
        }
        if(tag.indexOf("bold")!=-1){
            return getFontBold(ctx);
        }
        if(tag.indexOf("light")!=-1) {
            return getFontLight(ctx);
        }

        return getFont(ctx);

    }


    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }
}
