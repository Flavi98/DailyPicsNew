package fstoianov.htlgrieskirchen.at.dailypics;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by fstoianov on 12.06.2015.
 */
public class ImageShow extends ImageView {

    public ImageShow(Context context) {
        super(context);
    }

    public ImageShow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageShow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageShow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
