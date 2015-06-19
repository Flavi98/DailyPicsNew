package fstoianov.htlgrieskirchen.at.dailypics;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    DrawingView dv ;
    private Paint paint;
    Bitmap bitmap;
    //private DrawingManager mDrawingManager=null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.save:
                saveViewAsImage();
                return true;
            case R.id.showpics:
                Intent intent = new Intent(this, PicsList.class);
                startActivity(intent);
                return true;
            case R.id.upload:
                uploadImageToServer();
                return true;
            case R.id.takepic:
                Intent intent1 = new Intent(this, TakePic.class);
                startActivity(intent1);
                return true;

        }
        return super.onCreateOptionsMenu((Menu) item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(8);
    }

    public void saveViewAsImage(){
        DrawingView content = dv;
        content.setDrawingCacheEnabled(true);
        bitmap = content.getDrawingCache();
        File file, f = null;
        Log.i("nuller", "1");

        file =new File("/storage/emulated/0/Pictures");
        Log.i("asdf", file.getAbsolutePath().toString());
        f = new File(file.getAbsolutePath()+File.separator+ System.currentTimeMillis()+".png");
        FileOutputStream ostream = null;
        try {
            Log.i("nuller", "5");
            ostream = new FileOutputStream(f);
            Log.i("nuller", "6");
        } catch (FileNotFoundException e1) {
            Log.i("nuller", "7");
            e1.printStackTrace();
        }
        Log.i("nuller", "9");

        //BAST IS NEICH
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);

        try {
            ostream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        bitmap = null;

    }

    public void uploadImageToServer(){
        DrawingView content = dv;
        content.setDrawingCacheEnabled(true);
        bitmap = content.getDrawingCache();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        final byte[] byteArray = byteArrayOutputStream .toByteArray();
        final String data = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.i("upload", byteArray.toString());

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading");
        pd.create();
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://www.dailypics.16mb.com/insert_pic_dailypics.php");

                try {
                    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(2);
                    nameValuePairList.add(new BasicNameValuePair("file", data));
                    nameValuePairList.add(new BasicNameValuePair("picname", System.currentTimeMillis()+".png"));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

                    HttpResponse response = httpClient.execute(httpPost);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        };
        t.start();


    }



    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap bitmap;
        private Canvas canvas;
        private Path path;
        private Paint   mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context=c;
            path = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.GREEN);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);


        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);

        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawBitmap( bitmap, 0, 0, mBitmapPaint);

            canvas.drawPath( path,  paint);

            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 0;

        private void touch_start(float x, float y) {
            path.reset();
            path.moveTo(x, y);
            mX = x;
            mY = y;
        }
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                path.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }
        private void touch_up() {
            path.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to ljklmlmour offscrlklpkjeen
            //blabla
            canvas.drawPath(path,  paint);
            // kill this so we don't double draw
            path.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    break;
            }
            return true;
        }
    }


}
