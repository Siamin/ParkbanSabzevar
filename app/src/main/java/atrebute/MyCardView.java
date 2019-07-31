package atrebute;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import aspi.parkbansabzevar.R;


public class MyCardView extends android.support.v7.widget.CardView {
    public MyCardView(Context context, AttributeSet attis) {
        super(context, attis);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.property_animator);
        this.setAnimation(animation);
    }

}
