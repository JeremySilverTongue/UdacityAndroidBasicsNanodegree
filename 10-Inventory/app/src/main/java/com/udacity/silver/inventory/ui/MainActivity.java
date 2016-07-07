package com.udacity.silver.inventory.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.silver.inventory.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {


    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final String DIALOG_TAG = "Product dialog";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.empty)
    View empty;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private String mCurrentPhotoPath;

    private static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        productAdapter = new ProductAdapter(this, empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        productAdapter.refresh();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        productAdapter.refresh();
    }

    public void addProduct(@SuppressWarnings("UnusedParameters") View view) {
        new AddProductDialog().show(getFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Timber.d("Fragment dismissed");
        productAdapter.refresh();
    }

    private File createImageFile() throws IOException {
        verifyStoragePermissions(this);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Timber.d(mCurrentPhotoPath);
        return image;
    }

    public void dispatchTakePictureIntent(@SuppressWarnings("UnusedParameters") View view) {
        verifyStoragePermissions(this);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Timber.e(ex, "Filed to create file to store photo");
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.udacity.silver.inventory", photoFile);
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddProductDialog.GET_IMAGE_TAG && resultCode == RESULT_OK) {
            Fragment dialog = getFragmentManager().findFragmentByTag(DIALOG_TAG);
            if (dialog != null) {
                ((AddProductDialog) dialog).receivePhoto(mCurrentPhotoPath);
            }
        }

    }


}
