package com.luffycode.test3p.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;
import com.luffycode.test3p.dao.Person;
import com.luffycode.test3p.dao.PersonResults;
import com.luffycode.test3p.helper.ConnectionRetrofit;
import com.luffycode.test3p.helper.Utils;
import com.luffycode.test3p.iface.CustomerInterface;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends Test3PCompatActivity {
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignIn;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        inputEmail.addTextChangedListener(new TextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new TextWatcher(inputPassword));

        btnSignIn.setOnClickListener(listener);
        btnRegister.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.btnSignIn:
                    submitForm();
                    break;
                case R.id.btnRegister:
                    intent = new Intent(SignActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    overridePendingTransitionEnter();
                    break;
            }
        }
    };

    private void submitForm(){
        if (!validateEmail()){
            Toast.makeText(this, getString(R.string.err_msg_email_required), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePassword()){
            Toast.makeText(this, getString(R.string.err_msg_password_required), Toast.LENGTH_SHORT).show();
            return;
        }

        processForm();

        Log.d("luffyans", "Hello Luffy, form submited");
    }

    private boolean validateEmail(){
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !Utils.isValidateEmail(email)){
            inputLayoutEmail.setError(getString(R.string.err_msg_email_format));
            Utils.requestFocus(SignActivity.this, inputEmail);
            return false;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        String password = inputPassword.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutPassword.setError(getString(R.string.err_msg_password_required));
            Utils.requestFocus(SignActivity.this, inputPassword);
            return false;
        }else if (password.length() < 4){
            inputLayoutPassword.setError(getString(R.string.err_msg_password_min_required));
            Utils.requestFocus(SignActivity.this, inputPassword);
            return false;
        }else{
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private class TextWatcher implements android.text.TextWatcher {
        private View mView;

        public TextWatcher(View view){
            this.mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (mView.getId()){
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    private void processForm(){
        final ProgressDialog dialog = new ProgressDialog(SignActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading..");
        dialog.show();

        final String email = inputEmail.getText().toString();
        final String passsword = inputPassword.getText().toString();
        try {
            byte[] bytesOfMessage = passsword.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            Utils.log(thedigest.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        CustomerInterface api = new ConnectionRetrofit().getRerofit().create(CustomerInterface.class);
        Call<PersonResults> personCall = api.login(email, passsword);
        personCall.enqueue(new Callback<PersonResults>() {
            @Override
            public void onResponse(Call<PersonResults> call, Response<PersonResults> response) {
                PersonResults resultsPerson = response.body();
                if (resultsPerson.getStatus() == Utils.STATUS_OK){
                    for (int i = 0; i < resultsPerson.getResults().size(); i++) {
                        Person person = resultsPerson.getResults().get(i);

                        SharedPreferences.Editor editor = Utils.getPreference(SignActivity.this).edit();
                        editor.putString("id", person.getId());
                        editor.putString("full_name", person.getFull_name());
                        editor.putString("email", person.getEmail());
                        editor.putString("address", person.getAddress());
                        editor.putString("city", person.getCity());
                        editor.putString("phone", person.getPhone());
                        editor.putString("password", person.getPassword());
                        editor.putString("company_name", person.getCompany_name());
                        editor.putString("establishment", person.getEstablishment());
                        editor.putString("company_address", person.getCompany_address());
                        editor.putString("company_city", person.getCompany_city());
                        editor.putString("postal_code", person.getPostal_code());
                        editor.putString("phone_office", person.getPhone_office());
                        editor.putString("fax_office", person.getFax_office());
                        editor.putString("company_email", person.getCompany_email());
                        editor.putString("company_website", person.getCompany_website());
                        editor.putString("company_since", person.getCompany_since());
                        editor.putString("company_NPWP", person.getCompany_NPWP());
                        editor.putString("company_pkp", person.getCompany_pkp());
                        editor.commit();

                        dialog.dismiss();

                        Intent intent = new Intent(SignActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransitionEnter();
                        finish();
                    }
                }else{
                    dialog.dismiss();
                    Toast.makeText(SignActivity.this, resultsPerson.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonResults> call, Throwable t) {
                dialog.dismiss();
                call.cancel();
                Utils.log(call.toString());
                Utils.log(t.getMessage());
            }
        });
    }
}
