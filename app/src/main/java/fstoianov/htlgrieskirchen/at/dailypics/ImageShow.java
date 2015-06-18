package fstoianov.htlgrieskirchen.at.dailypics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by fstoianov on 12.06.2015.
 */
public class ImageShow extends Activity {
    byte[] binaryData;
    byte[] decoded;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow);
        Intent i = getIntent();
        binaryData = i.getByteArrayExtra("BinaryData");
        decoded = i.getByteArrayExtra("Decoded");
        image();
    }

    public void image()
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }



}
