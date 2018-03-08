package strilets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "football_league";
    public static final String TABLE_MATCHES = "matches";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_TEAM1 = "nameTeam1";
    public static final String COLUMN_NAME_TEAM2 = "nameTeam2";
    public static final String COLUMN_GOAL_TEAM1 = "goalTeam1";
    public static final String COLUMN_GOAL_TEAM2 = "goalTeam2";
    public static final String COLUMN_NUMBER_MATCH = "numberMatch";

    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_MATCHES + "( "
                + COLUMN_ID + " integer primary key, "
                + COLUMN_NAME_TEAM1 + " text, "
                + COLUMN_NAME_TEAM2 + " text,"
                + COLUMN_GOAL_TEAM1 + " text, "
                + COLUMN_GOAL_TEAM2 + " text, "
                + COLUMN_NUMBER_MATCH + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MATCHES);
        onCreate(db);
    }

    void addMatch(Match match) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(COLUMN_NAME_TEAM1, match.getNameTeam1());
        contentValue.put(COLUMN_NAME_TEAM2, match.getNameTeam2());
        contentValue.put(COLUMN_GOAL_TEAM1, match.getGoalTeam1());
        contentValue.put(COLUMN_GOAL_TEAM2, match.getGoalTeam2());
        contentValue.put(COLUMN_NUMBER_MATCH, match.getNumberMatch());
        db.insert(TABLE_MATCHES, null, contentValue);
        db.close();
    }

    public void deleteMatches() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + TABLE_MATCHES);
        onCreate(db);
        db.close();
    }

    public List<Match> getAllMatches() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Match> matchesList = new ArrayList<Match>();
        String selectQuery = "SELECT  * FROM " + TABLE_MATCHES;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(Integer.parseInt(cursor.getString(0)));
                match.setNameTeam1(cursor.getString(1));
                match.setNameTeam2(cursor.getString(2));
                match.setGoalTeam1(cursor.getString(3));
                match.setGoalTeam2(cursor.getString(4));
                match.setNumberMatch(cursor.getInt(5));
                matchesList.add(match);
            } while (cursor.moveToNext());
        }

        return matchesList;
    }

    public int updateTask(Match match) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GOAL_TEAM1, match.getGoalTeam1());
        values.put(COLUMN_GOAL_TEAM2, match.getGoalTeam2());
        return db.update(TABLE_MATCHES, values, COLUMN_NUMBER_MATCH + " = ?", new String[] { String.valueOf(match.getNumberMatch()) });
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
