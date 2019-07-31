package aspi.parkbansabzevar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class Recycler_sharj extends RecyclerView.Adapter<Recycler_sharj.item> {

    List<Contant_sharj> cl;
    Context context;
    OtherMetod om = new OtherMetod();


    public Recycler_sharj(List<Contant_sharj> CL, Context context) {
        this.cl = CL;
        this.context = context;
    }

    @Override
    public item onCreateViewHolder(ViewGroup parent, int viewType) {
        View rec = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sharj, parent, false);
        return new item(rec);
    }

    @Override
    public void onBindViewHolder(item h, int position) {

        final Contant_sharj cu = cl.get(position);

        h.name.setText("صاحب خودرو : "+cu.getName()+" "+cu.getFamily());
        h.pay.setText("مبلغ شارژ : "+cu.getPay());
        h.plak.setText("پلاک : "+cu.getPlak());
        h.user.setText("پارکبان : "+cu.getUser());

        h.date.setText("تاریخ ثبت : "+cu.getData());
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class item extends RecyclerView.ViewHolder {

        TextView name, pay, plak, user, date;

        public item(View iv) {
            super(iv);
            date = (TextView) iv.findViewById(R.id.item_sharj_date);
            name = (TextView) iv.findViewById(R.id.item_sharj_name);
            pay = (TextView) iv.findViewById(R.id.item_sharj_pay);
            plak = (TextView) iv.findViewById(R.id.item_sharj_plak);
            user = (TextView) iv.findViewById(R.id.item_sharj_register);

        }

    }

}