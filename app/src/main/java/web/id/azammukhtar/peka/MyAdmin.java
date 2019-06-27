package web.id.azammukhtar.peka;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import es.dmoral.toasty.Toasty;

public class MyAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toasty.info(context,"Device admin : Enable", Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toasty.info(context,"Device admin : Disable", Toasty.LENGTH_SHORT).show();
    }
}
