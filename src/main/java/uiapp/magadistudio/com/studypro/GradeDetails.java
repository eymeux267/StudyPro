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
import data.DatabaseHandlerGrades;
import model.Course;
import model.Grade;

public class GradeDetails extends AppCompatActivity {

    private DatabaseHandlerGrades dba;
    private EditText gradeName, gradeScore, gradeWeight;
    private Button theButton;
    private String gradeNameStr, gradeScoreStr, gradeWeightStr, gradeCourseStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_details);
        final Course course = (Course) getIntent().getSerializableExtra("userObj");
        gradeCourseStr = course.getCourseAbbreviation_();
        setTitle("Add a grade");


        dba = new DatabaseHandlerGrades(GradeDetails.this);


        gradeName = (EditText) findViewById(R.id.editTextGradeNameId);
        gradeScore = (EditText) findViewById(R.id.editTextGradeScoreId);
        gradeWeight = (EditText) findViewById(R.id.editTextWeightId);
        theButton = (Button) findViewById(R.id.buttonGradeAddId);

        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gradeNameStr = gradeName.getText().toString();
                gradeScoreStr = gradeScore.getText().toString();
                gradeWeightStr = gradeWeight.getText().toString();

                double gradeScoreDouble = Double.parseDouble(gradeScoreStr);
                double gradeWeightDouble = Double.parseDouble(gradeWeightStr);

                if(gradeWeightStr == "" || gradeScoreStr == "" || gradeNameStr == "")
                {
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
                }

                else if(gradeWeightDouble > 100)
                {
                    Toast.makeText(getApplicationContext(), "Grade weight can't be more than 100", Toast.LENGTH_LONG).show();
                }

                else{
                    Grade grade = new Grade();

                    grade.setGradeName_(gradeNameStr);
                    grade.setGrade_(gradeScoreDouble);
                    grade.setPonderation_(gradeWeightDouble);
                    grade.setGradeCourse_(gradeCourseStr);
                    dba.addGrade(grade);
                    dba.close();

                    Intent i = new Intent(GradeDetails.this, GradesList.class);
                    i.putExtra("userObj2", false);
                    i.putExtra("userObj", course);
                    startActivity(i);
                    Log.v("LE BAGAY CRAZ PR LA " , " penis");
                }
            }
        });
    }
}
