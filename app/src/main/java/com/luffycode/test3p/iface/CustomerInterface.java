package com.luffycode.test3p.iface;

import com.luffycode.test3p.dao.CityDao;
import com.luffycode.test3p.dao.EstablishmentResults;
import com.luffycode.test3p.dao.GroupMembers;
import com.luffycode.test3p.dao.Profile;
import com.luffycode.test3p.dao.PersonResults;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by MacMini on 2/24/17.
 */

public interface CustomerInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<PersonResults> register(
            @Field("full_name") String app,
            @Field("email") String name,
            @Field("address") String email,
            @Field("city") String city,
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
    Call<PersonResults> login(
            @Field("email") String email,
            @Field("password") String password);

    @POST("profile.php")
    Call<Profile> profile(
            @Field("id") String id,
            @Field("update") String update);

    @GET("city.php")
    Call<CityDao> city();

    @GET("establishment.php")
    Call<EstablishmentResults> establishment();

    @FormUrlEncoded
    @POST("group_members.php")
    Call<GroupMembers> groups(
            @Field("id") String id,
            @Field("company_name") String company_name
    );

    @FormUrlEncoded
    @POST("profile.php")
    Call<PersonResults> profileUpdate(
            @Field("id") String id,
            @Field("update") String update,
            @Field("full_name") String app,
            @Field("email") String name,
            @Field("address") String email,
            @Field("city") String city,
            @Field("phone") String phone,
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
