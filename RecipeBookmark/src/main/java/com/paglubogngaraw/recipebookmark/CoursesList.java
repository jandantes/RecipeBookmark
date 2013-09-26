package com.paglubogngaraw.recipebookmark;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
                getResources().getStringArray(R.array.courses_array)
        );
        courseList.setAdapter(adapter);

    }

}
