package drillwondocs.magicstudios.com.drilldowndocs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import interfaces.NetworkResponse;
import model.Category;
import model.Subcategory;
import network.Request;

public class SubcategoryActivity extends AppCompatActivity implements NetworkResponse, AdapterView.OnItemClickListener{

    ListView subCatLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
         subCatLV = findViewById(R.id.subCatLV);
         subCatLV.setOnItemClickListener(this);

        Request request = new Request(Request.URL_SUB_CAT + "&" + Request.PARAM_SUPPORT + "=" + String.valueOf(Category.getSelectedCategory().id), this, (NetworkResponse)this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

    }

    @Override
    public void updateList(ArrayList dataArray) {
        ArrayList<String> idNames = new ArrayList<>();
        for (int i=0; i<dataArray.size(); i++) {
            Subcategory cat = ((Subcategory)dataArray.get(i));
            idNames.add(cat.id + "-" + cat.name);
        }


        ArrayAdapter catAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, idNames);
        subCatLV.setAdapter(catAdapter);
    }

    @Override
    public void onBackPressed() {
        Subcategory.subcategoryList.clear();
        super.onBackPressed();
    }
}
