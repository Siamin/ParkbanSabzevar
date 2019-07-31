package atrebute;

import android.content.Context;
import android.util.AttributeSet;

import aspi.parkbansabzevar.OtherMetod;

public class MyEnTextView extends android.support.v7.widget.AppCompatTextView {


    public MyEnTextView(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();
        this.setTypeface(om.SetFont(context, "en"));
    }

}
