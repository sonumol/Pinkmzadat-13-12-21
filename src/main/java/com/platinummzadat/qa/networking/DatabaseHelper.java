package com.platinummzadat.qa.networking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "PRofile_image";
    private static final String COL1 = "ID";
    private static final String USER_ID = "user_id";
    private static final String NAME = "name";
    private static final String PATH = "path";


    Double total1=0.0;
    Double x;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_ID +" TEXT ,"+NAME+" TEXT,"+PATH+",TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String userid, String name, String path) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userid);
        contentValues.put(NAME, name);
        contentValues.put(PATH, path);





        Log.d(TAG, "addData: Adding " + userid + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor checkdata(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String q="SELECT name FROM invoice_table ";
        Cursor data1= db.rawQuery(q, null);
        return data1;

    }


    private boolean isRecordExistInDatabase(String tableName,String field,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + field + "=" + name, null);
        if (c.moveToFirst()) {
            //Record exist
            c.close();
            return true;
        }
        //Record available
        c.close();
        return false;
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        Log.d(TAG, "updateName: query: " + data.getString(3));
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
    // * @param name
     * @return
     */
    public Cursor getItemID(String userid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID + "=" + userid;

        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "updateName1: query: " + query);
        return data;
    }
    public Cursor getsum()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String query="select (SUM(total))  from "+TABLE_NAME;
        // Cursor res = db.rawQuery( "select (SUM(salary)) AS fullname from "+CONTACTS_TABLE_NAME, null );
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    /**
     * Updates the name field
     * @param user_id
     * @param name
     * @param path
     */
    public void updateName(String user_id, String name, String path){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + PATH +
                " = '" + path + "' WHERE " + USER_ID + " = '" + user_id + "'" ;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + path);
        db.execSQL(query);
    }
    public void updatetotal( Double v, int tot,int position){
        SQLiteDatabase db = this.getWritableDatabase();



        String query = "UPDATE " + TABLE_NAME + " SET " + PATH +
                " = '" + v + "'";

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + x);
        db.execSQL(query);


    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + USER_ID + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    public void deleteinvoice(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        //Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    public void deleteallrecord()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

    }
}
