package uiapp.magadistudio.com.studypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView myCoursesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(my_toolbar);        // le rend compatible avc les vieille version de android.

        getSupportActionBar().setTitle("Study Pro");
        getSupportActionBar().setIcon(R.drawable.ic_agenda);



        myCoursesButton = (ImageView) findViewById(R.id.myCoursesIconId);
        // If you click on my courses
        myCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CoursesActivity.class));
            }
        });

    }
}
