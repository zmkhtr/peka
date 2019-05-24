package web.id.azammukhtar.peka;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class BackgroundService extends Service {
    private static final String TAG = "BackgroundService";

    private BroadcastReceiver receiver;
    public int counter = 0;
    LockScreenActivity obj = new LockScreenActivity();
    Context mContext;
    Apply_password_on_appDatabase db = new Apply_password_on_appDatabase(this);

    static int flag = 0;
    static int flag2 = 0;

    String current_app = "";
    public BackgroundService(){}

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeround();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeround() {
        String NOTIFICATION_CHANNEL_ID = "example.permanence";
        String channelName = "Background Service";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, RestartService.class);
        this.sendBroadcast(broadcastIntent);
    }

    private Timer timer;
    private TimerTask timerTask;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (flag == 0) {
                    Cursor res = db.getAllData();

                    ArrayList<String> name = new ArrayList<>();
                    ArrayList<String> pass = new ArrayList<>();

                    while (res.moveToNext()){
                        Log.d(TAG, "run: " + "=====" + printForegroundTask());
                        name.add(res.getString(0));
                        pass.add(res.getString(1));
                    }

                    if (name.contains(printForegroundTask())){
                        flag = 1;

                        current_app = printForegroundTask();
                        Intent lockIntent = new Intent(mContext, LockScreenActivity.class);
                        lockIntent.putExtra("name", pass.get(name.indexOf(printForegroundTask())));
                        lockIntent.putExtra("pack", printForegroundTask());
                        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(lockIntent);
                    }
                }
                if((!printForegroundTask().equals("web.id.azammukhtar.peka")) && flag2 == 0) {
                        if ((!printForegroundTask().equals(current_app))){
                            flag = 0;
                        }
                }

                if(printForegroundTask().equals("web.id.azammukhtar.peka")) {
                    flag = 2;
                }
            }
        };
        timer.schedule(timerTask,0,100);
    }

    public void stoptimertask(){
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String printForegroundTask(){
        String currentApp = "NULL";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*1000,time);
            if (appList != null && appList.size() > 0 ){
                SortedMap<Long,UsageStats> sortedMap = new TreeMap<>();
                for (UsageStats usageStats : appList){
                    sortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (sortedMap != null && !sortedMap.isEmpty()){
                    currentApp = sortedMap.get(sortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = activityManager.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
        return currentApp;
    }
}
