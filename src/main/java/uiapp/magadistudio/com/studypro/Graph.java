package uiapp.magadistudio.com.studypro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import data.DatabaseHandler;
import data.DatabaseHandlerGrades;
import model.Grade;

public class Graph extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        String courseAbv = getIntent().getStringExtra("name");
        setTitle("Grades line graph");

        DatabaseHandlerGrades dba = new DatabaseHandlerGrades(getApplicationContext());

        ArrayList<Grade> listGrades = dba.getGrades();
        ArrayList<Grade> listGradesReal = new ArrayList<Grade>();

        for(int i = 0; i < listGrades.size(); i++)
        {
            if(listGrades.get(i).getGradeCourse_().equals(courseAbv))
            {
                listGradesReal.add(listGrades.get(i));
            }
        }

        /*
        //Met en ordre de date.
        Collections.sort(listGradesReal, new Comparator<Grade>() {
            @Override
            public int compare(Grade grade2, Fruit fruit1)
            {

                return  fruit1.fruitName.compareTo(fruit2.fruitName);
            }
        });
*/

        double y,x;
        x = 0;
        GraphView graph = (GraphView) findViewById(R.id.graphId);
        series = new LineGraphSeries<DataPoint>();

        //listGradesReal.get(0).getPonderation_();

        for(int i = 0 ; i < 50; i++)
        {
            y = 2*x;
            series.appendData(new DataPoint(x,y), true, 100);
            x += 1;
        }

        graph.addSeries(series);

        Viewport viewport = graph.getViewport();
        viewport.setXAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMinX(0);
        viewport.setMaxX(100);
        viewport.setMaxY(100);


    }
}
