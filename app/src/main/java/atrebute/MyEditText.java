package atrebute;

import android.content.Context;
import android.util.AttributeSet;
import aspi.parkbansabzevar.OtherMetod;


public class MyEditText extends android.support.v7.widget.AppCompatEditText {


    public MyEditText(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, "fa"));
    }

}
