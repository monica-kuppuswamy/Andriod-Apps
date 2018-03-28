package com.example.monic.storageapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button upload;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.android_image);
        upload = (Button) findViewById(R.id.button_upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToStorage();
            }
        });
    }

    private void uploadImageToStorage() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        imageView.setDrawingCacheEnabled(false);
        byte[] data = baos.toByteArray();

        String path = "images/" + UUID.randomUUID() + ".png";
        StorageReference imageRef = storage.getReference(path);
        StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("text", "this is an android image")
                .build();

        UploadTask uploadTask = imageRef.putBytes(data, metadata);
        uploadTask.addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri url = taskSnapshot.getDownloadUrl();
                Log.d("demo", "URL of the image in firebse : " + url.toString());
            }
        });
    }
}
