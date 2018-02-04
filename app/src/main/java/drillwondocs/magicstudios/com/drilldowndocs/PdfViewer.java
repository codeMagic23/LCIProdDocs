package drillwondocs.magicstudios.com.drilldowndocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class PdfViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Toast.makeText(this, "Loadin PDF...", Toast.LENGTH_SHORT).show();
        String url = getIntent().getExtras().getString("url");

        openPDF(url);
    }

    private void openPDF(String url) {
        String doc="<iframe src='"+url+"' width='100%' height='100%' style='border: none;'></iframe>";
        WebView webView = findViewById(R.id.pdfWV);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(doc, "text/html", "UTF-8");
    }
}
