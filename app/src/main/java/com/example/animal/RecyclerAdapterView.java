package com.example.animal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterView extends RecyclerView.Adapter<RecyclerAdapterView.MyHolder> {

    private Context mContext;
    private ArrayList<AnimalType> listRecycV;

    public RecyclerAdapterView(Context mContext, ArrayList<AnimalType> listRecycV) {
        this.mContext = mContext;
        this.listRecycV = listRecycV;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.icon_layout, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.imageIcon.setImageBitmap(listRecycV.get(position).getIcon());
        holder.titleIcon.setText(listRecycV.get(position).getTitle());
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_HEART_FLAG, Context.MODE_PRIVATE);
        String key = listRecycV.get(position).getTitle();
        String tagPref = pref.getString(key, null);

        /**
         * Kiểm tra dữ liệu trong SharedPreferences xem tag icon heart đã được fill hay chưa.
         * nếu giá trị nhận được là "filled" thì hiển thị icon heart
         * ngoài ra thì ẩn icon heart
         * */

        if (tagPref == null) {
            holder.heart.setVisibility(View.GONE);
            listRecycV.get(position).setTag("nonefillcolor");
        } else if (tagPref.contains("filled")) {
            /** setVisibility()
             * View.GONE This view is invisible, and it doesn't take any space for layout purposes.
             * View.INVISIBLE This view is invisible, but it still takes up space for layout purposes.
             * View.VISIBLE This view is visible.
             * https://developer.android.com/reference/android/view/View#attr_android:visibility
             * */
            holder.heart.setVisibility(View.VISIBLE);
            listRecycV.get(position).setTag("filled");
        } else {
            holder.heart.setVisibility(View.GONE);
            listRecycV.get(position).setTag("nonefillcolor");
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "item " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                // taọ hiệu ứng mờ trên card khi click
                holder.card.setAlpha((float) 0.5);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                FrgMH002 frg2 = new FrgMH002(holder.getAdapterPosition(), listRecycV);
                activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.alpha, R.anim.alpha).replace(R.id.ln_main, frg2, null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listRecycV.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView titleIcon;
        ImageView imageIcon, heart;
        CardView card;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleIcon = itemView.findViewById(R.id.tvcard);
            imageIcon = itemView.findViewById(R.id.ivcard);
            card = itemView.findViewById(R.id.icon);
            heart = itemView.findViewById(R.id.iv_heart_in_icon);
        }
    }
}
