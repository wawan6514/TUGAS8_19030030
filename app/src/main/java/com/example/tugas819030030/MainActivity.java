package com.example.tugas819030030;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1000;
    private static final int RECORD_CAPTURE_CODE = 1001;

    Button RekamBtn;

    Uri video_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RekamBtn = findViewById(R.id.rekam_btn);

        RekamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        startRecord();
                    }
                }
                else {
                    startRecord();
                }
            }
        });
    }

    private void startRecord() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, "New Video");
        values.put(MediaStore.Video.Media.DESCRIPTION, "From the Camera");
        video_uri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

        Intent micIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        micIntent.putExtra(MediaStore.EXTRA_OUTPUT, video_uri);
        startActivityForResult(micIntent, RECORD_CAPTURE_CODE);
    }
}