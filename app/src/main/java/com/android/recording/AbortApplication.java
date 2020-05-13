package com.android.recording;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class AbortApplication {
    private static final String IS_FROM_MMS = "isfromMMs";
    private static final String ACTIVITY = "activity";
    private LinkedList<HashMap<String , Object>> mList = new LinkedList<HashMap<String , Object>>();
    private static AbortApplication instance;
    private AbortApplication() {
    }

    public synchronized static AbortApplication getInstance() {
        if (null == instance) {
            instance = new AbortApplication();
        }
        return instance;
    }
    // add Activity
    public void addActivity(boolean isFromMMs , Activity activity) {
        Log.d(RecordService.TAG , "addActivity() activity = "+activity + " fromMms="+isFromMMs);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(IS_FROM_MMS, isFromMMs);
        map.put(ACTIVITY, activity);
        mList.add(map);
    }
    public void exit() {
        try {
            for(int i =0 ; i < mList.size() - 1; i++){
                HashMap<String, Object> map = mList.get(i);
                Activity activity = (Activity)map.get(ACTIVITY);
                if (activity != null) {
                    Log.e(RecordService.TAG , "activity:" + activity + " isDestroyed() "+activity.isDestroyed());
                    if(!activity.isDestroyed()){
                        Log.d(RecordService.TAG, "exit() finish activity" + activity);
                        activity.finish();
                    }
                    mList.remove(map);
                }
            }
            for(int j=0;j<RecordService.activityList.size();j++){
                Activity soundPickerActivity = RecordService.activityList.get(j);
                if(soundPickerActivity != null) {
                    if(!soundPickerActivity.isDestroyed()) {
                        Log.d(RecordService.TAG, "exit() finish activity" + soundPickerActivity);
                        soundPickerActivity.finish();
                    }
                    RecordService.activityList.remove(j);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //    public void exit() {
//        try {
//            Iterator<HashMap<String, Object>> it = mList.iterator();
//            while(it.hasNext()){
//                HashMap<String, Object> map = it.next();
//                Activity activity = (Activity)map.get(ACTIVITY);
//                if (activity != null && !(Boolean)map.get(IS_FROM_MMS)) {
//                    Log.d(RecordService.TAG, "exit() finish activity" + activity);
//                    activity.finish();
//                    it.remove();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //System.exit(0);
//        }
//    }
    public void exitAll(){
        try {
            Iterator<HashMap<String, Object>> it = mList.iterator();
            Log.e(RecordService.TAG , "exitAll() mList"+mList.size());
            while(it.hasNext()){
                HashMap<String, Object> map = it.next();
                Activity activity = (Activity)map.get(ACTIVITY);
                if (activity != null) {
                    Log.d(RecordService.TAG, "exitAll() finish activity" + activity);
                    activity.finish();
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}