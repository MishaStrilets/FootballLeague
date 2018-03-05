package strilets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "football_league";
    public static final String TABLE_MATCHES = "matches";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_NAME_TEAM1 = "nameTeam1";
    public static final String COLUMN_NAME_TEAM2 = "nameTeam2";
    public static final String COLUMN_GOAL_TEAM1 = "goalTeam1";
    public static final String COLUMN_GOAL_TEAM2 = "goalTeam2";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MATCHES + "(" + COLUMN_ID
                + " integer primary key, " + COLUMN_NUMBER + " integer, " + COLUMN_NAME_TEAM1 + " text, "
                +  COLUMN_NAME_TEAM2 + " text," + COLUMN_GOAL_TEAM1 + " integer, "
                + COLUMN_GOAL_TEAM2 + " integer " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MATCHES);
        onCreate(db);
    }

    void addMatches(ArrayList<Match> matches) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            ContentValues contentValue = new ContentValues();
            contentValue.put(COLUMN_NUMBER, match.getMatch());
            contentValue.put(COLUMN_NAME_TEAM1, match.getNameTeam1());
            contentValue.put(COLUMN_NAME_TEAM2, match.getNameTeam2());
            contentValue.put(COLUMN_GOAL_TEAM1, match.getGoalTeam1());
            contentValue.put(COLUMN_GOAL_TEAM2, match.getGoalTeam2());
            db.insert(TABLE_MATCHES, null, contentValue);
        }

        db.close();
    }

    public void deleteMatches() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + TABLE_MATCHES);
        onCreate(db);
        db.close();
    }

    public boolean checkMatches() {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MATCHES;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount() == 0){
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }
}
