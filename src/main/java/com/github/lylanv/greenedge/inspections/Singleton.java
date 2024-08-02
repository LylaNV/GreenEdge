package com.github.lylanv.greenedge.inspections;

import com.intellij.openapi.project.Project;
import java.util.Map;
import java.util.HashMap;

public class Singleton {
//    public static Project project;
    public static Map<String, Integer> redAPICalls = new HashMap<>(); //holds red API call's name and its energy cost
    public static int NUMBER_OF_RED_APIS;

//    public Singleton(final Project project) {
//        setAPICallsMap();
//        Singleton.project = project;
//    }

    public Singleton() {
        setAPICallsMap();
    }

    // This function adds the API calls to ReadAPICalls set
    private void setAPICallsMap() {
        //TODO: you have some the same method expression such as query, updated, addView, findViewById, setText and show
        // consider to do something with them. Their energy consumption could be different
        redAPICalls.clear();
        redAPICalls.put("performClick",1);
        redAPICalls.put("getIntExtra",1);
        redAPICalls.put("i",1);
        redAPICalls.put("finish",1);
        redAPICalls.put("cancelAll",1);
        redAPICalls.put("startActivityForResult",1);
        redAPICalls.put("findViewById",1);
        redAPICalls.put("getPhoneType",1);
        redAPICalls.put("clear",1);
        redAPICalls.put("getPixel",1);
        redAPICalls.put("getReadableDatabase",1);
        redAPICalls.put("getWritableDatabase",1);
        redAPICalls.put("openDatabase",1);
        redAPICalls.put("update",1);
        redAPICalls.put("query",1);
        redAPICalls.put("insertOrThrow",1);
        redAPICalls.put("execSQL",1);
        redAPICalls.put("delete",1);
        redAPICalls.put("insert",1);
        redAPICalls.put("openOrCreateDatabase",1);
        redAPICalls.put("rawQuery",1);
        redAPICalls.put("getVersion",1);
        redAPICalls.put("endTransaction",1);
        redAPICalls.put("executeInsert",1);
        redAPICalls.put("openRawResource",1);
        redAPICalls.put("getGpsStatus",1);
        redAPICalls.put("setContentView",1);
        redAPICalls.put("show",1);
        redAPICalls.put("makeText",1);
        redAPICalls.put("notifyDataSetChanged",1);
        redAPICalls.put("setSelection",1);
        redAPICalls.put("addView",1);
        redAPICalls.put("removeAllViews",1);
        redAPICalls.put("setText",1);
        redAPICalls.put("getTextBounds",1);
        redAPICalls.put("setTextColor",1);
        redAPICalls.put("dismiss",1);
        redAPICalls.put("setMax",1);
        redAPICalls.put("setEnabled",1);
        redAPICalls.put("drawText",1);
        redAPICalls.put("setLayoutParams",1);
        redAPICalls.put("setClickable",1);
        redAPICalls.put("getLongPressTimeout",1);
        redAPICalls.put("setImageResource",1);
        redAPICalls.put("openRawResource",1);
        redAPICalls.put("decodeStream",1);
        redAPICalls.put("createBitmap",1);
        redAPICalls.put("startAnimation",1);
        redAPICalls.put("getStackTraceString",1);
        redAPICalls.put("getDateFormat",1);
        redAPICalls.put("onStartCommand",1);
        redAPICalls.put("dispatchMessage",1);
        redAPICalls.put("e",1);
        redAPICalls.put("loadData",1);
        redAPICalls.put("loadDataWithBaseURL",1);
        redAPICalls.put("loadUrl",1);

        NUMBER_OF_RED_APIS = redAPICalls.size();
    }

//    public void setProject(final Project project) {
//        this.project = project;
//    }
//
//    public Project getProject(){
//        return project;
//    }
}
