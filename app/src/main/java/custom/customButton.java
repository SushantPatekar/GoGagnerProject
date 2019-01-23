package custom;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class customButton extends android.support.v7.widget.AppCompatButton {
    public customButton(Context context) {
        super(context);
        setTypeFace(context);
    }

    public customButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context);
    }

    private void setTypeFace(Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/sf_ui_display_medium.otf");
        setTypeface(face);
    }
}
