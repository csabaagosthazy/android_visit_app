package com.example.visitapp.ui.visits;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.R;
import com.example.visitapp.adapter.SpinnerAdapter;
import com.example.visitapp.database.entity.PersonEntity;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.util.OnAsyncEventListener;
import com.example.visitapp.viewmodel.VisitViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitDetails extends AppCompatActivity {
    private static final String TAG = "ClientDetails";

    private static final int CREATE_VISIT = 0;
    private static final int EDIT_VISIT = 1;
    private static final int DELETE_VISIT = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etDescription, etDate;
    private Spinner spinnerVisitor, spinnerEmployee;

    private SpinnerAdapter<PersonEntity> adapterEmployee;
    private SpinnerAdapter<PersonEntity> adapterVisitor;


    private VisitViewModel viewModel;

    private VisitEntity visit;
    private List<PersonEntity> employees;
    private List<PersonEntity> visitors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visit_details);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Long idVisit = getIntent().getLongExtra("idVisit",-1);

        initiateView();
        setupEmployeeSpinner();
        setupVisitorSpinner();
        VisitViewModel.Factory factory = new VisitViewModel.Factory(getApplication(), idVisit);
        //viewModel = ViewModelProviders.of(this, factory).get(PersonViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(VisitViewModel.class);
        viewModel.getVisit().observe(this, visitEntity -> {
            if (visitEntity != null) {
                visit = visitEntity;
                updateContent();
            }
        });
        viewModel.getEmployees().observe(this, personEntities -> {
            if(personEntities!=null){
                employees = personEntities;

                updateEmployeeSpinner(employees);
            }
        });
        viewModel.getVisitors().observe(this, visitorsEntities -> {
            if(visitorsEntities!=null){
                visitors = visitorsEntities;
                updateVisitorSpinner(visitors);
            }
        });

        if (idVisit != -1) {
            setTitle(R.string.title_visit_details);
        } else {
            setTitle(R.string.title_visit_create);
            switchEditableMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (visit != null)  {
            menu.add(0, EDIT_VISIT, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_edit_foreground)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_VISIT, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, CREATE_VISIT, Menu.NONE, getString(R.string.action_create))
                    .setIcon(R.drawable.ic_add_foreground)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_VISIT) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit_foreground);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_VISIT) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteVisit(visit, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "deleteClient: success");
                        onBackPressed();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "deleteClient: failure", e);
                    }
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
        }
        if (item.getItemId() == CREATE_VISIT) {
            try {
                long selectedEmployee = ((PersonEntity) spinnerEmployee.getSelectedItem()).getIdPerson();
                long selectedVisitor = ((PersonEntity) spinnerVisitor.getSelectedItem()).getIdPerson();

                Log.d("debugWithCsaba", ""+(PersonEntity)spinnerVisitor.getSelectedItem());
                createVisit(
                        etDescription.getText().toString(),
                        new SimpleDateFormat("dd/MM/yyyy").parse(etDate.getText().toString()),
                        selectedVisitor,
                        selectedEmployee
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupVisitorSpinner() {
        spinnerVisitor = findViewById(R.id.spinner_visitor);
        adapterVisitor = new SpinnerAdapter<>(this, R.layout.row_person, new ArrayList<>());
        spinnerVisitor.setAdapter(adapterVisitor);
    }

    private void setupEmployeeSpinner() {
        spinnerEmployee = findViewById(R.id.spinner_employee);
        adapterEmployee = new SpinnerAdapter<>(this, R.layout.row_person, new ArrayList<>());
        spinnerEmployee.setAdapter(adapterEmployee);
    }

    private void updateVisitorSpinner(List<PersonEntity> persons) {
        adapterVisitor.updateData(new ArrayList<>(persons));
    }
    private void updateEmployeeSpinner(List<PersonEntity> persons) {
        adapterEmployee.updateData(new ArrayList<>(persons));
    }


    private void initiateView() {
        isEditable = false;
        etDescription = findViewById(R.id.description);
        spinnerVisitor = findViewById(R.id.spinner_visitor);
        spinnerEmployee = findViewById(R.id.spinner_employee);
        etDate = findViewById(R.id.etDate);

        etDescription.setFocusable(false);
        etDescription.setEnabled(false);
        spinnerVisitor.setFocusable(false);
        spinnerVisitor.setEnabled(false);
        spinnerEmployee.setFocusable(false);
        spinnerEmployee.setEnabled(false);
        etDate.setFocusable(false);
        etDate.setEnabled(false);
    }

    private void switchEditableMode() {
        if (!isEditable) {
            etDescription.setFocusable(true);
            etDescription.setEnabled(true);
            etDescription.setFocusableInTouchMode(true);

            spinnerVisitor.setFocusable(true);
            spinnerVisitor.setEnabled(true);
            spinnerVisitor.setFocusableInTouchMode(true);

            spinnerEmployee.setFocusable(true);
            spinnerEmployee.setEnabled(true);
            spinnerEmployee.setFocusableInTouchMode(true);

            etDate.setFocusable(true);
            etDate.setEnabled(true);
            etDate.setFocusableInTouchMode(true);

            etDescription.requestFocus();
        } else {
            try {
                saveChanges(
                        etDescription.getText().toString(),
                        new SimpleDateFormat("dd/MM/yyyy").parse(etDate.getText().toString()),
                        spinnerEmployee.getSelectedItemId(),
                        spinnerVisitor.getSelectedItemId()
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            etDescription.setFocusable(false);
            etDescription.setEnabled(false);

            etDate.setFocusable(false);
            etDate.setEnabled(false);

            spinnerEmployee.setFocusable(false);
            spinnerEmployee.setEnabled(false);

            spinnerVisitor.setFocusable(false);
            spinnerVisitor.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void createVisit(String description, Date visitDate, long visitor, long employee) {


        visit = new VisitEntity();
        visit.setDescription(description);
        visit.setVisitDate(visitDate);
        visit.setVisitor(visitor);
        visit.setEmployee(employee);


        viewModel.createVisit(visit, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createVisit: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createVisit: failure", e);
            }
        });
    }

    private void saveChanges(String description, Date visitDate, long visitor, long employee) {

        visit = new VisitEntity();
        visit.setDescription(description);
        visit.setVisitDate(visitDate);
        visit.setVisitor(visitor);
        visit.setEmployee(employee);

        viewModel.updateVisit(visit, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateVisit: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateVisit: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.visit_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        if (visit != null) {
            etDescription.setText(visit.getDescription());
            etDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(visit.getVisitDate()));
            spinnerVisitor.setSelection(visit.getVisitor().intValue());
            spinnerEmployee.setSelection(visit.getEmployee().intValue());
        }
    }
}
