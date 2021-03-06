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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

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
        ImageView phone = v.findViewById(R.id.phone_icon);
        TextView tvPhoneNumber = v.findViewById(R.id.phone_number);


        image.setImageBitmap(animalInfo.getImagedetail());
        text.setText(animalInfo.getDetail());
        title.setText(animalInfo.getTitle());

        if (animalInfo.getTag().equals("filled")) {
            heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button_filled));
        } else {
            heart.setImageDrawable(mContext.getDrawable(R.drawable.heart_button));
        }

        SharedPreferences phoneNumberSharedPref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_DIALOG_PHONE_NUMBER, Context.MODE_PRIVATE);
        String fillPhoneNumber = phoneNumberSharedPref.getString(animalInfo.getTitle(), null);

        if (animalInfo.getTitle() != null) {
            tvPhoneNumber.setText(fillPhoneNumber);
        }

        container.addView(v);

        /**
         * L??ng nghe s??? ki???n click tr??n icon heart
         * khi c?? s??? ki???n click tr??n icon heart th??:
         *
         * case1: ki???m tra trong SharedPreferences n???u ch??a c?? key t????ng ???ng v???i title th?? tagPref s??? c?? gi?? tr??? null
         * fill icon heart v?? g???i d?? li???u "filled" v???i key l?? title c???a con v???t l??u v??o SharePreferences.
         *
         * case2: ki???m tra trong SharedPreferences n???u ch???a key t????ng ???ng title th?? tagPref s??? c?? gi?? tr??? l?? d??? li???u ???? ???????c l??u l?? "filled" ho???c "nonefillcolor"
         * N???u d??? li???u nh???n ???????c l?? "nonfillcolor" th?? fill icon heart, thay gi?? tr??? "nonefillcolor" b???ng "filled" v?? l??u l???i SharedPreferences.
         * N???u d??? li???u nh???n ???????c l?? "filled" th?? chuy???n th??nh icon heart none fill, thay gi?? tr??? "filled" b???ng "nonefillcolor" v?? l??u l???i SharedPreferences.
         * */

        //X??? l?? click l??n icon heart th?? fill color, click again th?? none fill color
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_HEART_FLAG, Context.MODE_PRIVATE);
                String tagPref = pref.getString(title.getText().toString(), null);
                if (tagPref == null) {
                    //Toast.makeText(mContext, "tagPref in SharedPreferences is null", Toast.LENGTH_SHORT).show();
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


        // X??? l?? click icon phone hi???n th??? dialogue ????? nh???p v??o s??? ??i???n tho???i
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show dialog fragment from adapter
                FragmentActivity activity = (FragmentActivity) (mContext);
                FragmentManager fragManager = activity.getSupportFragmentManager();
                DialogFrag dialogObject = new DialogFrag(mContext, animalInfo, tvPhoneNumber);
                dialogObject.show(fragManager, "PhoneNumberDiaglogueFragment");
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
