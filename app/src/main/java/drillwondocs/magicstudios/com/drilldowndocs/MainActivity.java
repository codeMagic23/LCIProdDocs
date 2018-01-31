package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import interfaces.NetworkResponse;
import model.Category;
import network.Request;

public class MainActivity extends AppCompatActivity implements NetworkResponse, AdapterView.OnItemClickListener {

    ListView categoryLV;

    private static ArrayList<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryLV = findViewById(R.id.catLV);
        categoryLV.setOnItemClickListener(this);
        Request request = new Request(Request.URL_CATEGORY, this, (NetworkResponse)this);
    }

    @Override
    public void updateList(ArrayList categories) {
        categoryList = categories;
        ArrayList<String> idNames = new ArrayList<>();
        for (int i=0; i<categories.size(); i++) {
            Category cat = ((Category)categories.get(i));
            idNames.add(cat.id + "-" + cat.name);
        }


        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, idNames);
        categoryLV.setAdapter(catAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Category item = (Category)adapterView.getItemAtPosition(position);
        Toast.makeText(this, "Item Clicked: " + adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
        Category.selectCategory(categoryList.get(position));
        Intent i = new Intent(this, SubcategoryActivity.class);
        startActivity(i);
    }
}
