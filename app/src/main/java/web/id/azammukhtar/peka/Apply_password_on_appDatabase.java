package web.id.azammukhtar.peka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Apply_password_on_appDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "logg.db";
    public static final String TABLE_NAME = "apps";
    public static final String col1 = "package_name";
    public static final String col2 = "password";

    public Apply_password_on_appDatabase(Context context){
        super(context, DATABASE_NAME, null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( package_name TEXT PRIMARY KEY , password TEXT  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String name, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,name);
        contentValues.put(col2,pass);

        long result =  db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,pass);
        db.update(TABLE_NAME,contentValues,col1+"=?", new String[]{name});
        return true;
    }

    public Integer deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, col1+"=?", new String[]{name});
        return i;
    }
}
