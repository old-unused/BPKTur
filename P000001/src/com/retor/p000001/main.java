/**
 * 
 */
package com.retor.p000001;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author retor
 */
public class main extends Activity implements View.OnClickListener {
//=============
//===============

    Button exit, auth, start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getActionBar();//getSupportActionBar();
        actionBar.hide();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        exit = (Button) findViewById(R.id.button);
        auth = (Button) findViewById(R.id.button3);
        start = (Button) findViewById(R.id.button2);
        exit.setOnClickListener(this);
        auth.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                super.finish();
                break;
            case R.id.button2:
                Intent inter = new Intent();
                inter.setFlags(1);
                startActivity(new Intent(this, tabLayout.class));
                break;
        }
    }

}