package com.luffycode.test3p.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;
import com.luffycode.test3p.helper.Utils;

public class SignActivity extends Test3PCompatActivity {
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        inputEmail.addTextChangedListener(new TextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new TextWatcher(inputPassword));

        btnSignIn.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSignIn:
                    submitForm();
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
        }else if (password.length() < 8){
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

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                dialog.dismiss();

                /*if (!UserDao.checkUser(email,passsword)){
                    Toast.makeText(SignInActivity.this, "Email atau Password salah!", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                Intent intent = new Intent(SignActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
