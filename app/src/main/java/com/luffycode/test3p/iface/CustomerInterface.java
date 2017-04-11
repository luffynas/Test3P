package com.luffycode.test3p.iface;

import com.luffycode.test3p.dao.Profile;
import com.luffycode.test3p.dao.ResultsPerson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MacMini on 2/24/17.
 */

public interface CustomerInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<ResultsPerson> register(
            @Field("full_name") String app,
            @Field("email") String name,
            @Field("address") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("company_name") String company_name,
            @Field("establishment") String establishment,
            @Field("company_address") String company_address,
            @Field("company_city") String company_city,
            @Field("postal_code") String postal_code,
            @Field("phone_office") String phone_office,
            @Field("fax_office") String fax_office,
            @Field("company_email") String company_email,
            @Field("company_website") String company_website,
            @Field("company_since") String company_since,
            @Field("company_NPWP") String company_NPWP,
            @Field("company_pkp") String company_pkp);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResultsPerson> login(
            @Field("email") String email,
            @Field("password") String password);

    @POST("profile.php")
    Call<Profile> profile(
            @Field("id") String id,
            @Field("update") String update);

    @POST("profile.php")
    Call<Profile> profileUpdate(
            @Field("id") String id,
            @Field("update") String update,
            @Field("full_name") String app,
            @Field("email") String name,
            @Field("address") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("company_name") String company_name,
            @Field("establishment") String establishment,
            @Field("company_address") String company_address,
            @Field("company_city") String company_city,
            @Field("postal_code") String postal_code,
            @Field("phone_office") String phone_office,
            @Field("fax_office") String fax_office,
            @Field("company_email") String company_email,
            @Field("company_website") String company_website,
            @Field("company_since") String company_since,
            @Field("company_NPWP") String company_NPWP,
            @Field("company_pkp") String company_pkp);
}
