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
import model.Subcategory;
import network.Request;

public class SubcategoryActivity extends AppCompatActivity implements NetworkResponseListener, AdapterView.OnItemClickListener{

    public static final String SUB_CAT_ID = "subCategoryID";
    ListView subCatLV;

    List<Subcategory> subcategoryList;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
         subCatLV = findViewById(R.id.subCatLV);
         subCatLV.setOnItemClickListener(this);

        Request request = new Request(Request.URL_SUB_CAT + "&" + Request.PARAM_SUPPORT + "="
                + String.valueOf(Category.getSelectedCategory().id), this, this);

        // show message if trouble executing request
        if (!request.executeRequest()) {
            Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        int id = categoryList.get(position).id;
        Intent i = new Intent(this, ProdDocActivity.class);
        i.putExtra(SUB_CAT_ID, id);
        startActivity(i);
    }

    @Override
    public void onNetworkResponseSuccess(List dataArray) {
        categoryList = dataArray;
        List<Category> data = dataArray;
        ArrayList<String> idNames = new ArrayList<>();
        for (Category item : data) {
            idNames.add(item.id + "-" + item.name);
        }

        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, idNames);
        subCatLV.setAdapter(catAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
