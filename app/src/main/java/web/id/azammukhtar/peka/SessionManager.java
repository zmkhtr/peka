package web.id.azammukhtar.peka;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // Shared preferences file name1
    private static final String PREF_NAME = "peka";
    private static final String PASSWORD = "PASSWORD";
    private static final String ANSWER = "ANSWER";

    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setPassword(String password) {
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public void setAnswer(String answer) {
        editor.putString(ANSWER, answer);
        editor.commit();
    }


    public String getPassword(){
        return pref.getString(PASSWORD, null);
    }
    public String getAnswer(){
        return pref.getString(ANSWER, null);
    }
}


