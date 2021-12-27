package com.example.animal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String SAVE_PREF_HEART_FLAG = "heart_flag";
    public static final String SAVE_PREF_DIALOG_PHONE_NUMBER = "phone_number";
    public static final int READ_PHONE_STATE_REQUEST_CODE = 101;
    public static final int READ_CALL_LOG_REQUEST_CODE = 110;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

    }


    protected void checkPermission() {

        if (this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG}, READ_PHONE_STATE_REQUEST_CODE);
        } else if (this.checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, READ_CALL_LOG_REQUEST_CODE);
        } else {
            showFrg1();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case READ_PHONE_STATE_REQUEST_CODE:

                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showFrg1();
                } else {
                    Toast.makeText(this, "Please allow this permission to use features of the app", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case READ_CALL_LOG_REQUEST_CODE:

                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFrg1();
                } else {
                    Toast.makeText(this, "Please allow this permission to use features of the app", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }

    //Called when the activity has detected the user's press of the back key
    @Override
    public void onBackPressed() {
        showFrg1();
    }

    // phương thức gọi hiển thị fragment FrgMH001
    public void showFrg1() {
        FrgMH001 frg1 = new FrgMH001();
        frg1.setAnimalTypeArrayList(iconlist());
        showFrg(frg1);
    }

    // phương thức gọi fragment với hiệu ứng alpha
    public void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.alpha, R.anim.alpha).replace(R.id.ln_main, frg, null).commit();
    }


    // phương thức iconlist dùng để tạo ra danh sách animal gồm thông tin bitmap và titile của icon
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