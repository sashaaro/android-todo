package com.example.sasha.todo;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sasha on 06.02.2017.
 */
public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("OpenSans-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        //....
    }
}
