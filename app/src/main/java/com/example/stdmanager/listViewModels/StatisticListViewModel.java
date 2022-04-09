package com.example.stdmanager.listViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stdmanager.R;
import com.example.stdmanager.models.Statistic;
import com.example.stdmanager.models.Student;

import java.util.ArrayList;

public class StatisticListViewModel extends ArrayAdapter<Statistic> {

    Context context;
    int resource;
    ArrayList<Statistic> data;

    public StatisticListViewModel(@NonNull Context context, int resource, @NonNull ArrayList<Statistic> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public int count()
    {
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

        TextView title = convertView.findViewById(R.id.titleStatistic);
        TextView text = convertView.findViewById(R.id.textStatistic);

        Statistic entry = data.get(position);

        title.setText(entry.getTitle());
        text.setText(entry.getText());
//
//        avatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Open the screen which shows student's detail*/
//                Intent intent = new Intent(context, ClassroomIndividualActivity.class);
//                /*Pass student object to next activity - Student class must implements Serializable*/
//                intent.putExtra("student", student );
//                /*start next activity*/
//                ((ClassroomActivity)context).startActivity(intent);
//            }
//        });

        return convertView;
    }
}
