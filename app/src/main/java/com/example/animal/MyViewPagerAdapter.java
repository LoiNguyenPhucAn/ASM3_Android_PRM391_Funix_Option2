package com.example.animal;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MyViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<AnimalType> listAnimal;

    public MyViewPagerAdapter(Context mContext, ArrayList<AnimalType> listAnimal) {
        this.mContext = mContext;
        this.listAnimal = listAnimal;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.text_content, container, false);
        AnimalType animalInfo = listAnimal.get(position);

        ImageView image = v.findViewById(R.id.iv_detail_image);
        TextView text = v.findViewById(R.id.tv_detail_content_text);
        TextView title = v.findViewById(R.id.detail_title);
        ImageView heart = v.findViewById(R.id.heart_button);

        image.setImageBitmap(animalInfo.getImagedetail());
        text.setText(animalInfo.getDetail());
        title.setText(animalInfo.getTitle());

        if (animalInfo.getTag().equals("filled")){
            heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button_filled));
        }else{
            heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button));
        }

        container.addView(v);

        /**
         * Lăng nghe sự kiện click trên icon heart
         * khi có sự kiện click trên icon heart thì:
         *
         * case1: kiểm tra trong SharedPreferences nếu chưa có key tương ứng với title thì tagPref sẽ có giá trị null
         * fill icon heart và gửi dư liệu "filled" với key là title của con vật lưu vào SharePreferences.
         *
         * case2: kiểm tra trong SharedPreferences nếu chứa key tương ứng title thì tagPref sẽ có giá trị là dữ liệu đã được lưu là "filled" hoặc "nonefillcolor"
         * Nếu dữ liệu nhận được là "nonfillcolor" thì fill icon heart, thay giá trị "nonefillcolor" bằng "filled" và lưu lại SharedPreferences.
         * Nếu dữ liệu nhận được là "filled" thì chuyển thành icon heart none fill, thay giá trị "filled" bằng "nonefillcolor" và lưu lại SharedPreferences.
         * */
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);

                String tagPref = pref.getString(title.getText().toString(), null);

                if (tagPref == null) {
                    Toast.makeText(mContext, "tagPref in SharedPreferences is null", Toast.LENGTH_SHORT).show();
                    heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button_filled));
                    tagPref = "filled";
                    pref.edit().putString(animalInfo.getTitle(), tagPref).commit();
                } else if (tagPref.contains("nonefillcolor")) {

                    heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button_filled));

                    String s = tagPref.replace("nonefillcolor", "filled");

                    //https://developer.android.com/training/data-storage/shared-preferences#WriteSharedPreference
                    /** To write to a shared preferences file, create a SharedPreferences.Editor by calling edit() on SharedPreferences Object
                     * apply() changes the in-memory SharedPreferences object immediately but writes the updates to disk asynchronously.
                     * Alternatively, you can use:
                     * commit() to write the data to disk synchronously. But because commit() is synchronous, you should avoid calling it from your main thread because it could pause your UI rendering.
                     * */
                    pref.edit().putString(animalInfo.getTitle(), s).commit();

                } else {

                    heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button));

                    String s = tagPref.replace("filled", "nonefillcolor");

                    pref.edit().putString(animalInfo.getTitle(), s).commit();
                }
            }
        });

        return v;
    }

    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
