package uiapp.magadistudio.com.studypro;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DatabaseHandler;
import model.Course;

public class CoursesActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Course> dbCourses = new ArrayList<>();
    private CustomListViewAdapter courseAdapter;
    private ListView listView;
    private Button addCourseButton;
    private Button deleteCourseButton;
    private Course myCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        // Changes the toolbar
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbarActivityCourses);
        setSupportActionBar(my_toolbar);        // le rend compatible avc les vieille version de android.

        getSupportActionBar().setTitle("My courses");
        getSupportActionBar().setIcon(R.drawable.ic_agenda);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dba = new DatabaseHandler(getApplicationContext());

        listView = (ListView) findViewById(R.id.listViewId);
        addCourseButton = (Button) findViewById(R.id.addCourseButtonId);

        refreshData();

        // Clicking on add course
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(CoursesActivity.this, CourseDetailActivity.class));
                }
                catch(Exception e ){e.printStackTrace();}
            }
        });

    }

    // Methods.
    private void refreshData(){

        dbCourses.clear();


        ArrayList<Course> coursesFromDb = dba.getCourses();

        Log.v("Le bahay a ete crazer", "LE bahaY CRAZZZZZZZZZZZZZZZZ");
        int shit = coursesFromDb.size();
        String a = "" + shit;
        Log.v("Le bahay a ete crazer", a);
        for(int i = 0; i < coursesFromDb.size(); i++)
        {
            String name = coursesFromDb.get(i).getCourseName_();
            String abreviation = coursesFromDb.get(i).getCourseAbbreviation_();
            String location = coursesFromDb.get(i).getCourseLocation_();
            double currentGrade = coursesFromDb.get(i).getCurrentGrade_();
            double passingGrade = coursesFromDb.get(i).getPassingGrade_();
            int courseId = coursesFromDb.get(i).getCourseId_();

            Log.v("LE BAGAY CRAZ PR LA " , " MOUNE FOUU");

            myCourse = new Course();
            myCourse.setCourseName_(name);
            myCourse.setCourseAbbreviation_(abreviation);
            myCourse.setCourseLocation_(location);
            myCourse.setCurrentGrade_(currentGrade);
           // myCourse.setPassingGrade(passingGrade);
            myCourse.setCourseId_(courseId);

            dbCourses.add(myCourse);

            Log.v("LE BAGAY CRAZ PR LA " , " debugggg");
        }
        dba.close();

        // setup adapter.
        courseAdapter = new CustomListViewAdapter(CoursesActivity.this, R.layout.list_row, dbCourses);
        listView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
    }


}
