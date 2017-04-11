package com.luffycode.test3p.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
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
import com.luffycode.test3p.dao.Person;
import com.luffycode.test3p.dao.PersonResults;
import com.luffycode.test3p.dao.Profile;
import com.luffycode.test3p.helper.ConnectionRetrofit;
import com.luffycode.test3p.helper.Utils;
import com.luffycode.test3p.iface.CustomerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Test3PCompatActivity {
    private TextInputLayout inputLayoutFullName;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutAddress;
    private TextInputLayout inputLayoutCity;
    private TextInputLayout inputLayoutPhone;
    private TextInputLayout inputLayoutCompanyName;
    private TextInputLayout inputLayoutCompanyAddress;
    private TextInputLayout inputLayoutCompanyCity;
    private TextInputLayout inputLayoutCompanyPhone;
    private TextInputLayout inputLayoutCompanySince;

    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputAddress;
    private AutoCompleteTextView inputCity;
    private EditText inputPhone;
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
    private Button btnUpdate;
    private CheckBox companyPkp;

    private List<String> typeItemsCity;
    private List<String> typeItemsEstablish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.profile));
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        inputLayoutFullName = (TextInputLayout) findViewById(R.id.input_layout_full_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutCity = (TextInputLayout) findViewById(R.id.input_layout_city);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutCompanyName = (TextInputLayout) findViewById(R.id.input_layout_company_name);
        inputLayoutCompanyAddress = (TextInputLayout) findViewById(R.id.input_layout_company_address);
        inputLayoutCompanyCity = (TextInputLayout) findViewById(R.id.input_layout_company_city);
        inputLayoutCompanyPhone = (TextInputLayout) findViewById(R.id.input_layout_company_phone);
        inputLayoutCompanySince = (TextInputLayout) findViewById(R.id.input_layout_company_since);

        inputFullName = (EditText) findViewById(R.id.input_full_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputAddress = (EditText) findViewById(R.id.input_address);
        inputCity = (AutoCompleteTextView) findViewById(R.id.input_city);
        inputPhone = (EditText) findViewById(R.id.input_phone);
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
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        inputEmail.addTextChangedListener(new TextWatcher(inputEmail));

        btnUpdate.setOnClickListener(listener);

        loadProfile();

        establishment.setAdapter(spinnerAdapter(ProfileActivity.this));

        inputCompanyCity.setAdapter(spinnerAdapterCity(ProfileActivity.this));
        inputCompanyCity.setThreshold(1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typeItemsCity);
        inputCity.setAdapter(adapter);
        inputCity.setThreshold(1);
    }

    private void loadProfile(){
        inputFullName.setText(Utils.getPreference(ProfileActivity.this).getString("full_name",""));
        inputEmail.setText(Utils.getPreference(ProfileActivity.this).getString("email",""));
        inputAddress.setText(Utils.getPreference(ProfileActivity.this).getString("address",""));
        inputCity.setText(Utils.getPreference(ProfileActivity.this).getString("city",""));
        inputPhone.setText(Utils.getPreference(ProfileActivity.this).getString("phone",""));
        inputCompanyName.setText(Utils.getPreference(ProfileActivity.this).getString("company_name",""));
        inputCompanyAddress.setText(Utils.getPreference(ProfileActivity.this).getString("company_address",""));
        inputCompanyCity.setText(Utils.getPreference(ProfileActivity.this).getString("company_city",""));
        inputCompanyPostalCode.setText(Utils.getPreference(ProfileActivity.this).getString("postal_code",""));
        inputCompanyPhone.setText(Utils.getPreference(ProfileActivity.this).getString("phone_office",""));
        inputCompanyFax.setText(Utils.getPreference(ProfileActivity.this).getString("fax_office",""));
        inputCompanyEmail.setText(Utils.getPreference(ProfileActivity.this).getString("company_email",""));
        inputCompanyWebsite.setText(Utils.getPreference(ProfileActivity.this).getString("company_website",""));
        inputCompanySince.setText(Utils.getPreference(ProfileActivity.this).getString("company_since",""));
        inputCompanyNpwp.setText(Utils.getPreference(ProfileActivity.this).getString("company_NPWP",""));

        establishment.setSelection(2);
        if (Integer.parseInt(Utils.getPreference(ProfileActivity.this).getString("company_pkp","")) == 1){
            companyPkp.setChecked(true);
        }else{
            companyPkp.setChecked(false);
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnUpdate:
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
            Utils.requestFocus(ProfileActivity.this, inputFullName);
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
            Utils.requestFocus(ProfileActivity.this, inputEmail);
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
            Utils.requestFocus(ProfileActivity.this, inputAddress);
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
            Utils.requestFocus(ProfileActivity.this, inputCity);
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
            Utils.requestFocus(ProfileActivity.this, inputPhone);
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
            Utils.requestFocus(ProfileActivity.this, inputCompanyName);
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
            Utils.requestFocus(ProfileActivity.this, inputCompanyCity);
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
            Utils.requestFocus(ProfileActivity.this, inputCompanyAddress);
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
            Utils.requestFocus(ProfileActivity.this, inputCompanyPhone);
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
            Utils.requestFocus(ProfileActivity.this, inputCompanySince);
            return false;
        }else{
            inputLayoutCompanySince.setErrorEnabled(false);
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
            }
        }
    }

    private void processForm(){
        final ProgressDialog dialog = new ProgressDialog(ProfileActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading..");
        dialog.show();

        final String id = Utils.getPreference(ProfileActivity.this).getString("id", "");
        final String fullName = inputFullName.getText().toString();
        final String email = inputEmail.getText().toString();
        final String address = inputAddress.getText().toString();
        final String city = inputCity.getText().toString();
        final String phone = inputPhone.getText().toString();
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
        Call<PersonResults> personCall = api.profileUpdate(
                id, "oke", fullName, email, address, city, phone, companyName, establis, companyAddress, companyCity,
                companyPostalCode, companyPhone, companyFax, companyEmail, companyWebsite, companySince, companyNpwp, companyPkp);
        personCall.enqueue(new Callback<PersonResults>() {
            @Override
            public void onResponse(Call<PersonResults> call, Response<PersonResults> response) {
                PersonResults resultsPerson = response.body();
                if (resultsPerson.getStatus() == Utils.STATUS_OK){
                    for (int i = 0; i < resultsPerson.getResults().size(); i++) {
                        Person person = resultsPerson.getResults().get(i);

                        SharedPreferences.Editor editor = Utils.getPreference(ProfileActivity.this).edit();
                        editor.putString("id", person.getId());
                        editor.putString("full_name", person.getFull_name());
                        editor.putString("email", person.getEmail());
                        editor.putString("address", person.getAddress());
                        editor.putString("city", person.getCity());
                        editor.putString("phone", person.getPhone());
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
                    }

                    Toast.makeText(ProfileActivity.this, resultsPerson.getMessage(), Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }else{
                    Toast.makeText(ProfileActivity.this, resultsPerson.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PersonResults> call, Throwable t) {
                call.cancel();
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

                if (cityDao1.getStatus() == Utils.STATUS_OK){
                    for (int i = 0; i < cityDao1.getResults().size(); i++) {
                        City city = cityDao1.getResults().get(i);
                        String kode = city.getKode_kota();
                        String nama = city.getNamaKota();
                        typeItemsCity.add(kode+" - "+nama);
                    }
                }else{
                    Toast.makeText(ProfileActivity.this, cityDao1.getMessage(), Toast.LENGTH_SHORT).show();
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
