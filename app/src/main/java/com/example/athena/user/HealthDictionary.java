package com.example.athena.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.athena.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HealthDictionary extends AppCompatActivity {
    private ListView listView;
    private List<String> wordList;
    private JSONObject jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_dictionary);
        listView = findViewById(R.id.listView);

        // Load and parse JSON data from file
        String json = loadJSONFromAsset();
        jsonData = parseJSONData(json);
        wordList = extractKeysFromJSON(jsonData);

        // Create an ArrayAdapter to display the word list in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wordList);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);

        // Set item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected word from the list
                String selectedWord = wordList.get(position);

                try {
                    // Retrieve the meaning of the selected word from JSON
                    String meaning = jsonData.getString(selectedWord);

                    // Display the meaning in a dialog
                    showMeaningDialog(selectedWord, meaning);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Load JSON data from file
    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("dictionary.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    // Parse JSON data and return as JSONObject
    private JSONObject parseJSONData(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Extract keys from JSON object
    private List<String> extractKeysFromJSON(JSONObject jsonObject) {
        List<String> keys = new ArrayList<>();
        if (jsonObject != null) {
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                keys.add(iterator.next());
            }
        }
        return keys;
    }

    // Show a dialog with the selected word and its meaning
    private void showMeaningDialog(String word, String meaning) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(word)
                .setMessage(meaning)
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void back(View view){

        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }
}