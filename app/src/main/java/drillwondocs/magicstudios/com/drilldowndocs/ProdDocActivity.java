package drillwondocs.magicstudios.com.drilldowndocs;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import adapters.DocsAdapter;
import interfaces.NetworkResponseListener;
import model.Category;
import model.ListParent;
import model.ProductDocuments;
import network.NetworkRequest;

public class ProdDocActivity extends ExpandableListActivity implements NetworkResponseListener, Response.Listener, View.OnClickListener{

    private List<ProductDocuments> documentList;
    private ExpandableListAdapter adapter;

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
        List<ProductDocuments> prodList = createDocs((JSONObject)response);

        //initialize list for static headings being used in ExpandableListView
        ListParent.clearAndInitParentList();

        getExpandableListView().setAdapter(new DocsAdapter(this, prodList, this.getExpandableListView()));
    }

    private List<ProductDocuments> createDocs(JSONObject obj) {
        List<ProductDocuments> docList = new ArrayList<>();
        try {
            JSONArray manuals = obj.getJSONArray("manuals");
            JSONArray components = obj.getJSONArray("components");
            JSONArray assemblies = obj.getJSONArray("assemblies");
            List<ProductDocuments> tempList = new ArrayList();
            createProdObjects(manuals, ProductDocuments.TYPE_MANUAL, docList);
            createProdObjects(components, ProductDocuments.TYPE_COMPONENT, docList);
            createProdObjects(assemblies, ProductDocuments.TYPE_ASSEMBLY, docList);

            return docList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return docList;
    }

    // create class member variables from JSONArray param
    private ProductDocuments createProdObjects(JSONArray array, String type, List docList) {
        ProductDocuments prod = new ProductDocuments();
        prod.type = type;
        for (int i=0; i < array.length(); i++) {
            JSONObject object = null;
            try {
                object = array.getJSONObject(i);

                ProductDocuments.ChildItem child = prod.new ChildItem();
                child.migxID = object.getString("MIGX_id");
                child.title = object.getString("title");
                child.pdf = object.getString("pdf");
                child.image = object.getString("image");
                prod.children.add(child);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // add to member list
        }
        docList.add(prod);
        return prod;
    }

    @Override
    public void onClick(View view) {
        String url = "http://docs.google.com/gview?embedded=true&url=" + (String)view.getTag();
        Intent i = new Intent(this, PdfViewer.class);
        i.putExtra("url", url);
        startActivity(i);
    }
}
