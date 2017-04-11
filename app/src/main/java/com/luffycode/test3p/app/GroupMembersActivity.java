package com.luffycode.test3p.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;
import com.luffycode.test3p.dao.GroupMembers;
import com.luffycode.test3p.dao.Person;
import com.luffycode.test3p.helper.ConnectionRetrofit;
import com.luffycode.test3p.helper.Utils;
import com.luffycode.test3p.iface.CustomerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMembersActivity extends Test3PCompatActivity {
    private CategoryAdapter adapter;

    private List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.dashboard));
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        persons = new ArrayList<>();
        adapter = new CategoryAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getMembers();
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

        public CategoryAdapter() {

        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_members, parent, false);
            CategoryViewHolder holder = new CategoryViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, int position) {
            Person person = persons.get(position);
            holder.txtCompanyName.setText(person.getCompany_name());
            holder.txtLocation.setText(person.getCity());
            holder.txtAddress.setText(person.getAddress());
            holder.txtPhone.setText(person.getPhone());
            holder.txtEmail.setText(person.getEmail());
            holder.txtWebsite.setText(person.getCompany_website());
        }

        @Override
        public int getItemCount() {
            return persons.size();
        }
    }

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCompanyName;
        public TextView txtLocation;
        public TextView txtAddress;
        public TextView txtPhone;
        public TextView txtEmail;
        public TextView txtWebsite;

        CategoryViewHolder(View itemView) {
            super(itemView);
            txtCompanyName = (TextView) itemView.findViewById(R.id.txtCompanyName);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            txtPhone = (TextView) itemView.findViewById(R.id.txtPhone);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtWebsite = (TextView) itemView.findViewById(R.id.txtWebsite);
        }
    }

    private void getMembers(){
        final ProgressDialog dialog = new ProgressDialog(GroupMembersActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading..");
        dialog.show();

        String idUser = Utils.getPreference(GroupMembersActivity.this).getString("id", "");
        String companyName = Utils.getPreference(GroupMembersActivity.this).getString("company_name", "");
        Utils.log(idUser);
        Utils.log(companyName);

        CustomerInterface api = new ConnectionRetrofit().getRerofit().create(CustomerInterface.class);
        Call<GroupMembers> groupMembersCall = api.groups(idUser, companyName);
        groupMembersCall.enqueue(new Callback<GroupMembers>() {
            @Override
            public void onResponse(Call<GroupMembers> call, Response<GroupMembers> response) {
                GroupMembers groupMembers = response.body();
                if (groupMembers.getStatus() == Utils.STATUS_OK){
                    if (groupMembers.getResults().size() == 0){

                    }else{
                        for (int i = 0; i < groupMembers.getResults().size(); i++) {
                            Person person = groupMembers.getResults().get(i);
                            persons.add(person);
                        }
                    }

                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else{
                    Toast.makeText(GroupMembersActivity.this, groupMembers.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GroupMembers> call, Throwable t) {
                Utils.log(t.getMessage());
                dialog.dismiss();
            }
        });
    }
}
