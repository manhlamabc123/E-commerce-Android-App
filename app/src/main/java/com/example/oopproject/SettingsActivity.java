package com.example.oopproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;

import com.example.oopproject.classes_for_controll.DateController;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn,  closeTextBtn, saveTextButton;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker = "";

    private DateController dateController = new DateController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        storageProfilePictureRef = FirebaseStorage.getInstance().getReference()

        //-------------------------Connect to UI-------------------------
        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        fullNameEditText = (EditText) findViewById(R.id.settings_name);
        userPhoneEditText = (EditText) findViewById(R.id.settings_phone_number);
        dateButton = findViewById(R.id.datePickerButton);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update_account_settings_btn);
        //Gender Picker
        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //-----------------------------------------------------------------
        //-------------------------Initialize Date-------------------------
        initDatePicker();
        dateButton.setText(dateController.getTodayDate());
        //-----------------------------------------------------------------
        //-------------------------Close Button-------------------------
        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        //-----------------------------------------------------------------
        //-------------------------Save Button-------------------------
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
//                    userInfoSaved();
                }
                else
                {
//                    updateOnlyUserInfo();
                }
            }
        });
        //-----------------------------------------------------------------
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = dateController.makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}