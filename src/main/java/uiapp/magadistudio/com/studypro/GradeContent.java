package uiapp.magadistudio.com.studypro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import data.DatabaseHandler;
import data.DatabaseHandlerGrades;
import model.Course;
import model.Grade;

public class GradeContent extends AppCompatActivity {

    private Button deleteGrade;
    private Grade grade;
    private DatabaseHandler dbaCourses;
    private ArrayList<Course> courseList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_content);

        intent = new Intent(GradeContent.this, GradesList.class);
        dbaCourses = new DatabaseHandler(getApplicationContext());
        deleteGrade = (Button) findViewById(R.id.deleteGradeButtonId);
        grade = (Grade) getIntent().getSerializableExtra("obj");
        courseList = dbaCourses.getCourses();

        Log.v("getGradeCourse du grade", grade.getGradeCourse_());

        // On search les cours dans le database pour find celui qui correspond au cours.
        boolean courseFound = false;
        for(int i = 0; i < courseList.size() && !courseFound; i++)
        {
            if(courseList.get(i).getCourseAbbreviation_().equals(grade.getGradeCourse_()))           // Si on trouve le cours dans le database.
            {
                courseFound = true;
                intent.putExtra("userObj", courseList.get(i));
                Log.v("getCourseAbv:  ", courseList.get(i).getCourseAbbreviation_());
            }
        }
        deleteGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GradeContent.this);
                alert.setTitle("Delete?");
                alert.setMessage("Are you sure you want to delete this grade : " + grade.getGradeName_() +"?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandlerGrades dba = new DatabaseHandlerGrades(getApplicationContext());
                        dba.deleteGrade(grade.getGradeName_());
                        Toast.makeText(getApplicationContext(), "Grade Successfully Deleted! ", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                        // remove this activity from activity stack.
                        GradeContent.this.finish();
                    }
                });
                alert.show();
            }
        });
    }

}
