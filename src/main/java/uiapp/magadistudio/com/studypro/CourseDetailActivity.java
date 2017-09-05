package uiapp.magadistudio.com.studypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Course;

public class CourseDetailActivity extends AppCompatActivity {

    private EditText courseName, courseAbbreviation, location;
    private Button addButtonDetails;
    private DatabaseHandler dba;
    private String courseNameStr, courseAbvStr, locationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        setTitle("Add a course");

        Log.v("LE BAGAY CRAZ PR LA " , " salut bro");
        dba = new DatabaseHandler(CourseDetailActivity.this);
        courseName = (EditText) findViewById(R.id.editCourseNameId);
        courseAbbreviation = (EditText) findViewById(R.id.editCourseAbbreviationId);
        location = (EditText) findViewById(R.id.editLocationId);
        addButtonDetails = (Button) findViewById(R.id.editButtonId);



        addButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseNameStr = courseName.getText().toString();
                courseAbvStr = courseAbbreviation.getText().toString();
                locationStr = location.getText().toString();

                if (courseNameStr.equals("") || courseAbvStr.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill the non optional fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Course course = new Course();

                    course.setCourseName_(courseNameStr);
                    course.setCourseAbbreviation_(courseAbvStr);
                    course.setCourseLocation_(locationStr);

                    // Add the course to the database.
                    dba.addCourse(course);
                    dba.close();

                    // return to CourseActivity.

                    startActivity(new Intent(CourseDetailActivity.this, CoursesActivity.class));

                }
            }
        });
    }
}

