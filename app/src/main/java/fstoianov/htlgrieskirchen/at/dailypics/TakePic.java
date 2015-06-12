package fstoianov.htlgrieskirchen.at.dailypics;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Darius17 on 12.06.2015.
 */
public class TakePic extends Activity{
    File file;
    File f;
    public TakePic() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takepic);
        takePhoto();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
         file= null;
        f= null;
        Log.i("nuller", "1");
        file =new File("/storage/emulated/0/Pictures/" + System.currentTimeMillis()+".png");

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));
        imageUri = Uri.fromFile(file);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {


                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.picId);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(this.getContentResolver(), selectedImage);

                        imageView.setImageBitmap(bitmap);
                        //imageView.setImageURI(selectedImage);
                        imageView.invalidate();
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Cameraeee", e.toString());
                    }
                }
        }
    }
}
