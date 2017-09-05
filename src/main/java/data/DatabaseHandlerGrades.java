package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Course;
import model.Grade;

/**
 * Created by charbel pc on 2016-07-15.
 */
public class DatabaseHandlerGrades extends SQLiteOpenHelper {

    public DatabaseHandlerGrades(Context context) {
        super(context, Constants.DATABASE_NAME_GRADES, null, Constants.DATABASE_VERSION);
    }

    private final ArrayList<Grade> gradeList = new ArrayList<>();

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_GRADES + " ("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.GRADE_NAME
                + " TEXT, " + Constants.GRADE_COURSE + " TEXT, " + Constants.GRADE_WEIGHT + " LONG, " + Constants.GRADE_DATE + " LONG, " + Constants.GRADE_SCORE + " LONG);";
        db.execSQL(CREATE_TABLE); // On create un table en ecrivant un SQL command dans lfond.
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_GRADES);
        // Create a new one.
        onCreate(db);
    }

    // LES METHODS.
    // Get total items saved
    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT * FROM " + Constants.TABLE_NAME_GRADES;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();
        cursor.close();
        return totalItems;
    }


    public double getTotalWeight() {        // COMME CA QD LA PONDERATION TOTALE SERA 100 TU POURRA PLUS ADD DES BAHAY.
        double weight = 0;
        SQLiteDatabase dba = this.getReadableDatabase();
        // La somme dune certaine colonne (Ici celle des calories.)
        String query = "SELECT SUM(" + Constants.GRADE_WEIGHT + " ) " +
                "FROM " + Constants.TABLE_NAME_GRADES;
        Cursor cursor = dba.rawQuery(query, null);
        if (cursor.moveToFirst())        // Si il ya kelkechose ds lecursor
        {
            weight = cursor.getInt(0);
        }
        cursor.close();
        return weight;
    }

    public double getTotalScore() {        // COMME CA QD LA PONDERATION TOTALE SERA 100 TU POURRA PLUS ADD DES BAHAY.
        double score = 0;
        SQLiteDatabase dba = this.getReadableDatabase();
        // La somme dune certaine colonne (Ici celle des calories.)
        String query = "SELECT SUM(" + Constants.GRADE_SCORE + " ) " +
                "FROM " + Constants.TABLE_NAME_GRADES;
        Cursor cursor = dba.rawQuery(query, null);
        if (cursor.moveToFirst())        // Si il ya kelkechose ds lecursor
        {
            score = cursor.getInt(0);
        }
        cursor.close();
        return score;
    }

    // Delete food item
    public void deleteGrade(String name) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME_GRADES, Constants.GRADE_NAME +
                " = ?", new String[]{name});

        dba.close();

        Log.v("Deleted Grade Item", "YES!!");
    }

    // Add food.
    public void addGrade(Grade grade) {
        SQLiteDatabase dba = this.getReadableDatabase();
        // Les content values cest basically comme un HashMap.
        ContentValues values = new ContentValues();
        values.put(Constants.GRADE_NAME, grade.getGradeName_());
        values.put(Constants.GRADE_WEIGHT, grade.getPonderation_());
        values.put(Constants.GRADE_DATE, System.currentTimeMillis());
        values.put(Constants.GRADE_SCORE, grade.getGrade_());
        values.put(Constants.GRADE_COURSE, grade.getGradeCourse_());

        dba.insert(Constants.TABLE_NAME_GRADES, null, values);

        Log.v("Added Grade Item", "YES!!");
    }

    public ArrayList<Grade> getGrades() {
        gradeList.clear();
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(Constants.TABLE_NAME_GRADES, new String[]{Constants.KEY_ID, Constants.GRADE_NAME, Constants.GRADE_WEIGHT,
                Constants.GRADE_SCORE, Constants.GRADE_DATE, Constants.GRADE_COURSE}, null, null, null, null, Constants.GRADE_DATE + " DESC ");

        if (cursor.moveToFirst()) {
            do {
                Grade grade = new Grade();
                grade.setGradeName_(cursor.getString(cursor.getColumnIndex(Constants.GRADE_NAME)));
                grade.setPonderation_(cursor.getDouble(cursor.getColumnIndex(Constants.GRADE_WEIGHT)));
                //  grade.setGradeDate_(cursor.getString(cursor.getColumnIndex(Constants.GRADE_DATE)));
                grade.setGradeCourse_(cursor.getString(cursor.getColumnIndex(Constants.GRADE_COURSE)));
                grade.setGrade_(cursor.getDouble(cursor.getColumnIndex(Constants.GRADE_SCORE)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.GRADE_DATE))).getTime());
                grade.setGradeDate_(date);

                gradeList.add(grade);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dba.close();
        return gradeList;
    }
}