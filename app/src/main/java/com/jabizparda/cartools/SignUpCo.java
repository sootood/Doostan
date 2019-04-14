package com.jabizparda.cartools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jabizparda.cartools.room.AgantData;
import com.jabizparda.cartools.room.AppDatabase;
import com.jabizparda.cartools.room.CategoryData;
import com.jabizparda.cartools.room.CityData;
import com.jabizparda.cartools.room.ProvinceData;
import com.jabizparda.cartools.room.User;
import com.koushikdutta.async.future.FutureCallback;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpCo extends HappyCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @BindView(R.id.spinner1)
    MaterialSpinner spinner1;
    @BindView(R.id.spinnerZone)
    MaterialSpinner spinnerZone;
    @BindView(R.id.password)
    EditText lastName;
    @BindView(R.id.email)
    EditText name;

    @BindView(R.id.numberCo)
    EditText numberCo;
    @BindView(R.id.transportTell)
    EditText transportTell;
    @BindView(R.id.nationalCode)
    EditText nationalCode;
    @BindView(R.id.postalCode)
    EditText postalCode;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.transortNumber)
    EditText transortNumber;
    @BindView(R.id.telegramNumber)
    EditText telegramNumber;
    @BindView(R.id.email_sign_in_button)
    Button sendAgant;
    ArrayList<ProvinceData> linkedProvince;
    String[] privonce;
    ArrayList<CityData> linkedCity;
    String[] city;
    String cityCode,provinceCode,cityName,provinceName;
    Core core;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_co);
        linkedProvince= new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpCo.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
        ButterKnife.bind(this);
        core= new Core(this);
        spinnerZone.setHintColor(getResources().getColor(R.color.black));
        spinner1.setHintColor(getResources().getColor(R.color.black));
        spinnerZone.setArrowColor(getResources().getColor(R.color.whaite));
        if (AppDatabase.getAppDatabase(getApplicationContext()).provinceDoa().getAll().isEmpty()){
        core.getAllProvince(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                Logger.d(result);
                if (e==null){
                    if (result.has("code") & result.get("code").getAsInt()==200){
                        linkedProvince = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<ProvinceData>>() {
                        }.getType());
                        privonce = new String[linkedProvince.size()];
                        for (int i = 0; i < linkedProvince.size(); i++) {

                            privonce[i] = linkedProvince.get(i).getName();
                        }
                        showProgress(false);
                        final ArrayAdapter<String> replaceAdapter = new ArrayAdapter<String>(SignUpCo.this, android.R.layout.simple_spinner_item, privonce);
                        spinner1.setAdapter(replaceAdapter);
                    }
                }else {
                    //no data
                    Toast.makeText(SignUpCo.this,"خطا در دریافت اطلاعات",Toast.LENGTH_LONG).show();

                }

            }
        });}



        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        final boolean[] btnFace = {false};
        if (!AppDatabase.getAppDatabase(getApplicationContext()).agantDoa().getAgant().isEmpty()){
            List<AgantData> agant= AppDatabase.getAppDatabase(getApplicationContext()).agantDoa().getAgant();
            btnFace[0] =true;
            mEmailView.setText(agant.get(0).getFirstName());
            mEmailView.setEnabled(false);
            mPasswordView.setText(agant.get(0).getLastName());
            mPasswordView.setEnabled(false);
//            nameCo.setText(agant.get(0).getTransportName());
//            nameCo.setEnabled(false);
            numberCo.setText(agant.get(0).getMobile());
            numberCo.setEnabled(false);
            telegramNumber.setText(agant.get(0).getTelegram());
            telegramNumber.setEnabled(false);
            transortNumber.setText(agant.get(0).getTransportName());
            transortNumber.setEnabled(false);
            transportTell.setText(agant.get(0).getTel());
            transportTell.setEnabled(false);
            nationalCode.setText(agant.get(0).getNationalCode());
            nationalCode.setEnabled(false);
            address.setText(agant.get(0).getAddress());
            address.setEnabled(false);
            postalCode.setText(agant.get(0).getPostCode());
            postalCode.setEnabled(false);
            spinner1.setEnabled(false);
            spinner1.setHint(agant.get(0).getProvinceId());
            spinnerZone.setEnabled(false);

            spinnerZone.setHint(agant.get(0).getCity());
            sendAgant.setText("تغییر اطلاعات");

        }else {
            btnFace[0] =false;
        }
        // Set up the login form.
        populateAutoComplete();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFace[0] ==false){
                attemptLogin();
                }else{
                    btnFace[0] =false;
                    AppDatabase.getAppDatabase(getApplicationContext()).agantDoa().deleteAll();
                    mEmailView.setEnabled(true);
                    mPasswordView.setEnabled(true);
//                    nameCo.setEnabled(true);
                    numberCo.setEnabled(true);
                    transortNumber.setEnabled(true);
                    transportTell.setEnabled(true);
                    telegramNumber.setEnabled(true);
                    postalCode.setEnabled(true);
                    nationalCode.setEnabled(true);
                    address.setEnabled(true);
                    spinnerZone.setEnabled(true);
                    spinner1.setEnabled(true);
                    spinner1.setHint("استان");
                    spinner1.setHint("شهر");
                    sendAgant.setText("ذخیره");


                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        String[] ITEMS = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpCo.this, android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinnerZone.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d(adapterView.getSelectedItemPosition());
                AppDatabase.getAppDatabase(getApplicationContext()).cityDoa().deleteAll();
                if (AppDatabase.getAppDatabase(getApplicationContext()).cityDoa().getAll().isEmpty()){
                   int cityInput=0;
                    if (i==-1){
                        cityInput=0;
                        provinceCode="";
                    }else{
                        cityInput=(int)l;
                        provinceCode= String.valueOf(linkedProvince.get(i).getId());
                        provinceName=linkedProvince.get(i).getName();
                    }
                    core.getAllCity(cityInput,new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Logger.d(result);
                            if (result.get("data").getAsJsonArray().size()!=0){
                                if (result.has("code") & result.get("code").getAsInt()==200){
                                    linkedCity = new Gson().fromJson(result.get("data").getAsJsonArray(), new TypeToken<List<CityData>>() {
                                    }.getType());
                                    city = new String[linkedCity.size()];
                                    for (int i = 0; i < linkedCity.size(); i++) {

                                        city[i] = linkedCity.get(i).getName();
                                    }
                                    showProgress(false);
                                    final ArrayAdapter<String> replaceAdapter = new ArrayAdapter<String>(SignUpCo.this, android.R.layout.simple_spinner_item, city);
                                    spinnerZone.setAdapter(replaceAdapter);
                                }
                            }else {
                                //no data
//                                Toast.makeText(SignUpCo.this,"خطا در دریافت اطلاعات",Toast.LENGTH_LONG).show();

                            }

                        }
                    });}

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==-1){
                    cityCode="";
                }else{
                    cityCode= String.valueOf(linkedCity.get(i).getId());
                    cityName=linkedCity.get(i).getName();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (password.equals("")) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        //
        if (transortNumber.getText().toString().equals("")) {
            transortNumber.setError(getString(R.string.error_field_required));
            focusView = transortNumber;
            cancel = true;
        }
        if (cityCode.equals("")) {
            spinnerZone.setError(getString(R.string.error_field_required));
            focusView = spinnerZone;
            cancel = true;
        }

        if (provinceCode.equals("")) {
            spinner1.setError(getString(R.string.error_field_required));
            focusView = spinner1;
            cancel = true;
        }
        //
//        if (postalCode.getText().toString().equals("")) {
//            postalCode.setError(getString(R.string.error_field_required));
//            focusView = postalCode;
//            cancel = true;
//        }
        //

        if (address.getText().toString().equals("")) {
            address.setError(getString(R.string.error_field_required));
            focusView = address;
            cancel = true;
        }


//        if (nameCo.getText().toString().equals("")) {
//            nameCo.setError(getString(R.string.error_field_required));
//            focusView = nameCo;
//            cancel = true;
//        }
        //
        if (nationalCode.getText().toString().equals("") ) {
            nationalCode.setError(getString(R.string.error_field_required));
            focusView = nationalCode;
            cancel = true;
        }else if (!isNationalcodeValid(nationalCode.getText().toString())) {
            nationalCode.setError("شماره نامعتبر");
            focusView = nationalCode;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        //
        if (TextUtils.isEmpty(telegramNumber.getText().toString())) {
            telegramNumber.setError(getString(R.string.error_field_required));
            focusView = telegramNumber;
            cancel = true;
        } else if (!isNumberValid(telegramNumber.getText().toString())) {
            telegramNumber.setError("شماره نامعتبر");
            focusView = telegramNumber;
            cancel = true;
        }
        //
        if (TextUtils.isEmpty(numberCo.getText().toString())) {
            numberCo.setError(getString(R.string.error_field_required));
            focusView = numberCo;
            cancel = true;
        } else if (!isNumberValid(numberCo.getText().toString())) {
            numberCo.setError("شماره نامعتبر");
            focusView = numberCo;
            cancel = true;
        }
        //

        if (TextUtils.isEmpty(transportTell.getText().toString())) {
            transportTell.setError(getString(R.string.error_field_required));
            focusView = transportTell;
            cancel = true;
        } else if (!isNumberStaticValid(transportTell.getText().toString())) {
            transportTell.setError("شماره نامعتبر");
            focusView = transportTell;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }



    }

    private boolean isNumberValid(String number) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return number.length()==11 && number.startsWith("09");

    }

    private boolean isNumberStaticValid(String number) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return number.length()==11 && number.startsWith("0");

    }

    private boolean isNationalcodeValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() == 10;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent= new Intent(SignUpCo.this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);
//        finish();
//    }

    /**
     * Shows the progress UI and hides the login form.
     */
    AlertDialog alertDialog;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        if(bool) {
//            if(alertDialog != null){
//                alertDialog.dismiss();
//            }
//            View view = getLayoutInflater().inflate(R.layout.dialog_progress_one, null);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
//            builder.setView(view);
//            view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
//            builder.setCancelable(false);
//            alertDialog = builder.create();
//            alertDialog.show();
//        } else {
//            if(alertDialog != null)
//                alertDialog.dismiss();
//        }
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(SignUpCo.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            // Simulate network access.


            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(true);
            JsonObject jsonObject= new JsonObject();
            jsonObject.addProperty("FirstName",name.getText().toString());
            jsonObject.addProperty("LastName",lastName.getText().toString());
            jsonObject.addProperty("NationalCode",nationalCode.getText().toString());
            jsonObject.addProperty("Mobile",numberCo.getText().toString());
            jsonObject.addProperty("Tel",transportTell.getText().toString());
            jsonObject.addProperty("ProvinceId",provinceCode);
            jsonObject.addProperty("City",cityCode);
            jsonObject.addProperty("Address",address.getText().toString());
            jsonObject.addProperty("PostCode",postalCode.getText().toString());
            jsonObject.addProperty("IsAgency",1);
            jsonObject.addProperty("Bearing",transortNumber.getText().toString());
            jsonObject.addProperty("Telegram",telegramNumber.getText().toString());
            jsonObject.addProperty("FirebaseId", FirebaseInstanceId.getInstance().getToken());
            Logger.json(jsonObject.toString());
            core.createAgent(jsonObject, new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
//                    Logger.json(result.toString());
                    if (result!= null){
                        if (result.has("code")&& result.get("code").getAsInt()==200){
                            if (result.get("data")!=null){
                                AgantData agant = new AgantData();
                                agant.setId(1);
                                agant.setAddress(address.getText().toString());
                                agant.setCity(cityName);
                                agant.setFirstName(mEmailView.getText().toString());
                                agant.setPostCode(postalCode.getText().toString());
                                agant.setLastName(mPasswordView.getText().toString());
                                agant.setProvinceId(provinceName);
                                agant.setNationalCode(nationalCode.getText().toString());
                                agant.setMobile(numberCo.getText().toString());
                                agant.setTelegram(telegramNumber.getText().toString());
                                agant.setTel(transportTell.getText().toString());
                                agant.setTransportName(transortNumber.getText().toString());
                                core.insertAgant(agant);
                                showProgress(false);
                                showToast(SignUpCo.this,"ثبت اطلاعات با موفقیت انجام شد.");
                                Intent intent= new Intent(SignUpCo.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();
                            }else{
                                //data is null
                            }
                        }else {

                            //result hasent 200 code
                        }

                    }else {
                        //result is null
                        showProgress(false);
                        Logger.e(e.toString());
                    }
                }
            });
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.back_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

