package com.luffycode.test3p.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;
import com.luffycode.test3p.dao.City;
import com.luffycode.test3p.dao.CityDao;
import com.luffycode.test3p.dao.Establishment;
import com.luffycode.test3p.dao.EstablishmentResults;
import com.luffycode.test3p.dao.PersonResults;
import com.luffycode.test3p.helper.ConnectionRetrofit;
import com.luffycode.test3p.helper.Utils;
import com.luffycode.test3p.iface.CustomerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Test3PCompatActivity {

    private TextInputLayout inputLayoutFullName;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutAddress;
    private TextInputLayout inputLayoutCity;
    private TextInputLayout inputLayoutPhone;
    private TextInputLayout inputLayoutRePassword;
    private TextInputLayout inputLayoutCompanyName;
    private TextInputLayout inputLayoutCompanyAddress;
    private TextInputLayout inputLayoutCompanyCity;
    private TextInputLayout inputLayoutCompanyPhone;
    private TextInputLayout inputLayoutCompanySince;

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputAddress;
    private AutoCompleteTextView inputCity;
    private EditText inputPhone;
    private EditText inputRepassword;
    private EditText inputCompanyName;
    private EditText inputCompanyAddress;
    private AutoCompleteTextView inputCompanyCity;
    private EditText inputCompanyPostalCode;
    private EditText inputCompanyPhone;
    private EditText inputCompanyFax;
    private EditText inputCompanyEmail;
    private EditText inputCompanyWebsite;
    private EditText inputCompanySince;
    private EditText inputCompanyNpwp;

    private Spinner establishment;
    private Button btnRegister;
    private CheckBox companyPkp;

    private List<String> typeItemsCity;
    private List<String> typeItemsEstablish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputLayoutFullName = (TextInputLayout) findViewById(R.id.input_layout_full_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_layout_city);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutRePassword = (TextInputLayout) findViewById(R.id.input_layout_repassword);
        inputLayoutCompanyName = (TextInputLayout) findViewById(R.id.input_layout_company_name);
        inputLayoutCompanyAddress = (TextInputLayout) findViewById(R.id.input_layout_company_address);
        inputLayoutCompanyCity = (TextInputLayout) findViewById(R.id.input_layout_company_city);
        inputLayoutCompanyPhone = (TextInputLayout) findViewById(R.id.input_layout_company_phone);
        inputLayoutCompanySince = (TextInputLayout) findViewById(R.id.input_layout_company_since);

        inputFullName = (EditText) findViewById(R.id.input_full_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputAddress = (EditText) findViewById(R.id.input_address);
        inputCity = (AutoCompleteTextView) findViewById(R.id.input_city);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputRepassword = (EditText) findViewById(R.id.input_repassword);
        inputCompanyName = (EditText) findViewById(R.id.input_company_name);
        inputCompanyAddress = (EditText) findViewById(R.id.input_company_address);
        inputCompanyCity = (AutoCompleteTextView) findViewById(R.id.input_company_city);
        inputCompanyPostalCode = (EditText) findViewById(R.id.input_postal_code);
        inputCompanyPhone = (EditText) findViewById(R.id.input_company_phone);
        inputCompanyFax = (EditText) findViewById(R.id.input_company_fax);
        inputCompanyEmail = (EditText) findViewById(R.id.input_company_email);
        inputCompanyWebsite = (EditText) findViewById(R.id.input_company_website);
        inputCompanySince = (EditText) findViewById(R.id.input_company_since);
        inputCompanyNpwp = (EditText) findViewById(R.id.input_company_npwp);

        establishment = (Spinner) findViewById(R.id.establishment);
        companyPkp = (CheckBox) findViewById(R.id.companyPkp);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        inputEmail.addTextChangedListener(new TextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new TextWatcher(inputPassword));

        btnRegister.setOnClickListener(listener);

        establishment.setAdapter(spinnerAdapter(RegisterActivity.this));

        inputCompanyCity.setAdapter(spinnerAdapterCity(RegisterActivity.this));
        inputCompanyCity.setThreshold(1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typeItemsCity);
        inputCity.setAdapter(adapter);
        inputCity.setThreshold(1);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnRegister:
                    submitForm();
                    break;
            }
        }
    };

    private void submitForm(){
        if (!validateName()) return;
        if (!validateEmail())return;
        if (!validateAddress())return;
        if (!validateCity())return;
        if (!validatePhone())return;
        if (!validatePassword())return;
        if (!validateRePassword())return;
        if (!validateCompanyName())return;
        if (!validateCompanyAddress())return;
        if (!validateCompanyCity())return;
        if (!validateCompanyPhone())return;
        if (!validateCompanySince())return;

        processForm();

        Log.d("luffyans", "Hello Luffy, form submited");
    }

    private boolean validateName(){
        String name = inputFullName.getText().toString().trim();
        if (name.isEmpty()){
            inputLayoutFullName.setError(getString(R.string.err_msg_name_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputFullName);
            return false;
        }else{
            inputLayoutFullName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail(){
        String email = inputEmail.getText().toString().trim();
        if (email.isEmpty() || !Utils.isValidateEmail(email)){
            inputLayoutEmail.setError(getString(R.string.err_msg_email_format));
            Utils.requestFocus(RegisterActivity.this, inputEmail);
            return false;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateAddress(){
        String address = inputAddress.getText().toString().trim();
        if (address.isEmpty()){
            inputLayoutAddress.setError(getString(R.string.err_msg_address_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputAddress);
            return false;
        }else{
            inputLayoutAddress.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCity(){
        String city = inputCity.getText().toString().trim();
        if (city.isEmpty()){
            inputLayoutCity.setError(getString(R.string.err_msg_city_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCity);
            return false;
        }else{
            inputLayoutCity.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone(){
        String phone = inputPhone.getText().toString().trim();
        if (phone.isEmpty()){
            inputLayoutPhone.setError(getString(R.string.err_msg_phone_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputPhone);
            return false;
        }else{
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCompanyName(){
        String companyName = inputCompanyName.getText().toString().trim();
        if (companyName.isEmpty()){
            inputLayoutCompanyName.setError(getString(R.string.err_msg_company_name_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCompanyName);
            return false;
        }else{
            inputLayoutCompanyName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCompanyCity(){
        String companyCity = inputCompanyCity.getText().toString().trim();
        if (companyCity.isEmpty()){
            inputLayoutCompanyCity.setError(getString(R.string.err_msg_company_city_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCompanyCity);
            return false;
        }else{
            inputLayoutCompanyCity.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCompanyAddress(){
        String companyAddress = inputCompanyAddress.getText().toString().trim();
        if (companyAddress.isEmpty()){
            inputLayoutCompanyAddress.setError(getString(R.string.err_msg_company_address_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCompanyAddress);
            return false;
        }else{
            inputLayoutCompanyAddress.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCompanyPhone(){
        String companyPhone = inputCompanyPhone.getText().toString().trim();
        if (companyPhone.isEmpty()){
            inputLayoutCompanyPhone.setError(getString(R.string.err_msg_company_phone_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCompanyPhone);
            return false;
        }else{
            inputLayoutCompanyPhone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCompanySince(){
        String companySince = inputCompanySince.getText().toString().trim();
        if (companySince.isEmpty()){
            inputLayoutCompanySince.setError(getString(R.string.err_msg_company_since_not_empty));
            Utils.requestFocus(RegisterActivity.this, inputCompanySince);
            return false;
        }else{
            inputLayoutCompanySince.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        String password = inputPassword.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutPassword.setError(getString(R.string.err_msg_password_required));
            Utils.requestFocus(RegisterActivity.this, inputPassword);
            return false;
        }else if (password.length() < 4){
            inputLayoutPassword.setError(getString(R.string.err_msg_password_min_required));
            Utils.requestFocus(RegisterActivity.this, inputPassword);
            return false;
        }else{
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateRePassword(){
        String password = inputRepassword.getText().toString().trim();
        if (password.isEmpty()){
            inputLayoutRePassword.setError(getString(R.string.err_msg_password_required));
            Utils.requestFocus(RegisterActivity.this, inputRepassword);
            return false;
        }else if (password.length() < 4){
            inputLayoutRePassword.setError(getString(R.string.err_msg_password_min_required));
            Utils.requestFocus(RegisterActivity.this, inputRepassword);
            return false;
        }else if (!password.equals(inputPassword.getText().toString().trim())){
            inputLayoutRePassword.setError(getString(R.string.err_msg_password_not_same_min_required));
            Utils.requestFocus(RegisterActivity.this, inputRepassword);
            return false;
        }else{
            inputLayoutRePassword.setErrorEnabled(false);
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
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading..");
        dialog.show();

        final String fullName = inputFullName.getText().toString();
        final String email = inputEmail.getText().toString();
        final String address = inputAddress.getText().toString();
        final String city = inputCity.getText().toString();
        final String phone = inputPhone.getText().toString();
        final String passsword = inputPassword.getText().toString();
        final String companyName = inputCompanyName.getText().toString();
        final String establis = String.valueOf((establishment.getSelectedItemPosition() + 1));
        final String companyAddress = inputCompanyAddress.getText().toString();
        final String companyCity = inputCompanyCity.getText().toString().substring(0, 3);
        final String companyPostalCode = inputCompanyPostalCode.getText().toString();
        final String companyPhone = inputCompanyPhone.getText().toString();
        final String companyFax = inputCompanyFax.getText().toString();
        final String companyEmail = inputCompanyEmail.getText().toString();
        final String companyWebsite = inputCompanyWebsite.getText().toString();
        final String companySince = inputCompanySince.getText().toString();
        final String companyNpwp = inputCompanyNpwp.getText().toString();
        final String companyPkp = String.valueOf(this.companyPkp.isChecked() ? 1:0);

        CustomerInterface api = new ConnectionRetrofit().getRerofit().create(CustomerInterface.class);
        Call<PersonResults> personCall = api.register(
                fullName, email, address, city, phone, passsword, companyName, establis, companyAddress, companyCity,
                companyPostalCode, companyPhone, companyFax, companyEmail, companyWebsite, companySince, companyNpwp, companyPkp);
        personCall.enqueue(new Callback<PersonResults>() {
            @Override
            public void onResponse(Call<PersonResults> call, Response<PersonResults> response) {
                PersonResults resultsPerson = response.body();
                if (resultsPerson.getStatus() == Utils.STATUS_OK){
                    Toast.makeText(RegisterActivity.this, resultsPerson.getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, SignActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransitionEnter();
                    dialog.dismiss();
                    finish();

                }else{
                    Toast.makeText(RegisterActivity.this, resultsPerson.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PersonResults> call, Throwable t) {
                call.cancel();
                Utils.log(call.toString());
                Utils.log(t.getMessage());
                dialog.dismiss();
            }
        });
    }

    private ArrayAdapter<String> spinnerAdapter(Context mContext){
        List<String> typeItems = new ArrayList<>();
        typeItems.add("Koperasi");
        typeItems.add("Badan Usaha Milik Negara");
        typeItems.add("Badan Usaha Milik Swasta (BUMS)");
        typeItems.add("Perusahaan Perseorangan");
        typeItems.add("Perusahaan Persekutuan / Partnership");

        /*typeItemsEstablish = new ArrayList<>();

        CustomerInterface api = new ConnectionRetrofit().getRerofit().create(CustomerInterface.class);
        Call<EstablishmentResults> results = api.establishment();
        results.enqueue(new Callback<EstablishmentResults>() {
            @Override
            public void onResponse(Call<EstablishmentResults> call, Response<EstablishmentResults> response) {
                EstablishmentResults results1 = response.body();
                if (results1.getStatus() == Utils.STATUS_OK){
                    for (int i = 0; i < results1.getResults().size(); i++) {
                        Establishment establishment = results1.getResults().get(i);
                        String kode = String.valueOf(establishment.getId());
                        String nama = establishment.getName();
                        typeItemsEstablish.add(kode+" - "+nama);
                    }
                }
            }

            @Override
            public void onFailure(Call<EstablishmentResults> call, Throwable t) {
                Utils.log(t.getMessage());
            }
        });*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, typeItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private ArrayAdapter<String> spinnerAdapterCity(Context mContext){
        typeItemsCity = new ArrayList<>();

        CustomerInterface api = new ConnectionRetrofit().getRerofit().create(CustomerInterface.class);
        Call<CityDao> cityDao = api.city();
        cityDao.enqueue(new Callback<CityDao>() {
            @Override
            public void onResponse(Call<CityDao> call, Response<CityDao> response) {
                CityDao cityDao1 = response.body();
                /*if (cityDao1 != null){
                    Toast.makeText(RegisterActivity.this, "Maaf, terjadi masalah dengan sistem kami.", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (cityDao1.getStatus() == Utils.STATUS_OK){
                    for (int i = 0; i < cityDao1.getResults().size(); i++) {
                        City city = cityDao1.getResults().get(i);
                        String kode = city.getKode_kota();
                        String nama = city.getNamaKota();
                        typeItemsCity.add(kode+" - "+nama);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, cityDao1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityDao> call, Throwable t) {
                Utils.log(t.getMessage());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, typeItemsCity);
        return adapter;
    }
}
