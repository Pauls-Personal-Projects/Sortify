/*
 * --------------------------------------------------
 * Sortify App, Created 13/03/2021
 * Made by Paul & Abdul
 * for Google's 2021 Solution Challenge
 * https://github.com/paulpall/Sortify/
 * --------------------------------------------------
 * ExploreActivity.java is the class for our List Activity.
 * This activity contains a list of items that was requested,
 *  along with the option of an image that was taken.
 */
package google.sc21.sortify;


// REQUIRED PACKAGES:
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static google.sc21.sortify.MainActivity.DATASET_FILENAME;



/** Explore Activity Object...
 *
 * Object that represents our Explore view.
 * @author Paul
 *
 */
public class ExploreActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    //GUI Objects
    private static MyRecyclerViewAdapter adapter;
    private static RecyclerView exploreList;
    private static ImageView junkPhoto;
    private static Context context;



    /** <p>Loads A List to RecyclerView</p>
     * A Method that takes a List of Junk and Displays it in our RecyclerView.
     * @param data A List of Junk objects.
     * @since 0.7
     */
    public static void loadData(List<Junk> data) {
        exploreList.setLayoutManager(new LinearLayoutManager(ExploreActivity.context));
        adapter = new MyRecyclerViewAdapter(ExploreActivity.context, data);
        exploreList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(exploreList.getContext(), LinearLayoutManager.VERTICAL);
        exploreList.addItemDecoration(dividerItemDecoration);
    }


    /** <p>Sets the Image (Displayed over the List)</p>
     * A Method that takes a Bitmap image and sets it to display over the list.
     * @param image An image that will be displayed over the list.
     * @since 0.7
     */
    public static void setImage(Bitmap image) {
        junkPhoto.setImageBitmap(image);
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    //region PLEASE DO NOT LOOK IN HERE (terrible practise, I know :()
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



    /** <p>Main Method</p>
     * Handles Tasks on Activity Launch.
     * @since 0.7
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Tie the GUI with Code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        junkPhoto = findViewById(R.id.junkView);
        exploreList = findViewById(R.id.junkListView);
        ExploreActivity.context = getApplicationContext();

        //Empty Item to Display While Waiting for Results to Arrive
        List<Junk> template = new ArrayList<>();
        List<String> empty = new LinkedList<String>();
        template.add(new Junk("Please Wait...",empty,"",""));

        //RecyclerView Configurations
        exploreList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, template);
        adapter.setClickListener(this);
        exploreList.setAdapter(adapter);

        //Checks whether the User started the Discover mode or Camera mode
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (mode.equals("discover")) {
            junkPhoto.setVisibility(View.GONE);
            loadData(importDataset());
        }
    }
}