package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Course;
import model.Grade;
import uiapp.magadistudio.com.studypro.CourseDetailActivity;
import uiapp.magadistudio.com.studypro.GradesList;
import uiapp.magadistudio.com.studypro.MainActivity;
import uiapp.magadistudio.com.studypro.R;

/**
 * Created by charbel pc on 2016-07-09.
 */
public class CustomListViewAdapter extends ArrayAdapter<Course> {
    private int layoutResource_;
    private Activity activity_;
    private ArrayList<Course> courseList_ = new ArrayList<>();

    //Constructor.
    public CustomListViewAdapter(Activity act, int resource, ArrayList<Course> data) {
        super(act, resource, data);
        layoutResource_ = resource;
        activity_ = act;
        courseList_ = data;
        notifyDataSetChanged();
    }

    // Methods
    @Override
    public int getCount() {
        return courseList_.size();
    }

    @Override
    public Course getItem(int position) {
        return courseList_.get(position);
    }

    @Override
    public int getPosition(Course item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;
        if (row == null || (row.getTag() == null)) {
            LayoutInflater inflater = LayoutInflater.from(activity_);
            row = inflater.inflate(layoutResource_, null);

            holder = new ViewHolder();
            holder.courseName_ = (TextView) row.findViewById(R.id.courseNameId);
            holder.courseAbv_ = (TextView) row.findViewById(R.id.courseAbvId);
            holder.grade_ = (TextView) row.findViewById(R.id.courseGradeId);
            holder.location_ = (TextView) row.findViewById(R.id.courseLocationId);
            holder.averageGrade_ = (TextView) row.findViewById(R.id.courseGradeId);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        holder.course_ = getItem(position);
        holder.courseName_.setText(holder.course_.getCourseName_());
        holder.courseAbv_.setText(holder.course_.getCourseAbbreviation_());
        holder.grade_.setText(String.valueOf(holder.course_.getCurrentGrade_()));
        holder.location_.setText(holder.course_.getCourseLocation_());
        // On met le average grade.
        DatabaseHandlerGrades dbaGrades = new DatabaseHandlerGrades(getContext());
        ArrayList<Grade> gradesList = dbaGrades.getGrades();
        ArrayList<Grade> realGradeList = new ArrayList<>();
        for(int i = 0; i < gradesList.size(); i++)
        {
            if(holder.course_.getCourseAbbreviation_().equals(gradesList.get(i).getGradeCourse_()))
            {
                realGradeList.add(gradesList.get(i));
            }
        }
        double gradeTotal = 0;
        double ponderationTotal = 0;
        for (int i = 0; i < realGradeList.size(); i++)
        {
            ponderationTotal += realGradeList.get(i).getPonderation_();
            gradeTotal += (realGradeList.get(i).getGrade_() * realGradeList.get(i).getPonderation_()) / 100;
        }
        double average = (gradeTotal / ponderationTotal) * 100;
        if(ponderationTotal == 0) {
            holder.averageGrade_.setText("-");
        }
        else {
            holder.averageGrade_.setText(String.format("%.2f", (double)average));
        }
        holder.course_.setCurrentGrade_(average);

        // Qd tu click un row tu vas sur le details page
        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_, GradesList.class);
                Bundle mBundle = new Bundle(); // Un Bundle est un object dans lequel on peu mettre dotre object dedans.
                // Reminder. on avai mis Foods comme enfant de serializableé
                mBundle.putSerializable("userObj", finalHolder.course_); // La on a pu freeze le food object pour lutiliser dans un otre activity.
                i.putExtras(mBundle);
                // Ici on met activity.startActivity(i) pcq on est ds un CustomListViewAdapter et non dans un activity
                // so tu peux pas seulement faire startAcitity(i).
                activity_.startActivity(i);
            }
        });
        // TRES IMPORTANT DE RETURN LE ROW ICI.
        return row;
    }
    // Le ViewHolder a le mm attribut que le class Food.
    public class ViewHolder {
        Course course_;
        TextView courseName_;
        TextView courseAbv_;
        TextView grade_;
        TextView location_;
        TextView averageGrade_;
    }
}
