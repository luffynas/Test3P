package com.luffycode.test3p.app;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;

public class GroupMembersActivity extends Test3PCompatActivity {
    private CategoryAdapter adapter;

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

        adapter = new CategoryAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {
        /*public TextView today;
        public TextView title;
        public TextView time;
        public TextView price;
        public TextView status;*/

        CategoryViewHolder(View itemView) {
            super(itemView);
            /*today = (TextView) itemView.findViewById(R.id.today);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            price = (TextView) itemView.findViewById(R.id.price);
            status = (TextView) itemView.findViewById(R.id.status);*/
        }
    }
}
