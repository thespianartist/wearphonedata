package com.platzi.leerdatosdesdeandroidwear;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;


public class MainActivity extends ActionBarActivity {

    TeleportClient mTeleportClient;
    TextView responsetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        responsetext = (TextView) findViewById(R.id.text_response);

        mTeleportClient = new TeleportClient(this);
        mTeleportClient.setOnSyncDataItemTask(new ShowToastHelloWorldTask());
    }





    public class ShowToastHelloWorldTask extends TeleportClient.OnSyncDataItemTask {

        @Override
        protected void onPostExecute(DataMap dataMap) {

            String myVoice = dataMap.getString("myVoice");

            Toast.makeText(getApplication(),"Texto Obtenido: "+myVoice,Toast.LENGTH_LONG).show();

            responsetext.setText(myVoice);

            mTeleportClient.setOnSyncDataItemTask(new ShowToastHelloWorldTask());

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.connect();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mTeleportClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTeleportClient.disconnect();
    }
}
