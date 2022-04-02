package com.example.myfirstapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.ArrayAdapter;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.viewmodel.VisitsListViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";
    private List<VisitEntity> currentVisitList;
    private String[] currentVisits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "created");
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.app_name));
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.home);

        VisitsListViewModel.Factory factory = new VisitsListViewModel.Factory(getApplication());
        VisitsListViewModel viewModel = new ViewModelProvider(this, factory).get(VisitsListViewModel.class);
        viewModel.getVisits().observe(this, visitEntities -> {
            if (visitEntities != null) {
                this.currentVisitList = visitEntities;
                updateContent();
            }
        });

        ListView list = findViewById(R.id.mainListView);

        //Add Header View
        View headerView = getLayoutInflater().inflate(R.layout.listview_header, null, false);
        TextView headerText = headerView.findViewById(R.id.listHeader);
        headerText.setText(R.string.mainListHeader);

        //Add view to list view as header view
        list.addHeaderView(headerView);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview_body, this.currentVisits);

        if(this.currentVisits == null){
            list.setEmptyView(findViewById(R.id.empty));
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
                        Toast.makeText(getApplicationContext(), "You have selected: " + currentVisits[position-1], Toast.LENGTH_LONG).show();
                    }

                }
            });
        }



    }
    private void updateContent() {
        if (this.currentVisitList != null) {
            this.currentVisits = new String[this.currentVisitList.size()];
            //add items to array
            for (int i = 0; i < currentVisitList.size(); i++)
            {
                this.currentVisits[i] = this.currentVisitList.get(i).getDescription();
            };
        }
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
/*    @Override
    protected void onResume() {
        super.onResume();
        //setTitle(getString(R.string.app_name));
        //bottomNavigationView.setSelectedItemId(R.id.nav_none);
    }*/

    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //alertDialog.setTitle(getString(R.string.action_logout));
        //just a copy
        alertDialog.setTitle("test dialog");
        alertDialog.setCancelable(false);
        //alertDialog.setMessage(getString(R.string.logout_msg));
        alertDialog.setMessage("test message");
        //alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_logout), (dialog, which) -> logout());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialog, which) -> alertDialog.dismiss());
        //alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "cancel", (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.infoHeader);
        alertDialog.setCancelable(false);
        String s1 = "<b>" + "App Created by"+ "</b>";
        String s2 = "Loan Rey";
        String s3 = "Csaba Agosthazy";
        Spanned strMessage = Html.fromHtml(s1+  "<br>" + s2 +  "<br>" + s3);
        alertDialog.setMessage(strMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Great job", (dialog, which) -> alertDialog.dismiss());


        int id = item.getItemId();
        if (id == R.id.btnAbout) {
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}
