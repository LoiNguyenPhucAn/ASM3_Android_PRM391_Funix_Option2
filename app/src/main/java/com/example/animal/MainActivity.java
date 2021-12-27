package com.example.animal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toolbar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String SAVE_PREF_HEART_FLAG = "heart_flag";
    public static final String SAVE_PREF_DIALOG_PHONE_NUMBER = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFrg1();


    }

    @Override
    public void onBackPressed() {
        showFrg1();
    }

    public void showFrg1() {
        FrgMH001 frg1 = new FrgMH001();
        frg1.setAnimalTypeArrayList(iconlist());
        showFrg(frg1);
    }

    public void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.alpha,R.anim.alpha).replace(R.id.ln_main, frg, null).commit();
    }

    /**
     * phương thức iconlist dùng để tạo ra danh sách animal gồm thông tin bitmap và titile của icon
     */
    public ArrayList<com.example.animal.AnimalType> iconlist() {

        int[] animalGroupID = {R.string.folder_icon_bird, R.string.folder_icon_mammal, R.string.folder_icon_sea};
        String[] assetFileName;
        ArrayList<com.example.animal.AnimalType> animalTypeArrayList = new ArrayList<>();

        try {
            for (int idStringResource : animalGroupID) {
                String assetFolderName = getString(idStringResource);
                //Duyệt qua tất cả các file trong assetFolderName và gán tất cả tên file vào string array
                assetFileName = getApplicationContext().getAssets().list(assetFolderName);

                for (String imageFileName : assetFileName) {
                    //tên file có cấu trúc là ic_[filename].png
                    //Vì vậy title sẽ lấy subString từ index 3 đến index của phần tử dấu .
                    String title = imageFileName.substring(3, imageFileName.indexOf("."));

                    //tạo path đến vị trí lưu file bitmap
                    String path = assetFolderName + "/" + imageFileName;

                    //input file bitmap icon
                    Bitmap bitmapIcon = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(path));

                    //input file bitmap detail image
                    String pathDetailImage = "detail/photo/" + title + ".jpg";
                    Bitmap detailimage = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(pathDetailImage));

                    //input detail content từ file .txt trong thư mục assets/detail/text/...
                    InputStream input = getApplicationContext().getAssets().open("detail/text/" + title + ".txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                    StringBuilder content = new StringBuilder();

                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    animalTypeArrayList.add(new AnimalType(title, bitmapIcon, null, content.toString(), detailimage));
                    br.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animalTypeArrayList;
    }


}