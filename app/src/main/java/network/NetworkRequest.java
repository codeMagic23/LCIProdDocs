package network;

import android.content.ContextWrapper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    public NetworkRequest(final String url, final ContextWrapper c) {
        this.url = url;
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
}