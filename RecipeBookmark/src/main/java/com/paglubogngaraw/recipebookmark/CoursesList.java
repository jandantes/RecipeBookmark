package com.paglubogngaraw.recipebookmark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by jan.dantes on 9/27/13.
 */
public class CoursesList extends Fragment {

    private ListView courseList;
    private ListViewItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_courses,container, false);
        courseList = (ListView) rootView.findViewById(R.id.listView_courses);
        return rootView;
    }

    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        adapter = new ListViewItemAdapter(
                getActivity(),
                R.layout.listview_row_courses,
                R.id.txt_courseName,
                R.id.txt_courseCount,
                "recipeCourse",
                getResources().getStringArray(R.array.courses_array)
        );
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView,final View view,final int i,final long l) {
                TextView name = (TextView) view.findViewById(R.id.txt_courseName);
                String itemName = name.getText().toString();
                //Toast.makeText(getActivity(),"View: " + itemName, Toast.LENGTH_SHORT).show();
                Intent coursesList = new Intent(getActivity(), RecipeListCourses.class);
                coursesList.putExtra("course", itemName);
                startActivity(coursesList);

            }
        });

    }

}
