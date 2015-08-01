package com.pac.roman.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Roman on 3/25/2015.
 */
public class HomePageActivity extends Activity {
    Button childrenManager;
    Button manageWays;
    Button schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        childrenManager = (Button)findViewById(R.id.childrenManagerBtnID);
        manageWays = (Button)findViewById(R.id.waysManagerBtnID);
        schedule = (Button)findViewById(R.id.schuduleBtnID);
    }

    public void setChildrenManagerClickOn(View view){
        Intent intent =  new Intent(HomePageActivity.this, ChildrenActivity.class);
        startActivity(intent);
    }
    public void OnClickManageWays(View view){
        Intent intent =  new Intent(HomePageActivity.this, ManageWays.class);
        startActivity(intent);
    }

}