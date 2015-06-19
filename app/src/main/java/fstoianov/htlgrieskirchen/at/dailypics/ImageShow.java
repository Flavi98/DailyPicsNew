package fstoianov.htlgrieskirchen.at.dailypics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fstoianov on 12.06.2015.
 */
public class ImageShow extends ActionBarActivity {
    String name;
    WebView webview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        webview = (WebView) findViewById(R.id.webView);

        image(name);
    }

    public void image(String name) {
        final String nameInsert = name;
        Log.i("desisaguadalog", nameInsert);

        webview.loadUrl("http://www.dailypics.16mb.com/get_pic_dailypics.php?name=" + nameInsert);


    }

}
