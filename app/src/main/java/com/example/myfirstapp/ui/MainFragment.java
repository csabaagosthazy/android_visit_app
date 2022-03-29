package com.example.myfirstapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ArrayAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {

    String[] currentVisits;

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //must be modified later!!!!
        List<VisitEntity> currentVisitList = new ArrayList<>();
        currentVisitList.add(new VisitEntity("Test description1", new Date(), (long) 1, (long) 4));
        currentVisitList.add(new VisitEntity("Test description", new Date(), (long) 1, (long)4));
        currentVisits = new String[currentVisitList.size()];
        //add items to array
        for (int i = 0; i < currentVisitList.size(); i++)
        {
            currentVisits[i] = currentVisitList.get(i).getDescription();
        };


        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main, null, false);
        //ListView

        ListView list= view.findViewById(R.id.mainListView);

        //Add Header View
        View headerView = inflater.inflate(R.layout.listview_header, null, false);
        TextView headerText = headerView.findViewById(R.id.listHeader);
        headerText.setText(R.string.mainListHeader);

        //Add view to list view as header view
        list.addHeaderView(headerView);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.listview_body, currentVisits);

        list.setAdapter(adapter.getAdapter());

        //ListeView handler
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("itemid", String.valueOf(position));
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "You have selected: " + currentVisits[position-1], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


}
