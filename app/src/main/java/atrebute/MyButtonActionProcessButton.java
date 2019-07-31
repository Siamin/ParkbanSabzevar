package atrebute;

import android.content.Context;
import android.util.AttributeSet;

import aspi.parkbansabzevar.OtherMetod;


public class MyButtonActionProcessButton extends com.dd.processbutton.iml.ActionProcessButton {


    public MyButtonActionProcessButton(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, "fa"));
        //this.setBackgroundColor(context.getResources().getColor(R.color.green));
    }

}
