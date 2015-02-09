package com.razibdeb.programmingquiz;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.lang.reflect.GenericArrayType;

import other.Constants;
import other.Utilities;


public class RegisterActivity extends ActionBarActivity {


    Button registerButton;
    EditText nameEditText;
    EditText dateOfBirthdayEditText;
    RadioGroup genderGroup;
    EditText emailEditText;
    EditText nickNameEditText;
    EditText confirmPasswordEditText;
    EditText passwordEditText;
    RadioButton maleRadioButton, femaleRadioButton;

    ProgressDialog progressDialog;

    boolean isFromFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.buttonRegRegister);
        nameEditText = (EditText) findViewById(R.id.editTextRegName);
        dateOfBirthdayEditText = (EditText) findViewById(R.id.editTextRegDateOfBirth);
        emailEditText = (EditText) findViewById(R.id.editTextRegEmail);
        nickNameEditText = (EditText) findViewById(R.id.editTextRegNickName);

        maleRadioButton = (RadioButton) findViewById(R.id.radioButtonRegMale);
        femaleRadioButton = (RadioButton) findViewById(R.id.radioButtonRegFemale);
        genderGroup = (RadioGroup) findViewById(R.id.radioButtonGroupReg);

        confirmPasswordEditText = (EditText) findViewById(R.id.editTextRegPasswordConfirm);

        if (getIntent().getBooleanExtra("isFromFacebook", false)) {
            isFromFacebook = true;
            populateView();
        } else {
            isFromFacebook = false;
        }
        //listeners
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO Check duplicity of email & nick name
                if (nameEditText.getText().toString() == "" || emailEditText.getText().toString() == "" || nickNameEditText.getText().toString() == "") {
                    Utilities.makeToast("Enter Name, Email & NickName", getApplicationContext());
                }
                else if (isFromFacebook == true)
                {
                    try {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        currentUser.setEmail(emailEditText.getText().toString());
                        currentUser.put(Constants.USER_NICK_NAME, nickNameEditText.getText().toString());
                        currentUser.put(Constants.USER_NAME, nameEditText.getText().toString());
                        currentUser.put(Constants.USER_BIRTHDAY, dateOfBirthdayEditText.getText().toString() == null ? "" : dateOfBirthdayEditText.getText().toString());
                        String sex = "";
                        if (maleRadioButton.isChecked()) {
                            sex = "male";
                        } else if (femaleRadioButton.isChecked()) {
                            sex = "female";
                        }
                        currentUser.put(Constants.USER_GENDER, sex);
                        Utilities.showProgressDialogue(getApplicationContext(), progressDialog);
                        currentUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                progressDialog.dismiss();
                                if (e == null) {
                                    Utilities.makeToast("Successfully Registered", getApplicationContext());
                                } else {
                                    Utilities.makeToast("Registration failed", getApplicationContext());
                                    Utilities.Log("Registration Failed " + e.toString());
                                }
                            }
                        });

                        Utilities.makeToast("Successfully Saved users data", getApplicationContext());
                    } catch (Exception e) {
                        Utilities.makeToast("Problem in saving users data", getApplicationContext());
                        Utilities.Log("Problem in saving users data \n" + e.toString());
                    }
                }
                else
                {
                    ParseUser user = new ParseUser();
                    user.setUsername(nickNameEditText.getText().toString());
                    user.setPassword(confirmPasswordEditText.getText().toString());
                    user.setEmail(emailEditText.getText().toString());
                    user.put(Constants.USER_NAME, nameEditText.getText().toString());
                    String sex = "";
                    if (maleRadioButton.isChecked()) {
                        sex = "male";
                    } else if (femaleRadioButton.isChecked()) {
                        sex = "female";
                    }
                    user.put(Constants.USER_GENDER, sex);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Utilities.makeToast("Successfully Registered", getApplicationContext());
                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Utilities.makeToast("Failed Error: "+ e.getMessage(), getApplicationContext());
                                Utilities.Log("Registration Failed, ERROR: " + e.toString());
                            }
                        }
                    });
                }
            }
        });

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonRegMale) {
                    femaleRadioButton.setChecked(false);
                } else if (checkedId == R.id.radioButtonRegFemale) {
                    maleRadioButton.setChecked(false);
                }
            }
        });
    }

    //populate the view
    private void populateView() {
        if (ParseUser.getCurrentUser() != null) {
            ParseUser currentUser = ParseUser.getCurrentUser();

            if (currentUser.get(Constants.USER_NAME) != null) {
                nameEditText.setText((String) currentUser.get(Constants.USER_NAME));
            }
            if (currentUser.get(Constants.USER_BIRTHDAY) != null) {
                dateOfBirthdayEditText.setText((String) currentUser.get(Constants.USER_BIRTHDAY));
            }

            if (currentUser.get(Constants.USER_GENDER) != null) {
                String gender = (String) currentUser.get(Constants.USER_GENDER);
                if (gender.equalsIgnoreCase("male")) {
                    maleRadioButton.setChecked(true);
                    femaleRadioButton.setChecked(false);
                } else {
                    maleRadioButton.setChecked(false);
                    femaleRadioButton.setChecked(true);
                }
            }
        } else {
            Utilities.Log("Register: Parse User not found");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
