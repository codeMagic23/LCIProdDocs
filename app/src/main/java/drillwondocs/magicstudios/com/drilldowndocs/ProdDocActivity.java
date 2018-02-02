package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.NetworkResponseListener;
import model.Category;
import model.ProductDocuments;
import network.NetworkRequest;

public class ProdDocActivity extends AppCompatActivity implements NetworkResponseListener, Response.Listener{

    private List<ProductDocuments> documentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_doc);

        Intent i = getIntent();
        int supportGroup = 0;
        if (i.getExtras() != null) {
            supportGroup = i.getIntExtra(Category.SUB_CAT_ID, 0);
        }

        if (supportGroup > 0) {
            NetworkRequest networkRequest = new NetworkRequest(NetworkRequest.URL_PROD_DOC + "&" + NetworkRequest.PARAM_SUPPORT + "="
                    + String.valueOf(supportGroup), this);

            // show message if trouble executing networkRequest
            if (!networkRequest.executeRequest(this)) {
                Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkResponseSuccess(List dataArray) {
        Log.i("TAG", "Data at index 0: " + dataArray.get(0));
    }

    @Override
    public void onResponse(Object response) {
        documentList = createDocs((JSONObject)response);
        //updateList(documentList);
    }

    private List<ProductDocuments> createDocs(JSONObject obj) {
        List<ProductDocuments> docList = new ArrayList<>();
        try {
            String title = obj.getJSONObject("results").get("name").toString();

            JSONArray manuals = obj.getJSONArray("manuals");
            JSONArray components = obj.getJSONArray("components");
            JSONArray assemblies = obj.getJSONArray("assemblies");
            List<ProductDocuments> tempList = new ArrayList();
            /*
            tempList = createProdObjects(manuals, ProductDocuments.TYPE_MANUAL);
            for (ProductDocuments doc : tempList) {
                docList.add(doc);
            }
            */
            createProdObjects(manuals, ProductDocuments.TYPE_MANUAL, docList);
            createProdObjects(components, ProductDocuments.TYPE_COMPONENT, docList);
            createProdObjects(assemblies, ProductDocuments.TYPE_ASSEMBLY, docList);

            // update listener in activity
            return docList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return docList;
    }

    // create class member variables from JSONArray param
    private List<ProductDocuments> createProdObjects(JSONArray array, String type, List docList) {
        ProductDocuments prod = null;
        List<ProductDocuments> documentList = new ArrayList();
        for (int i=0; i < array.length(); i++) {
            JSONObject object = null;
            try {
                object = array.getJSONObject(i);
                prod = new ProductDocuments();
                prod.migxID = object.getString("MIGX_id");
                prod.title = object.getString("title");
                prod.pdf = object.getString("pdf");
                prod.image = object.getString("image");
                prod.type = type;
                docList.add(prod);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // add to member list
        }
        return docList;
    }
}
