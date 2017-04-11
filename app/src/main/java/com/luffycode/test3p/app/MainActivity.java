package com.luffycode.test3p.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.luffycode.test3p.R;
import com.luffycode.test3p.Test3PCompatActivity;

public class MainActivity extends Test3PCompatActivity {
    private CardView cardProfile;
    private CardView cardGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.dashboard));
            mToolbar.setNavigationIcon(R.drawable.ic_drawer);
            setSupportActionBar(mToolbar);
        }

        cardProfile = (CardView) findViewById(R.id.cardProfile);
        cardGroup = (CardView) findViewById(R.id.cardGroup);

        cardProfile.setOnClickListener(listener);
        cardGroup.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.cardProfile:

                    break;
                case R.id.cardGroup:
                    intent = new Intent(MainActivity.this, GroupMembersActivity.class);
                    startActivity(intent);
                    overridePendingTransitionEnter();
                    break;
            }
        }
    };
}
