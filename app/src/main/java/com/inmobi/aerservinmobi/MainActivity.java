package com.inmobi.aerservinmobi;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.aerserv.sdk.*;
import com.aerserv.sdk.AerServVirtualCurrency;
import com.aerserv.sdk.utils.UrlBuilder;

import java.util.List;

import static android.util.Log.d;
import static com.aerserv.sdk.utils.WebViewJSRunner.LOG_TAG;

public class MainActivity extends AppCompatActivity {

    Button show;

    Button load;
    private AerServBanner banner;


    private AerServEventListener listener = new AerServEventListener(){
        @Override
        public void onAerServEvent(final AerServEvent event, final List<Object> args){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msg = null;
                    AerServVirtualCurrency vc = null;
                    AerServTransactionInformation ti = null;
                    switch (event) {
                        case AD_FAILED:
                            if (args.size() > 1) {
                                Integer adFailedCode =
                                        (Integer) args.get(AerServEventListener.AD_FAILED_CODE);
                                String adFailedReason =
                                        (String) args.get(AerServEventListener.AD_FAILED_REASON);
                                msg = "Ad failed with code=" + adFailedCode + ", reason=" + adFailedReason;
                            } else {
                                msg = "Ad Failed with message: " + args.get(0).toString();
                            }
                            break;
                        case VC_READY:
                            vc = (AerServVirtualCurrency) args.get(0);
                            msg = "Virtual Currency PLC has loaded:"
                                    + "\n name=" + vc.getName()
                                    + "\n amount=" + vc.getAmount().toString()
                                    + "\n buyerName=" + vc.getBuyerName()
                                    + "\n buyerPrice=" + vc.getBuyerPrice();
                            break;
                        case VC_REWARDED:
                            vc = (AerServVirtualCurrency) args.get(0);
                            msg = "Virtual Currency PLC has rewarded:"
                                    + "\n name=" + vc.getName()
                                    + "\n amount=" + vc.getAmount().toString()
                                    + "\n buyerName=" + vc.getBuyerName()
                                    + "\n buyerPrice=" + vc.getBuyerPrice();
                            break;
                        case LOAD_TRANSACTION:
                            ti = (AerServTransactionInformation) args.get(0);
                            msg = "Load Transaction Information PLC has:"
                                    + "\n buyerName=" + ti.getBuyerName()
                                    + "\n buyerPrice=" + ti.getBuyerPrice();
                            break;
                        case SHOW_TRANSACTION:
                            ti = (AerServTransactionInformation) args.get(0);
                            msg = "Show Transaction Information PLC has:"
                                    + "\n buyerName=" + ti.getBuyerName()
                                    + "\n buyerPrice=" + ti.getBuyerPrice();
                            break;
                        default:
                            msg = event.toString() + " event fired with args: " + args.toString();
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, msg);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (Button) findViewById(R.id.button2);
        load = (Button) findViewById(R.id.button);

        AerServSdk.init(MainActivity.this, "1012563");
         AerServConfig config = new AerServConfig(this, "1043542")
                .setEventListener(listener)
                .setRefreshInterval(60);
        banner = (AerServBanner) findViewById(R.id.banner);
        banner.configure(config).show();
        AerServSdk.setGdprConsentFlag( this, false);


        AerServSdk.getGdprConsentFlag(this);

        banner.configure(config).show();



    }


    public void showAd(View v) {

        //banner.configure(config).show();
    }


    public void LoadAd(View v) {


    }




}
