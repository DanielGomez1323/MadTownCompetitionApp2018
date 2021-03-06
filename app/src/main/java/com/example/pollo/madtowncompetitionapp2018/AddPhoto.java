package com.example.pollo.madtowncompetitionapp2018;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhoto extends AppCompatActivity {
    Button cameraButton;
    Button cameraRoll;
    Button savePictureButton;
    ImageButton rotateBtn;
    ImageButton homeButton;
    ImageView img;
    Bitmap bp = null;
    EditText teamNumberEnter;
    int width;
    int height;
    String teamNumber;
    String mCurrentPhotoPath;
    String imageFileName;
    String fname = null;
    SQLiteDatabase myDB = null;
    Cursor c;
    static final int REQUEST_TAKE_PHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        cameraButton = findViewById(R.id.camButton);
        cameraRoll = findViewById(R.id.cameraRollButton);
        savePictureButton = findViewById(R.id.savePicButton);
        rotateBtn = findViewById(R.id.imageButton);
        homeButton = findViewById(R.id.Home);
        img = findViewById(R.id.imageView);
        teamNumberEnter = findViewById(R.id.photoNumberEditText);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        if(savedInstanceState != null){
            bp = BitmapFactory.decodeByteArray(savedInstanceState.getByteArray("bp"), 0, savedInstanceState.getByteArray("bp").length);
            img.setImageBitmap(bp);
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessCamera();
            }
        });
        cameraRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraRollOpen();
            }
        });
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bp != null){
                    bp = rotateImage(bp, 90);
                    img.setImageBitmap(bp);
                }
            }
        });
        savePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePic();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AppMenu.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public void accessCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pollo.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void cameraRollOpen(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String th = pictureDirectory.getPath();
        Uri data = Uri.parse(th);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK) {
            bp = BitmapFactory.decodeFile(fname);
            /*Uri imageUri = getImageContentUri(this, new File(fname));
            try {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                ExifInterface ei = new ExifInterface(filePath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,6);
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bp = rotateImage(bp, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bp = rotateImage(bp, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bp = rotateImage(bp, 270);
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        break;
                }
                img.setImageBitmap(bp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            img.setImageBitmap(bp);
        }else if(requestCode == 1 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            String path = imageUri.getPath();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                bp = BitmapFactory.decodeStream(inputStream);

                Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                ExifInterface ei = new ExifInterface(filePath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,6);
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        bp = rotateImage(bp, 90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        bp = rotateImage(bp, 180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        bp = rotateImage(bp, 270);
                        break;
                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        break;
                }
                img.setImageBitmap(bp);
                saveToDirectory(bp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bp != null) {
            byte[] bytes = bitmapToByteArray();
            outState.putByteArray("bp", bytes);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }


    public void savePic(){
        teamNumber = teamNumberEnter.getText().toString();
        ContentValues args = new ContentValues();
        myDB = openOrCreateDatabase("FRC", MODE_PRIVATE, null);
        if(fname != null) {
            if(teamNumber.length() > 0 && teamNumber.length() <= 4) {
                args.put("teamNumber", teamNumber);
                args.put("pic1", fname);
                myDB.insert("TeamPictures", null, args);
                myDB.close();
                Toast.makeText(getApplicationContext(), "Saved Picture!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Please enter a valid team number!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Select a picture to save!", Toast.LENGTH_SHORT).show();
        }
    }
    public byte[] bitmapToByteArray(){
        byte[] byteArray = null;
        if(bp != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        return byteArray;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_app";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(
                storageDir,
                imageFileName+  /* prefix */
                        ".jpeg"         /* suffix */
                      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:/" + image.getAbsolutePath();
        fname = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName).getAbsolutePath();
        fname += ".jpeg";
        return image;
    }

    private void saveToDirectory(Bitmap bitmapImage){
        FileOutputStream fos = null;
        try {
            File file = createImageFile();
            fos = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
