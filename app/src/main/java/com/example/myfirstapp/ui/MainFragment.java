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
import com.example.myfirstapp.viewmodel.VisitsListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainFragment extends Fragment {
    private List<VisitEntity> currentVisitList;
    private VisitsListViewModel viewModel;
    private String[] currentVisits;


    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //must be modified later!!!!
        currentVisitList = new ArrayList<>();
        VisitsListViewModel.Factory factory = new VisitsListViewModel.Factory(getActivity().getApplication());
        //viewModel = ViewModelProviders.of(this, factory).get(PersonListViewModel.class);
        viewModel = new ViewModelProvider(getActivity(), factory).get(VisitsListViewModel.class);
        viewModel.getVisits().observe(getActivity(), visitEntities -> {
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

        ListView list= view.findViewById(R.id.mainRecyclerView);

        //Add Header View
        View headerView = inflater.inflate(R.layout.listview_header, null, false);
        TextView headerText = headerView.findViewById(R.id.listHeader);
        headerText.setText(R.string.mainListHeader);

        //Add view to list view as header view
        list.addHeaderView(headerView);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.listview_body, currentVisits);

        if(currentVisits == null){
            //do something
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

    private String createDate(boolean end){
        Calendar cal = Calendar.getInstance();
        String result;
        if(end){
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);

            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = dateFormat.format(date);
        }
        else{
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = dateFormat.format(date);
        }

        return result;
    }


}
