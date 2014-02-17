package com.retor.p000001;


import android.app.ActionBar;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableLayout;
import android.app.Activity;
import android.content.Intent;


public class Splash extends Activity{


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getActionBar();//getSupportActionBar();
        actionBar.hide();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);


        Animation anim = AnimationUtils.loadAnimation(Splash.this, R.anim.fade_in);
        TableLayout tl = (TableLayout)findViewById(R.id.TableLayout1);
        if (anim != null) {
            anim.setAnimationListener(new AnimationListener(){
                public void onAnimationEnd(Animation animation){
                    Splash.this.finish();
                    startActivity(new Intent(Splash.this, main.class));
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                }
            });
        }
        tl.startAnimation(anim);
  
    }

    
}
