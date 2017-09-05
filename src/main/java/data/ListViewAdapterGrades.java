package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Course;
import model.Grade;
import uiapp.magadistudio.com.studypro.GradeContent;
import uiapp.magadistudio.com.studypro.GradeDetails;
import uiapp.magadistudio.com.studypro.GradesList;
import uiapp.magadistudio.com.studypro.R;

/**
 * Created by charbel pc on 2016-07-15.
 */
public class ListViewAdapterGrades extends ArrayAdapter<Grade> {

    private int layoutResource_;
    private Activity activity_;
    private ArrayList<Grade> gradesList_ = new ArrayList<>();

    //Constructor.
    public ListViewAdapterGrades(Activity act, int resource, ArrayList<Grade> data) {
        super(act, resource, data);
        layoutResource_ = resource;
        activity_ = act;
        gradesList_ = data;
        notifyDataSetChanged();
    }


    // Methods
    @Override
    public int getCount() {
        return gradesList_.size();
    }

    @Override
    public Grade getItem(int position) {
        return gradesList_.get(position);
    }

    @Override
    public int getPosition(Grade item) {
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
            holder.gradeName_ = (TextView) row.findViewById(R.id.gradeNameId);
            holder.gradeWeight_ = (TextView) row.findViewById(R.id.gradeWeightId);
            holder.gradeScore_ = (TextView) row.findViewById(R.id.gradeScoreId);
            holder.gradeDate_ = (TextView) row.findViewById(R.id.gradeDateId);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        holder.grade_ = getItem(position);
        holder.gradeName_.setText(holder.grade_.getGradeName_());

        DatabaseHandlerGrades dbaNew = new DatabaseHandlerGrades(getContext());
        ArrayList<Grade> laListe = dbaNew.getGrades();
        for(int i = 0; i < laListe.size(); i++)
        {
            if(laListe.get(i).getGradeCourse_().equals(holder.grade_.getGradeCourse_()) && laListe.get(i).getGradeName_().equals(holder.grade_.getGradeName_()))
            {
                holder.grade_.setPonderation_(laListe.get(i).getPonderation_());
            }
        }

        holder.gradeWeight_.setText(String.format("%.2f", (double)holder.grade_.getPonderation_()));
        holder.gradeScore_.setText(String.format("%.2f", (double)holder.grade_.getGrade_()));
        holder.gradeDate_.setText(holder.grade_.getGradeDate_());
        Log.v("Ponderation: ",Double.toString(holder.grade_.getPonderation_()) );

        // Qd tu click un row tu vas sur le details page
        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_, GradeContent.class);
                Bundle mBundle = new Bundle(); // Un Bundle est un object dans lequel on peu mettre dotre object dedans.
                // Reminder. on avai mis Foods comme enfant de serializableé
                mBundle.putSerializable("obj", finalHolder.grade_); // La on a pu freeze le food object pour lutiliser dans un otre activity.
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
        Grade grade_;
        TextView gradeName_;
        TextView gradeWeight_;
        TextView gradeScore_;
        TextView gradeDate_;
    }

}
