package com.retor.p000001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static com.perm.kate.api.Auth.*;

/**
 * Created by retor on 19.01.14.
 */
public class auth extends Activity {
    WebView wv;
    private static final String TAG = "Kate.LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("AUTH");
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.auth);
        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.clearCache(true);
        wv.setWebViewClient(new VkontakteWebViewClient());

/*        //otherwise CookieManager will fall with java.lang.IllegalStateException: CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();*/
        String url= getUrl(Cons.API_ID, getSettings());
        wv.loadUrl(url);

    }

    class VkontakteWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }

    private void parseUrl(String url) {
        try {
            if(url==null)
                return;
            Log.i(TAG, "url=" + url);
            if(url.startsWith(redirect_url))
            {
                if(!url.contains("error=")){
                    String[] auth= parseRedirectUrl(url);
                    Intent intent=new Intent();
                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));
                    setResult(Activity.RESULT_OK, intent);
                    this.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}