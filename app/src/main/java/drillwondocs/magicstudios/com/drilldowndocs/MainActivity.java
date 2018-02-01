package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import interfaces.NetworkResponseListener;
import model.Category;
import network.NetworkRequest;

public class MainActivity extends AppCompatActivity implements NetworkResponseListener, AdapterView.OnItemClickListener {

    ListView categoryLV;

    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryLV = findViewById(R.id.catLV);
        categoryLV.setOnItemClickListener(this);
        NetworkRequest networkRequest = new NetworkRequest(NetworkRequest.URL_CATEGORY, this, this);

        // show message if trouble executing networkRequest
        if (!networkRequest.executeRequest()) {
            Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkResponseSuccess(List categories) {
        categoryList = categories;

        List<String> idNames = new ArrayList<>();
        for (Category cat : categoryList) {
            idNames.add(cat.id + "-" + cat.name);
        }

        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, idNames);
        categoryLV.setAdapter(catAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Category.selectCategory(categoryList.get(position));
        Intent i = new Intent(this, SubcategoryActivity.class);
        startActivity(i);
    }
}
