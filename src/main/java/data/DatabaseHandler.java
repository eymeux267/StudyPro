package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;

import model.Course;

/**
 * Created by charbel pc on 2016-07-10.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    private final ArrayList<Course> courseList = new ArrayList<>();

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.COURSE_NAME
                + " TEXT, " + Constants.COURSE_ABBREVIATION_NAME + " TEXT, " + Constants.COURSE_LOCATION_NAME + " TEXT, " + Constants.GRADE_NAME + " DOUBLE);";
        db.execSQL(CREATE_TABLE); // On create un table en ecrivant un SQL command dans lfond.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        // Create a new one.
        onCreate(db);
    }

    // LES METHODS.
    // Get total items saved
    public int getTotalItems() {
        int totalItems = 0;
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();
        cursor.close();
        return totalItems;
    }

    // Delete course item
    public void deleteCourse(int id) {
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID +
                " = ?", new String[]{String.valueOf(id)});

        dba.close();
    }

    // Add course.
    public void addCourse(Course course) {
        SQLiteDatabase dba = this.getReadableDatabase();
        // Les content values cest basically comme un HashMap.
        ContentValues values = new ContentValues();
        values.put(Constants.COURSE_NAME, course.getCourseName_());
        values.put(Constants.COURSE_ABBREVIATION_NAME, course.getCourseAbbreviation_());
        values.put(Constants.GRADE_NAME, course.getCurrentGrade_());
        //values.put(Constants.PASSING_GRADE_NAME, course.getPassingGrade_());
        values.put(Constants.COURSE_LOCATION_NAME, course.getCourseLocation_());

        dba.insert(Constants.TABLE_NAME, null, values);

        Log.v("Added Food Item", "YES!!");
    }


    // Get all courses (Create an ArrayList of courses)
    public ArrayList<Course> getCourses() {

        courseList.clear();
        SQLiteDatabase dba = this.getReadableDatabase();
        // Cursor est used pour pointer vers des colonnes dun database pour comme ca pour voir les extraire ailleurs.

        Log.v("MOUNE", "CRAZER ");
        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.COURSE_NAME,
                        Constants.COURSE_ABBREVIATION_NAME, Constants.COURSE_LOCATION_NAME, Constants.GRADE_NAME}, null, null, null, null, Constants.COURSE_NAME + " DESC ");
        // loop through.
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseName_(cursor.getString(cursor.getColumnIndex(Constants.COURSE_NAME)));
                course.setCourseAbbreviation_(cursor.getString(cursor.getColumnIndex(Constants.COURSE_ABBREVIATION_NAME)));
                course.setCourseId_(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                course.setCourseLocation_(cursor.getString(cursor.getColumnIndex(Constants.COURSE_LOCATION_NAME)));
                course.setCurrentGrade_(cursor.getDouble(cursor.getColumnIndex(Constants.GRADE_NAME)));
              //  course.setPassingGrade(cursor.getDouble(cursor.getColumnIndex(Constants.PASSING_GRADE_NAME)));

                courseList.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        dba.close();

        return courseList;
    }
}



