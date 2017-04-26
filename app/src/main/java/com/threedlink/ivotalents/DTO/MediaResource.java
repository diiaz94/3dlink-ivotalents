package com.threedlink.ivotalents.DTO;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by diiaz94 on 23-04-2017.
 */
public class MediaResource {
    private static final String TAG = "MediaResource";
    private String path;
    private String name;
    private String extension;
    private MediaReourceType type;
    private String date;
    public static String AudioExtensions = "|mp3|m4a|";
    public static String PhotoExtensions = "|jpg|png|";
    public static String VideoExtensions = "|mp4|";


    public MediaResource(String path,String timestamp) {
        this.path = path;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(timestamp));
        this.date = getDateFromTimeStamp(Long.parseLong(timestamp));
        setDataFromPath();
    }

    private String getDateFromTimeStamp(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(timestamp * 1000);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        return day+"/"+month+"/"+year;
    }

    private void setDataFromPath() {
        try {
            String[] parentArr = path.split("/");
            String name = parentArr[parentArr.length-1];
            String[] arr = name.split("\\.");
            String ext = arr[arr.length-1];
            this.name = name;
            this.extension = ext;
            if(AudioExtensions.indexOf("|"+ext+"|")!=-1)
                this.type = MediaReourceType.AUDIO;
            else if(PhotoExtensions.indexOf("|"+ext+"|")!=-1)
                this.type = MediaReourceType.PHOTO;
            else if(VideoExtensions.indexOf("|"+ext+"|")!=-1)
                this.type = MediaReourceType.VIDEO;
            else
                this.type = MediaReourceType.NO_EXTENSION;
        }catch (Exception e){
            Log.e(TAG,"Error::"+e.toString());
            this.type = MediaReourceType.NO_EXTENSION;
        }

    }

    public enum MediaReourceType{
        VIDEO,
        PHOTO,
        AUDIO,
        NO_EXTENSION
    }

    public String getDate() {
        return date;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public MediaReourceType getType() {
        return type;
    }

    public void setType(MediaReourceType type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}