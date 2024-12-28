package zeev.fraiman.stadiummaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class StaduimImage extends AppCompatActivity {

    TextView tv;
    WebView wv;
    Intent takeit;
    Stadium stadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_image);

        tv=findViewById(R.id.tv);
        wv=findViewById(R.id.wv);
        takeit=getIntent();
        stadium= (Stadium) takeit.getSerializableExtra("stadium");
        tv.setText(stadium.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String imageUrl = stadium.getImage();
        String htmlData = "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1, maximum-scale=1, user-scalable=no\"></head><body style=\"margin: 0; padding: 0;\">" +
                "<img src="+imageUrl+"style=\"width: 100%; height: auto;\"></body></html>";
        wv.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);

        wv.setWebViewClient(new WebViewClient());
    }
}