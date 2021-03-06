package com.example.animal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogFrag extends DialogFragment {

    Context mContext;
    ImageView ivIcon;
    EditText phoneNumberTextField;
    private AnimalType animalInfo;
    private TextView tvPhoneNumber;

    public DialogFrag( Context mContext, AnimalType animalInfo, TextView tvPhoneNumber) {
        this.mContext = mContext;
        this.animalInfo = animalInfo;
        this.tvPhoneNumber = tvPhoneNumber;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialogue, null);

        ivIcon = viewDialog.findViewById(R.id.icon_dialogue);
        phoneNumberTextField = viewDialog.findViewById(R.id.input_phone_number_dialogue);

        SharedPreferences phoneNumberSharedPref = mContext.getSharedPreferences(MainActivity.SAVE_PREF_DIALOG_PHONE_NUMBER, Context.MODE_PRIVATE);

        ivIcon.setImageBitmap(animalInfo.getIcon());
        String titleIcon = animalInfo.getTitle();
        String fillPhoneNumber = phoneNumberSharedPref.getString(titleIcon,null);
        if  (fillPhoneNumber!=null){
            phoneNumberTextField.setText(fillPhoneNumber);
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(viewDialog)
                //Add SAVE button on dialog
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String iconBitmapPath = animalInfo.getIconBitmapPath();
                        String phoneNumber = phoneNumberTextField.getText().toString();
                        tvPhoneNumber.setText(phoneNumber);
                        //l??u phone number v??o sharedPreference v???i key l?? titleIcon, value l?? phone number
                        phoneNumberSharedPref.edit().putString(titleIcon, phoneNumber).commit();
                        //l??u phone number v??o sharedPreference v???i key l?? phonenumber, value l?? path ch??? ?????n v??? tr?? l??u h??nh ???nh bitmap icon
                        phoneNumberSharedPref.edit().putString(phoneNumber,iconBitmapPath).commit();

                    }
                })
                //Add DELETE button on dialog
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        tvPhoneNumber.setText("");
                        DialogFrag.this.getDialog().cancel();
                        //T??m titleIcon key trong SharedPreferences
                        // khi t???n t???i key th?? gi?? tr?? tr??? v??? l?? value do ???? bi???n keyPref != null
                        // th?? th???c hi???n xo?? d??? li???u v???i key t????ng ???ng trong SharedPreferences.
                        String keyPref = phoneNumberSharedPref.getString(titleIcon, null);
                        if (keyPref != null) {
                            phoneNumberSharedPref.edit().remove(keyPref).commit();
                            phoneNumberSharedPref.edit().remove(titleIcon).commit();

                        }
                    }
                });
        return builder.create();
    }
}
