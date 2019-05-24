package web.id.azammukhtar.peka;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class RestartService extends BroadcastReceiver {
    private static final String TAG = "RestartService";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(TAG, "onReceive: service tried to stop");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, BackgroundService.class));
            } else {
                context.startService(new Intent(context, BackgroundService.class));
            }
        }

        Log.i(TAG, "onReceive: service tried to stop");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, BackgroundService.class));
        } else {
            context.startService(new Intent(context, BackgroundService.class));
        }
    }
}
