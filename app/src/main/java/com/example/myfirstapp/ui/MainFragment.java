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
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ArrayAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.database.repository.VisitRepository;
import com.example.myfirstapp.viewmodel.VisitsByDateViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {
    private List<VisitEntity> currentVisitList;
    private VisitsByDateViewModel viewModel;
    private String[] currentVisits;


    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //must be modified later!!!!
        currentVisitList = new ArrayList<>();
        VisitsByDateViewModel.Factory factory = new VisitsByDateViewModel.Factory(getActivity().getApplication(), new Date());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(getActivity(), factory).get(VisitsByDateViewModel.class);
        viewModel.getVisitsByDate().observe(getActivity(), visitEntities -> {
            if (visitEntities != null) {
                currentVisitList = visitEntities;
                currentVisits = new String[currentVisitList.size()];
                //add items to array
                for (int i = 0; i < currentVisitList.size(); i++)
                {
                    currentVisits[i] = currentVisitList.get(i).getDescription();
                };
            }
        });



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

        if(currentVisits == null){
            list.setEmptyView(view.findViewById(R.id.empty));
        }else{
            list.setAdapter(adapter.getAdapter());
            //ListeView handler
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if(position != 0 ){
                        Log.d("itemid", String.valueOf(position));
                        // TODO Auto-generated method stub
                        Toast.makeText(getContext(), "You have selected: " + currentVisits[position-1], Toast.LENGTH_LONG).show();
                    }

                }
            });
        }



        return view;
    }


}