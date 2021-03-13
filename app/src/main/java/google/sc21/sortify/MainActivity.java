// Sortify App 13/03/2021
// Made by Paul & Udit
// for Google's 2021 Solution Challenge

package google.sc21.sortify;

// IMPORTS
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


// MAIN
public class MainActivity extends AppCompatActivity {
    Button cameraButton;
    TextView testLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Connect GUI with Code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraButton = findViewById(R.id.cameraButton);
        testLabel = findViewById(R.id.helloLabel);

        // Camera Button Pressed
        cameraButton.setOnClickListener(v -> {
            testLabel.setText("Open Camera");
        });
    }
}