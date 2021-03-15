// Sortify App 13/03/2021
// Made by Paul & Udit
// for Google's 2021 Solution Challenge

package google.sc21.sortify;

// IMPORTS
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


// MAIN
public class MainActivity extends AppCompatActivity {
    // GUI Objects:
    Button cameraButton;
    TextView testLabel;
    ImageView imageBox;
    // Constant for our Image Capture:
    static final int REQUEST_IMAGE_CAPTURE = 1;



    // Calls Camera Activity:
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Log.e(String.valueOf(e), "Camera Intent not Found: ");
            testLabel.setText("Houston, we have a problem!?");
        }
    }
    // Returns Image from Camera Activity:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBox.setImageBitmap(imageBitmap);
        }
    }



    //MAIN:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Connect GUI with Code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraButton = findViewById(R.id.cameraButton);
        testLabel = findViewById(R.id.helloLabel);
        imageBox = findViewById(R.id.photoView);

        // Camera Button Pressed
        cameraButton.setOnClickListener(v -> {
            testLabel.setText("Open Camera");
            dispatchTakePictureIntent();
        });
    }
}