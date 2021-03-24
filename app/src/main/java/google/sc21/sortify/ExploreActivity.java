package google.sc21.sortify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static google.sc21.sortify.MainActivity.DATASET_FILENAME;

public class ExploreActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private static MyRecyclerViewAdapter adapter;
    private static RecyclerView exploreList;
    private static ImageView junkPhoto;
    private static Context context;
    private static List<Junk> referenceDataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        junkPhoto = findViewById(R.id.junkView);
        exploreList = findViewById(R.id.junkListView);
        ExploreActivity.context = getApplicationContext();

        List<Junk> template = new ArrayList<>();
        List<String> empty = new LinkedList<String>();
        template.add(new Junk("Please Wait...",empty,"",""));

        exploreList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, template);
        adapter.setClickListener(this);
        exploreList.setAdapter(adapter);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (mode.equals("discover")) {
            junkPhoto.setVisibility(View.GONE);
            loadData(importDataset());
        }
    }


    public static void loadData(List<Junk> data) {
        exploreList.setLayoutManager(new LinearLayoutManager(ExploreActivity.context));
        adapter = new MyRecyclerViewAdapter(ExploreActivity.context, data);
        exploreList.setAdapter(adapter);
    }


    public static void setImage(Bitmap image) {
        junkPhoto.setImageBitmap(image);
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    //region PLEASE DO NOT LOOK IN HERE (terrible practise, I know)
    private List<Junk> importDataset() {
        List<Junk> referenceData = new ArrayList<>();

        try (InputStreamReader is = new InputStreamReader(getAssets().open(DATASET_FILENAME))){
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                try (Scanner column = new Scanner(line)) {
                    column.useDelimiter(",");
                    referenceData.add(Junk.newItemFromCSV(column));
                }
            }
        } catch (IOException e) {
        }

        return referenceData;
    }
    //endregion
}