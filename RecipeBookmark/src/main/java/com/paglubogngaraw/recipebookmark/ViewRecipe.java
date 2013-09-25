package com.paglubogngaraw.recipebookmark;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ViewRecipe extends Activity {
    private WebView webView;
    private ShareActionProvider mShare;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrecipe);

        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setMax(100);

        String recipeName = getIntent().getStringExtra("name");
        String recipeUrl = getIntent().getStringExtra("url");
        setTitle(recipeName);

        webView = (WebView) findViewById(R.id.webView_viewRecipe);

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                ViewRecipe.this.setValue(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Toast.makeText(ViewRecipe.this, "Oops! "+ description, Toast.LENGTH_SHORT).show();
            }
        });

        WebSettings ws = webView.getSettings();
        ws.setAppCacheMaxSize(5 * 1024 * 1024); //5MB
        ws.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        ws.setAllowFileAccess(true);
        ws.setAppCacheEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_DEFAULT); //load online site by default
        ws.setJavaScriptEnabled(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        ws.setUseWideViewPort(true);

        if(!isNetworkAvailable()){ //load offline site
            ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(recipeUrl);
        ViewRecipe.this.progress.setProgress(0);

    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void setValue(int progress){
        this.progress.setProgress(progress);
        if(progress == 100){
            //Toast.makeText(ViewRecipe.this, "Loaded: " + progress, Toast.LENGTH_SHORT).show();
            this.progress.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_recipe, menu);
        MenuItem item = menu.findItem(R.id.action_shareRecipe);
        mShare = (ShareActionProvider) item.getActionProvider();

        Intent shareRecipe = new Intent();
        shareRecipe.setAction(Intent.ACTION_SEND);
        shareRecipe.putExtra(Intent.EXTRA_TEXT,getIntent().getStringExtra("url"));
        shareRecipe.setType("text/plain");
        mShare.setShareIntent(shareRecipe);

        return true;
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
