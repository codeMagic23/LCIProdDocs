package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import interfaces.NetworkResponseListener;
import network.NetworkRequest;

public class ProdDocActivity extends AppCompatActivity implements NetworkResponseListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_doc);

        Intent i = getIntent();
        int supportGroup = 0;
        if (i.getExtras() != null) {
            supportGroup = i.getIntExtra(SubcategoryActivity.SUB_CAT_ID, 0);
        }

        if (supportGroup > 0) {
            NetworkRequest networkRequest = new NetworkRequest(NetworkRequest.URL_PROD_DOC + "&" + NetworkRequest.PARAM_SUPPORT + "="
                    + String.valueOf(supportGroup), this, this);

            // show message if trouble executing networkRequest
            if (!networkRequest.executeRequest()) {
                Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "There was a problem retrieving data. Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkResponseSuccess(List dataArray) {

    }
}
