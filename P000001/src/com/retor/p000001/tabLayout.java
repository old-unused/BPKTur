package com.retor.p000001;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Антон on 12.02.14.
 */
public class tabLayout extends TabActivity {
    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        tabHost = (TabHost)findViewById(android.R.id.tabhost);

            // инициализация была выполнена в getTabHost
            // метод setup вызывать не нужно

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Обсуждения");
        tabSpec.setContent(new Intent(this, Browser.class));


        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tag2");
        tabSpec1.setIndicator("Авторизация");
        tabSpec1.setContent(new Intent(this, auth.class));
        tabHost.addTab(tabSpec);
        tabHost.addTab(tabSpec1);
    }



    /**
     * Returns the {@link android.widget.TabHost} the activity is using to host its tabs.
     *
     * @return the {@link android.widget.TabHost} the activity is using to host its tabs.
     */
    @Override
    public TabHost getTabHost() {
        return super.getTabHost();
    }

    /**
     * Sets the default tab that is the first tab highlighted.
     *
     * @param index the index of the default tab
     */
    @Override
    public void setDefaultTab(int index) {
        super.setDefaultTab(index);
    }
}
