package com.halffull.vipbettingtips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckerListener,NavigationView.OnNavigationItemSelectedListener{

    private WebView mywebview;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mywebview=(WebView) findViewById(R.id.web_view_id);
        mywebview.loadUrl("http://www.halffull.pvip.xyz/");
        mywebview.setWebViewClient(new WebViewClient()


                                   {
                                       @Override
                                       public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                           mywebview.loadUrl("file:///android_asset/hello.html");
                                       }
                                   }



        );

        WebSettings settings=mywebview.getSettings();
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        UpdateHelper.with(this).onUpdateCheck(MainActivity.this).check();

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        /////////
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.alert);
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder.setMessage("Do you want to exit?");

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
        /////////

        /*if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }*/


    }

    @Override
    public void onUpdateCheckerListener(final String urlApp) {

        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setTitle("New Version Available")
                .setMessage("Please Update for continue for new version use")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(getApplicationContext(),""+urlApp,Toast.LENGTH_SHORT).show();
                        redirectStore(urlApp);
                    }
                })
                /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })*/
                .create();


        alertDialog.setCancelable(false);

        alertDialog.show();


    }
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int item=menuItem.getItemId();
        if (item==R.id.nav_about_us){
            startActivity(new Intent(getApplicationContext(),About_Us.class));
            finish();
        }
        else if (item==R.id.nav_contact_us){
            startActivity(new Intent(getApplicationContext(),Contact_Us.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void rate_us(MenuItem item) {
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void correct_score(MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.correctscore.correctscoreviptips")));

    }

    public void fifty_plus(MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.fiftyplus.vipbettingtips")));

    }

    public void special(MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.special.vipbettingtips")));

    }
}
