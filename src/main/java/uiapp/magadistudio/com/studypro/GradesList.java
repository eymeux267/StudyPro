package uiapp.magadistudio.com.studypro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import data.DatabaseHandler;
import data.DatabaseHandlerGrades;
import data.ListViewAdapterGrades;
import model.Course;
import model.Grade;

public class GradesList extends AppCompatActivity {

    private  Button seeGraphButton;
    private Button deleteCourseButton;
    private Button addGradeButton;
    private Course course = new Course();
    private ListView listView;
    private ListViewAdapterGrades gradesAdapter;
    private Grade myGrade;
    private ArrayList<Grade> dbGrades = new ArrayList<>();
    private ArrayList<Grade> gradesArray = new ArrayList<>();
    private DatabaseHandlerGrades dba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades_list);

        course = (Course) getIntent().getSerializableExtra("userObj"); // On get le course quon a passer dans le intent.

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.toolbarGradesList);
        setSupportActionBar(my_toolbar);        // le rend compatible avc les vieille version de android.

        getSupportActionBar().setTitle("Grades for " + course.getCourseAbbreviation_());
        getSupportActionBar().setIcon(R.drawable.ic_agenda);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        deleteCourseButton = (Button) findViewById(R.id.deleteCourseButtonId);
        addGradeButton = (Button) findViewById(R.id.addGradeButtonId);
        listView = (ListView) findViewById(R.id.listViewGradesId);


        Log.v("Grade IDS: ", " JE SUIS ENTRER DANS GRADES LIST");


        seeGraphButton = (Button) findViewById(R.id.graphButtonId);


        seeGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(GradesList.this, Graph.class);
                theIntent.putExtra("name", course.getCourseAbbreviation_());
                startActivity(new Intent(GradesList.this, Graph.class));
            }
        });

        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GradesList.this);
                alert.setTitle("Delete?");
                alert.setMessage("Are you sure you want to delete this course : " + course.getCourseAbbreviation_() +"?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        dba.deleteCourse(course.getCourseId_());
                        Toast.makeText(getApplicationContext(), "Course Successfully Deleted!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(GradesList.this, CoursesActivity.class));

                        // remove this activity from activity stack.
                        GradesList.this.finish();
                    }
                });
                alert.show();
            }
        });


        addGradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GradesList.this, GradeDetails.class);
                intent.putExtra("userObj",course);
                startActivity(intent);  // Go to GradeDetails class.
            }
        });

        refreshData();

    }

    private void refreshData() {

       dbGrades.clear();        // CA CRASH ICI WTF MAN.

        dba = new DatabaseHandlerGrades(getApplicationContext());

        ArrayList<Grade> gradesFromDb = dba.getGrades();


        for (int i = 0; i < gradesFromDb.size(); i++)
        {
            // If it is the right course

            String course = gradesFromDb.get(i).getGradeCourse_();

            Course goodCourse = (Course) getIntent().getSerializableExtra("userObj");

         //   Log.v("LE BAGAY CRAZ PR LA " , goodCourse.getCourseAbbreviation_());
            //if(course == goodCourse.getCourseAbbreviation_()) {

                String name = gradesFromDb.get(i).getGradeName_();
                String dateText = gradesFromDb.get(i).getGradeDate_();
                double weight = gradesFromDb.get(i).getPonderation_();
                double score = gradesFromDb.get(i).getGrade_();
                int gradeId = gradesFromDb.get(i).getGradeId_();

                Log.v("Grade IDS: ", Integer.toString(gradesFromDb.size()));

                myGrade = new Grade();
                myGrade.setGradeName_(name);
                myGrade.setGradeId_(gradeId);
                myGrade.setGrade_(score);
                myGrade.setGradeCourse_(course);
                myGrade.setGradeDate_(dateText);

            dbGrades.add(myGrade);
           // }
        }

        Log.v("Course name: ",  course.getCourseAbbreviation_());
        Log.v("getGradeCourse: ", dbGrades.get(0).getGradeCourse_());
        Log.v("size au debut ", Integer.toString(dbGrades.size()));

        for(int i = 0; i < dbGrades.size(); i++)
        {
            if(dbGrades.get(i).getGradeCourse_().equals(course.getCourseAbbreviation_()))
            {
                gradesArray.add(dbGrades.get(i));
            }
        }
        Log.v("size a la fin ", Integer.toString(gradesArray.size()));

        dba.close();
        gradesAdapter = new ListViewAdapterGrades(GradesList.this, R.layout.grades_row, gradesArray);
        listView.setAdapter(gradesAdapter);   // LE PROBLEME EST ICI BRO!!!!!!!!!!!!!!!!!!!!!!!!! je croi dbGrades nest pas supposer etre null mai idk.
        gradesAdapter.notifyDataSetChanged();

    }
}
