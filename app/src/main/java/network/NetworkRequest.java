package network;

import android.content.ContextWrapper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import interfaces.NetworkResponseListener;
import model.Category;

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

    private NetworkResponseListener networkResponseDelegate;

    private String url;
    private RequestQueue queue;
    private ContextWrapper context;

    public NetworkRequest(final String url, final ContextWrapper c, NetworkResponseListener delegate) {
        this.networkResponseDelegate = delegate;
        this.url = url;
        this.context = c;
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(c);
    }

    public boolean executeRequest() {
        try {
            // NetworkRequest a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                         //   if (url.equals(NetworkRequest.URL_CATEGORY)) {
                                createCategories(response);
                         //   } else if (url.startsWith(NetworkRequest.URL_SUB_CAT)) {
                         //       createSubcategories(response);
                         //   }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error retrieving data!", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Error: " + error.getMessage());
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // Create a category from each obj in the array then add them to a list
    private void createCategories(JSONObject obj) {
        try {
            JSONArray results = obj.getJSONArray("results");

            List<Category> catList = new ArrayList<>();
            for (int i=0; i < results.length(); i++) {
                Category cat = new Category();
                JSONObject object = results.getJSONObject(i);
                cat.id = object.getInt("id");
                cat.name = object.getString("name");
                cat.parent = object.getInt("parent");
                cat.children = object.getBoolean("children");
                //Category.categoryArrayList.add(cat);
                catList.add(cat);
            }

            // update listener in activity
            networkResponseDelegate.onNetworkResponseSuccess(catList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    private void createSubcategories(JSONObject obj) {
        try {
            JSONArray results = obj.getJSONArray("results");
            for (int i=0; i < results.length(); i++) {
                Subcategory subCat = new Subcategory();
                JSONObject object = results.getJSONObject(i);
                subCat.id = object.getInt("id");
                subCat.name = object.getString("name");
                subCat.parent = object.getInt("parent");
                subCat.children = object.getBoolean("children");
                Subcategory.subcategoryList.add(subCat);
            }

            // update listener in activity
            networkResponseDelegate.onNetworkResponseSuccess(Subcategory.subcategoryList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // update listener in activity
        networkResponseDelegate.onNetworkResponseSuccess(Subcategory.subcategoryList);
    }
    */
}