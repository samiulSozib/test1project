package com.halffull.vipbettingtips;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {

    public static String KEY_UPDATE_ENABLE="isUpdated";
    public static String KEY_UPDATE_VERSION="version";
    public static String KEY_UPDATE_URL="update_url";

    public interface OnUpdateCheckerListener{
        void onUpdateCheckerListener(String urlApp);
    }

    public static Builder with(Context context)
    {
        return new Builder(context);
    }

    private Context context;
    private OnUpdateCheckerListener onUpdateCheckerListener;

    public UpdateHelper(Context context, OnUpdateCheckerListener onUpdateCheckerListener) {
        this.context = context;
        this.onUpdateCheckerListener = onUpdateCheckerListener;
    }

    public void check(){
        FirebaseRemoteConfig remoteConfig=FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE)){
            String currentVersion=remoteConfig.getString(KEY_UPDATE_VERSION);
            String appVersion=getAppVersion(context);
            String updateURL=remoteConfig.getString(KEY_UPDATE_URL);

            if (!TextUtils.equals(currentVersion,appVersion) && onUpdateCheckerListener!=null){
                onUpdateCheckerListener.onUpdateCheckerListener(updateURL);
            }
        }
    }

    private String getAppVersion(Context context) {
        String result="";
        try {
            result=context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class Builder{
        private Context context;
        private OnUpdateCheckerListener onUpdateCheckerListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateCheck(OnUpdateCheckerListener onUpdateCheckerListener){
            this.onUpdateCheckerListener=onUpdateCheckerListener;
            return this;
        }

        public UpdateHelper build(){
            return new UpdateHelper(context,onUpdateCheckerListener);
        }

        public UpdateHelper check(){
            UpdateHelper updateHelper=build();
            updateHelper.check();

            return updateHelper;
        }
    }

}
