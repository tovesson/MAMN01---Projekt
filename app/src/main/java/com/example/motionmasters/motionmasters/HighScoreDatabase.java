package com.example.motionmasters.motionmasters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HighScoreDatabase {
    private DecimalFormat decimalFormat;
    private int place;
    private String suffix;
    private String scoreString;

    myDbHelper myhelper;
    public HighScoreDatabase(Context context)
    {
        myhelper = new myDbHelper(context);
        decimalFormat = new DecimalFormat("0.00");
    }

    public long insertData(String name, Double score, String game)
    {

        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.SCORE, score);
        contentValues.put(myDbHelper.GAME, game);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public ArrayList<String> getData(String gameName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.SCORE, myhelper.GAME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,myDbHelper.SCORE + " DESC");
        ArrayList<String> list = new ArrayList<String>();
        place = 1;
        while (cursor.moveToNext())
        {
            if((cursor.getString(cursor.getColumnIndex(myDbHelper.GAME))).equals(gameName)) {

                int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String score = cursor.getString(cursor.getColumnIndex(myDbHelper.SCORE));
                String game = cursor.getString(cursor.getColumnIndex(myDbHelper.GAME));
                double scoreDouble = Double.parseDouble(score);
                if((cursor.getString(cursor.getColumnIndex(myDbHelper.GAME))).equals("Blowmaster")){
                    scoreString = decimalFormat.format(scoreDouble/1000) + "seconds";
                }else {
                    scoreString = String.format("%.2f", scoreDouble) + "m";
                }
                String leftText = place+". "+ name;
                String rightText = scoreString;
                final String resultText = leftText + "  " + rightText;
                final SpannableString styledResultText = new SpannableString(resultText);
                styledResultText.setSpan((new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE)), leftText.length() + 2, leftText.length() + 2 +rightText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                list.add(styledResultText.toString());
                place ++;
            }
        }
        return list;
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase1";    // HighScoreDatabase Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // HighScoreDatabase Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String SCORE= "Score";    // Column III
        private static final String GAME= "Game";    // Column IV
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+SCORE+" VARCHAR(225) ," +GAME+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                //Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
}
