package com.example.visitapp.ui.visitor;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.example.visitapp.R;
import com.example.visitapp.database.entity.VisitorEntity;
import com.example.visitapp.ui.BaseActivity;
import com.example.visitapp.util.OnAsyncEventListener;
import com.example.visitapp.viewmodel.visitor.VisitorViewModel;

public class VisitorDetails extends BaseActivity {
    private static final String TAG = "VisitorDetails";

    private static final int CREATE_CLIENT = 0;
    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;

    private VisitorViewModel viewModel;

    private VisitorEntity person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_details);

        String personId = getIntent().getStringExtra("personId");


        initiateView();

        VisitorViewModel.Factory factory = new VisitorViewModel.Factory(getApplication(), personId);
        viewModel = new ViewModelProvider(this, factory).get(VisitorViewModel.class);
        viewModel.getVisitor().observe(this, personEntity -> {
            if (personEntity != null) {
                person = personEntity;
                updateContent();
            }
        });

        if (personId != null) {
            setTitle(R.string.title_activity_details);
            Log.d(TAG, personId);
        } else {
            setTitle(R.string.title_activity_create);
            switchEditableMode();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (person != null)  {
            menu.add(0, EDIT_CLIENT, Menu.NONE, getString(R.string.action_edit))
                    .setIcon(R.drawable.ic_edit_foreground)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, DELETE_CLIENT, Menu.NONE, getString(R.string.action_delete))
                    .setIcon(R.drawable.ic_delete_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, CREATE_CLIENT, Menu.NONE, getString(R.string.action_create))
                    .setIcon(R.drawable.ic_add_foreground)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == EDIT_CLIENT) {
            if (isEditable) {
                item.setIcon(R.drawable.ic_edit_foreground);
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
                viewModel.deleteVisitor(person, new OnAsyncEventListener() {
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

        person = new VisitorEntity();
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);

        viewModel.createVisitor(person, new OnAsyncEventListener() {
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
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);

        viewModel.updateVisitor(person, new OnAsyncEventListener() {
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
            statusToast = Toast.makeText(this, getString(R.string.person_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(this, getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        if (person != null) {
            etFirstName.setText(person.getFirstName());
            etLastName.setText(person.getLastName());
            etEmail.setText(person.getEmail());
        }
    }
}
