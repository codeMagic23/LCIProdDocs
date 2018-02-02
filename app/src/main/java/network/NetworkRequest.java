package network;

import android.content.ContextWrapper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.NetworkResponseListener;
import model.ProductDocuments;

/**
 * Created by jinbody on 1/28/2018.
 */

public class NetworkRequest {

    public static final String URL_CATEGORY = "https://www.lci1.com/mylci-api.json?type=supportGroup&support=&info=&video=&history";
    public static final String URL_SUB_CAT = "https://www.lci1.com/mylci-api.json?type=supportGroup&info=&video=&history";
    public static final String URL_PROD_DOC = "https://www.lci1.com/mylci-api.json?type=supportItem&info=&video=&history";
    public static final String URL_PROD_DOC_PDF = "https://www.lci1.com/assets/content/support/manuals/Power";

/* possible params that might change
   could change to support other dynamic params
*/

    // parent ID (Needed for SUB_CAT & PROD_DOC)
    public static final String PARAM_SUPPORT = "support";


    private static final String TAG = NetworkRequest.class.getSimpleName();

    private String url;
    private RequestQueue queue;
    private ContextWrapper context;

    public NetworkRequest(final String url, final ContextWrapper c) {
        this.url = url;
        this.context = c;
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(c);
    }

    public boolean executeRequest(final Response.Listener delegate) {
        try {
            // NetworkRequest a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(url, null, delegate, null);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private void createDocs(JSONObject obj, NetworkResponseListener delegate) {
        //obj.getJSONArray("manuals").getJSONObject(0).get("title")
        try {
            String title = obj.getJSONObject("results").get("name").toString();
            List<ProductDocuments> docList = new ArrayList<>();
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
            delegate.onNetworkResponseSuccess(docList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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