package aspi.parkbansabzevar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AmIn on 7/26/2018.
 */

public class Recycler_car_all extends RecyclerView.Adapter<Recycler_car_all.item> {

    List<ContantAllCar> cl;
    Context context;
    OtherMetod om = new OtherMetod();


    public Recycler_car_all(List<ContantAllCar> CL, Context context) {
        this.cl = CL;
        this.context = context;
    }

    @Override
    public item onCreateViewHolder(ViewGroup parent, int viewType) {
        View rec = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_all, parent, false);
        return new item(rec);
    }

    @Override
    public void onBindViewHolder(item h, int position) {

        final ContantAllCar cu = cl.get(position);

        h.name.setText("صاحب خودرو : "+cu.getName()+" "+cu.getFamily());
        h.phone.setText("شماره تماس : "+cu.getPhoe());
        h.plak.setText("پلاک : "+cu.getNumber_car());
        h.user.setText("پارکبان : "+cu.getInsert());
        h.date.setText("تاریخ : "+cu.getDate());

        h.money.setText("مبلغ : "+cu.getMoney());
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class item extends RecyclerView.ViewHolder {

        TextView name, phone, plak, user, date, money;

        public item(View iv) {
            super(iv);

            money = (TextView) iv.findViewById(R.id.item_car_all_money);
            date = (TextView) iv.findViewById(R.id.item_car_all_date);
            name = (TextView) iv.findViewById(R.id.item_car_all_name);
            phone = (TextView) iv.findViewById(R.id.item_car_all_phone);
            plak = (TextView) iv.findViewById(R.id.item_car_all_plak);
            user = (TextView) iv.findViewById(R.id.item_car_all_register);

        }

    }

}