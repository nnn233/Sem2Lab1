package com.example.sem2lab1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button makePhoto, openWebsite, makeCall, sendMessage;
    EditText number, link, message;
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Find edittext elements by id");
        number = findViewById(R.id.phone_number);
        link = findViewById(R.id.link);

        makeCall = findViewById(R.id.make_call);
        makeCall.setOnClickListener(v -> {
            String phone_number = number.getText().toString();
            Intent phone_intent = new Intent(Intent.ACTION_DIAL);
            phone_intent.setData(Uri.parse("tel:" + phone_number));
            startActivity(phone_intent);
        });

        openWebsite = findViewById(R.id.open_website);
        openWebsite.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, InternetActivity.class);
            i.putExtra("link", link.getText().toString());
            startActivity(i);
        });

        Log.d(TAG, "Set textChangedListener to number edittext");
        number.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                makeCall.setEnabled(!s.equals(""));
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        link.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                openWebsite.setEnabled(!s.equals(""));
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        makePhoto = findViewById(R.id.make_photo);
        makePhoto.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PhotoActivity.class);
            startActivity(i);
        });

        message = findViewById(R.id.message);
        sendMessage = findViewById(R.id.send_message);
        message.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                sendMessage.setEnabled(!s.equals(""));
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        sendMessage.setOnClickListener(v -> {
            String appName = "org.telegram.messenger";
            boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);
            if (isAppInstalled) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.setPackage(appName);
                myIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());//
                startActivity(Intent.createChooser(myIntent, "Share with"));
            } else {
                Log.i(TAG, "Telegram not Installed message");
                Toast.makeText(MainActivity.this, "Telegram not Installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        Log.w(TAG, "Method getInstalledPackages may cause an exception on Android 11");
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo info : packages) {
            if (Objects.equals(info.packageName, appName))
                return true;
        }
        return false;
    }
}