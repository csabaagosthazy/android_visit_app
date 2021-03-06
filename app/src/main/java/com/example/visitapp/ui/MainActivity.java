package com.example.visitapp.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visitapp.R;
import com.example.visitapp.adapter.RecyclerAdapter;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.util.RecyclerViewItemClickListener;
import com.example.visitapp.viewmodel.visit.VisitListByDateViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends BaseActivity{

    private static final String TAG = "MainActivity";
    private List<VisitEntity> visits;
    private RecyclerAdapter<VisitEntity> recyclerAdapter;
    private VisitListByDateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "created");
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        setTitle(getString(R.string.mainTitle));
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.home);
        //remove action bar back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        visits = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "Item clicked: " + position);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "Item long clicked: " + position);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            logout();
        }else{

            VisitListByDateViewModel.Factory factory = new VisitListByDateViewModel.Factory(
                    getApplication(),
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    createDate(false), createDate(true)
            );
            viewModel = new ViewModelProvider(this, factory).get(VisitListByDateViewModel.class);
            viewModel.getVisitsByDate().observe(this, visitEntities -> {
                if (visitEntities != null) {
                    visits = visitEntities;
                    recyclerAdapter.setData(visits);
                }
            });
            recyclerView.setAdapter(recyclerAdapter);
        }


    }

    private Long createDate(boolean end){
        Calendar cal = Calendar.getInstance();
        Long result;
        if(end){
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);

            result = cal.getTimeInMillis();

        }
        else{
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            result = cal.getTimeInMillis();
        }

        return result;
    }
    @Override
    public void onBackPressed() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.sign_out_header);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.sign_out_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ok", (dialog, which) -> logout());
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
