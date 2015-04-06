package com.platzi.leerdatosdesdeandroidwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mariux.teleport.lib.TeleportClient;

import java.util.List;

public class MainActivity extends Activity {

    private TextView mTextView;

    private static final int SPEECH_REQUEST_CODE = 001;
    Button button;
    TeleportClient mTeleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTeleportClient = new TeleportClient(this);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                button = (Button) findViewById(R.id.button_wear);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        displaySpeechRecognizer();

                    }
                });


            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();
    }


    private void displaySpeechRecognizer() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {

            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String myVoice = results.get(0);
            Log.i("Spoken", myVoice);

            mTeleportClient.syncString("myVoice", myVoice);
            Toast.makeText(getApplicationContext(), "Usted dijo: " +myVoice , Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
