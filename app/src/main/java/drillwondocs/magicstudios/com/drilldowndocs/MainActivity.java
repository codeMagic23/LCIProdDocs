package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.NetworkResponseListener;
import model.Category;
import network.NetworkRequest;

public class MainActivity extends AppCompatActivity implements NetworkResponseListener, AdapterView.OnItemClickListener, Response.Listener {

    ListView categoryLV;

    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryLV = findViewById(R.id.catLV);
        categoryLV.setOnItemClickListener(this);
        Intent receiveIntent = getIntent();
        String requestURL = NetworkRequest.URL_CATEGORY;
        if (receiveIntent != null && receiveIntent.getExtras() != null) {
            if (!receiveIntent.getBooleanExtra("isChild", false)) {
                requestURL = NetworkRequest.URL_SUB_CAT + "&" + NetworkRequest.PARAM_SUPPORT + "="
                        + String.valueOf(Category.getSelectedCategory().id);
            }
        }
        NetworkRequest networkRequest = new NetworkRequest(requestURL, this);

        // show message if trouble executing networkRequest
        if (!networkRequest.executeRequest(this)) {
            Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkResponseSuccess(List categories) {
        categoryList = categories;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Category.selectCategory(categoryList.get(position));
        if (categoryList.get(position).isSubCategory(categoryList.get(position))) {
            int id = categoryList.get(position).id;
            Intent i = new Intent(this, ProdDocActivity.class);
            i.putExtra(Category.SUB_CAT_ID, id);
            startActivity(i);
        } else {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("isChild", categoryList.get(position).isSubCategory(categoryList.get(position)));
            startActivity(i);
        }
    }

    @Override
    public void onResponse(Object response) {
        categoryList = createCategories((JSONObject)response);
        updateList();
    }

    // Create a category from each obj in the array then add them to a list
    private List<Category> createCategories(JSONObject obj) {
        List<Category> catList = new ArrayList<>();
        try {
            JSONArray results = obj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                Category cat = new Category();
                JSONObject object = results.getJSONObject(i);
                cat.id = object.getInt("id");
                cat.name = object.getString("name");
                cat.parent = object.getInt("parent");
                cat.children = object.getBoolean("children");
                catList.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return catList;
    }

    private void updateList() {
        List<String> idNames = new ArrayList<>();
        for (Category cat : categoryList) {
            idNames.add(cat.id + "-" + cat.name);
        }

        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, idNames);
        categoryLV.setAdapter(catAdapter);
    }
}
