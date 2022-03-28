package com.example.myfirstapp.ui.visits;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirstapp.R;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.viewmodel.VisitViewModel;
import com.example.myfirstapp.util.OnAsyncEventListener;

public class VisitDetails extends AppCompatActivity {
    private static final String TAG = "ClientDetails";

    private static final int CREATE_CLIENT = 0;
    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;

    private VisitViewModel viewModel;

    private VisitEntity visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visit_details);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Long idVisit = getIntent().getLongExtra("idVisit",-1);

        initiateView();

        VisitViewModel.Factory factory = new VisitViewModel(getApplication(), idVisit);
        //viewModel = ViewModelProviders.of(this, factory).get(PersonViewModel.class);
        viewModel = new ViewModelProvider(this, factory).get(VisitViewModel.class);
        viewModel.getVisit().observe(this, visitEntity -> {
            if (visitEntity != null) {
                visit = visitEntity;
                updateContent();
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
        if (client != null)  {
            menu.add(0, EDIT_CLIENT, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_mode_edit_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_CLIENT, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, CREATE_CLIENT, Menu.NONE, getString(R.string.action_create))
                    .setIcon(R.drawable.ic_add_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_CLIENT) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                switchEditableMode();
            } else {
                item.setIcon(R.drawable.ic_done_white_24dp);
                switchEditableMode();
            }
        }
        if (item.getItemId() == DELETE_CLIENT) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.action_delete));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.delete_msg));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
                viewModel.deleteClient(client, new OnAsyncEventListener() {
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
        if (item.getItemId() == CREATE_CLIENT) {
            createClient(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString()
            );
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        isEditable = false;
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);

        etFirstName.setFocusable(false);
        etFirstName.setEnabled(false);
        etLastName.setFocusable(false);
        etLastName.setEnabled(false);
        etEmail.setFocusable(false);
        etEmail.setEnabled(false);
    }

    private void switchEditableMode() {
        if (!isEditable) {
            etFirstName.setFocusable(true);
            etFirstName.setEnabled(true);
            etFirstName.setFocusableInTouchMode(true);

            etLastName.setFocusable(true);
            etLastName.setEnabled(true);
            etLastName.setFocusableInTouchMode(true);

            etEmail.setFocusable(true);
            etEmail.setEnabled(true);
            etEmail.setFocusableInTouchMode(true);

            etFirstName.requestFocus();
        } else {
            saveChanges(
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString()
            );
            etFirstName.setFocusable(false);
            etFirstName.setEnabled(false);

            etLastName.setFocusable(false);
            etLastName.setEnabled(false);

            etEmail.setFocusable(false);
            etEmail.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    private void createClient(String firstName, String lastName, String email) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }

        client = new ClientEntity();
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setLastName(lastName);

        viewModel.createClient(client, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createClient: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createClient: failure", e);
            }
        });
    }

    private void saveChanges(String firstName, String lastName, String email) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }
        client.setEmail(email);
        client.setFirstName(firstName);
        client.setLastName(lastName);

        viewModel.updateClient(client, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateClient: success");
                setResponse(true);
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateClient: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(this, getString(R.string.client_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        if (client != null) {
            etFirstName.setText(client.getFirstName());
            etLastName.setText(client.getLastName());
            etEmail.setText(client.getEmail());
        }
    }
}
