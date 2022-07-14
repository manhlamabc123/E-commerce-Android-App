package com.example.oopproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oopproject.classes.Address;
import com.example.oopproject.classes.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword, InputDetailAddress;
    private ProgressDialog loadingBar;
    private String selectedProvince, selectedDistrict, selectedCommune;
    private Spinner provinceSpinner, districtSpinner, communeSpinner;
    private ArrayAdapter<CharSequence> provinceAdapter, districtAdapter, communeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //-----------------------Connect to UI----------------------------------------------
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputDetailAddress = (EditText) findViewById(R.id.register_detail_address_input);
        loadingBar = new ProgressDialog(this);
        provinceSpinner = (Spinner)findViewById(R.id.spinner_province);
        districtSpinner = (Spinner)findViewById(R.id.spinner_district);
        communeSpinner = (Spinner)findViewById(R.id.spinner_commune);
        //--------------------------------------------------------------------------------------------

        //-----------------------Province Spinner----------------------------------------------
        provinceAdapter = ArrayAdapter.createFromResource(this, R.array.VietNam_provinces, R.layout.spinner_layout);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtSpinner = findViewById(R.id.spinner_district);
                selectedProvince = provinceSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.spinner_province){
                    switch (selectedProvince){
                        case "Select Your Province": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.spinner_layout);
                            break;
                        case "Thanh pho Ha Noi": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Thanh_pho_Ha_Noi_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ha Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ha_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Cao Bang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Cao_Bang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Bac Kan": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Bac_Kan_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Tuyen Quang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Tuyen_Quang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Lao Cai": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Lao_Cai_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Dien Bien": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Dien_Bien_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Lai Chau": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Lai_Chau_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Son La": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Son_La_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Yen Bai": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Yen_Bai_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Hoa Binh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Hoa_Binh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Thai Nguyen": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Thai_Nguyen_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Lang Son": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Lang_Son_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Quang Ninh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Quang_Ninh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Bac Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Bac_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Phu Tho": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Phu_Tho_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Vinh Phuc": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Vinh_Phuc_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Bac Ninh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Bac_Ninh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Hai Duong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Hai_Duong_districts, R.layout.spinner_layout);
                            break;
                        case "Thanh pho Hai Phong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Thanh_pho_Hai_Phong_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Hung Yen": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Hung_Yen_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Thai Binh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Thai_Binh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ha Nam": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ha_Nam_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Nam Dinh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Nam_Dinh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ninh Binh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ninh_Binh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Thanh Hoa": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Thanh_Hoa_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Nghe An": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Nghe_An_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ha Tinh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ha_Tinh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Quang Binh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Quang_Binh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Quang Tri": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Quang_Tri_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Thua Thien Hue": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Thua_Thien_Hue_districts, R.layout.spinner_layout);
                            break;
                        case "Thanh pho Da Nang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Thanh_pho_Da_Nang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Quang Nam": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Quang_Nam_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Quang Ngai": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Quang_Ngai_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Binh Dinh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Binh_Dinh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Phu Yen": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Phu_Yen_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Khanh Hoa": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Khanh_Hoa_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ninh Thuan": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ninh_Thuan_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Binh Thuan": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Binh_Thuan_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Kon Tum": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Kon_Tum_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Gia Lai": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Gia_Lai_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Dak Lak": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Dak_Lak_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Dak Nong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Dak_Nong_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Lam Dong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Lam_Dong_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Binh Phuoc": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Binh_Phuoc_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Tay Ninh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Tay_Ninh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Binh Duong": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Binh_Duong_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Dong Nai": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Dong_Nai_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ba Ria - Vung Tau": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ba_Ria_Vung_Tau_districts, R.layout.spinner_layout);
                            break;
                        case "Thanh pho Ho Chi Minh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Thanh_pho_Ho_Chi_Minh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Long An": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Long_An_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Tien Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Tien_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ben Tre": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ben_Tre_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Tra Vinh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Tra_Vinh_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Vinh Long": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Vinh_Long_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Dong Thap": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Dong_Thap_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh An Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_An_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Kien Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Kien_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Thanh pho Can Tho": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Thanh_pho_Can_Tho_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Hau Giang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Hau_Giang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Soc Trang": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Soc_Trang_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Bac Lieu": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Bac_Lieu_districts, R.layout.spinner_layout);
                            break;
                        case "Tinh Ca Mau": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_Tinh_Ca_Mau_districts, R.layout.spinner_layout);
                            break;
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    districtSpinner.setAdapter(districtAdapter);

                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();

                            int parentID = parent.getId();
                            if (parentID == R.id.spinner_district) {
                                switch (selectedDistrict) {
                                    case "Select Your District":
                                        communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_default_commune, R.layout.spinner_layout);
                                        break;
                                    case "Quan Ba Dinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Ba_Dinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hoan Kiem": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Hoan_Kiem_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Tay Ho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Tay_Ho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Long Bien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Long_Bien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Cau Giay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Cau_Giay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Dong Da": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Dong_Da_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hai Ba Trung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Hai_Ba_Trung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hoang Mai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Hoang_Mai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Thanh Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Thanh_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Soc Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Soc_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Anh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Dong_Anh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Gia Lam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Gia_Lam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Nam Tu Liem": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Nam_Tu_Liem_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Tri":
                                        if (selectedProvince == "Thanh pho Ha Noi")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Thanh_pho_Ha_Noi_Huyen_Thanh_Tri_communes, R.layout.spinner_layout);
                                        else
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Soc_Trang_Huyen_Thanh_Tri_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Bac Tu Liem": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Bac_Tu_Liem_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Me Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Me_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Ha Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Quan_Ha_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Son Tay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Thi_xa_Son_Tay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba Vi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Ba_Vi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phuc Tho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Phuc_Tho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dan Phuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Dan_Phuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoai Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Hoai_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quoc Oai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Quoc_Oai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thach That": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Thach_That_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chuong My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Chuong_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Oai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Thanh_Oai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuong Tin": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Thuong_Tin_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Phu_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ung Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_Ung_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen My Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ha_Noi_Huyen_My_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ha Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Thanh_pho_Ha_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Van": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Dong_Van_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Meo Vac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Meo_Vac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Minh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Yen_Minh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quan Ba": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Quan_Ba_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vi Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Vi_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Me": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Bac_Me_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoang Su Phi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Hoang_Su_Phi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Xin Man": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Xin_Man_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Quang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Bac_Quang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Giang_Huyen_Quang_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Cao Bang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Thanh_pho_Cao_Bang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bao Lam":
                                        if (selectedProvince == "Tinh Cao Bang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Cao_Bang_Huyen_Bao_Lam_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Lam_Dong_Huyen_Bao_Lam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bao Lac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Bao_Lac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ha Quang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Ha_Quang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Trung Khanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Trung_Khanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ha Lang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Ha_Lang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Quang_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoa An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Hoa_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nguyen Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Nguyen_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thach An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Cao_Bang_Huyen_Thach_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh Pho Bac Kan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Thanh_Pho_Bac_Kan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Pac Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Pac_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba Be": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Ba_Be_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ngan Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Ngan_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bach Thong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Bach_Thong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cho Don": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Cho_Don_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cho Moi":
                                        if (selectedProvince == "Tinh Bac Kan")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Bac_Kan_Huyen_Cho_Moi_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_An_Giang_Huyen_Cho_Moi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Na Ri": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Kan_Huyen_Na_Ri_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tuyen Quang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Thanh_pho_Tuyen_Quang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lam Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Lam_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Na Hang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Na_Hang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chiem Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Chiem_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ham Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Ham_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Yen_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tuyen_Quang_Huyen_Son_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Lao Cai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Thanh_pho_Lao_Cai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bat Xat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Bat_Xat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Khuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Muong_Khuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Si Ma Cai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Si_Ma_Cai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Bac_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bao Thang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Bao_Thang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bao Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Bao_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Sa Pa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Thi_xa_Sa_Pa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Ban": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lao_Cai_Huyen_Van_Ban_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Dien Bien Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Thanh_pho_Dien_Bien_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi Xa Muong Lay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Thi_Xa_Muong_Lay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Nhe": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Muong_Nhe_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Cha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Muong_Cha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tua Chua": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Tua_Chua_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuan Giao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Tuan_Giao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dien Bien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Dien_Bien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dien Bien Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Dien_Bien_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Ang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Muong_Ang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Po": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dien_Bien_Huyen_Nam_Po_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Lai Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Thanh_pho_Lai_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tam Duong":
                                        if (selectedProvince == "Tinh Lai Chau")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Lai_Chau_Huyen_Tam_Duong_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Vinh_Phuc_Huyen_Tam_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Te": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Muong_Te_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Sin Ho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Sin_Ho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phong Tho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Phong_Tho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Than Uyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Than_Uyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Uyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Tan_Uyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Nhun": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lai_Chau_Huyen_Nam_Nhun_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Son La": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Thanh_pho_Son_La_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quynh Nhai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Quynh_Nhai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuan Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Thuan_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong La": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Muong_La_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Bac_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Phu_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Moc Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Moc_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Yen_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mai Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Mai_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Song Ma": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Song_Ma_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Sop Cop": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Sop_Cop_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Ho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Son_La_Huyen_Van_Ho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Yen Bai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Thanh_pho_Yen_Bai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Nghia Lo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Thi_xa_Nghia_Lo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Luc Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Luc_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Van_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mu Cang Chai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Mu_Cang_Chai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tran Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Tran_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tram Tau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Tram_Tau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Chan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Van_Chan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Yen_Bai_Huyen_Yen_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hoa Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Thanh_pho_Hoa_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Da Bac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Da_Bac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Luong Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Luong_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kim Boi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Kim_Boi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cao Phong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Cao_Phong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Lac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Tan_Lac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mai Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Mai_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lac Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Lac_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Yen_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lac Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hoa_Binh_Huyen_Lac_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thai Nguyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Thanh_pho_Thai_Nguyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Song Cong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Thanh_pho_Song_Cong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dinh Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Dinh_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Luong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Phu_Luong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Hy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Dong_Hy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vo Nhai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Vo_Nhai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dai Tu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Dai_Tu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Pho Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Thi_xa_Pho_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Nguyen_Huyen_Phu_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Lang Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Thanh_pho_Lang_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Trang Dinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Trang_Dinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Gia": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Binh_Gia_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Lang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Van_Lang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cao Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Cao_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Quan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Van_Quan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Bac_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Huu Lung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Huu_Lung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chi Lang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Chi_Lang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Loc Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Loc_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dinh Lap": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lang_Son_Huyen_Dinh_Lap_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ha Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thanh_pho_Ha_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Mong Cai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thanh_pho_Mong_Cai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Cam Pha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thanh_pho_Cam_Pha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Uong Bi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thanh_pho_Uong_Bi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Lieu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Binh_Lieu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Tien_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dam Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Dam_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hai Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Hai_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba Che": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Ba_Che_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Don": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Van_Don_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Dong Trieu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thi_xa_Dong_Trieu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Quang Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Thi_xa_Quang_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Co To": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ninh_Huyen_Co_To_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Bac Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Thanh_pho_Bac_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen The": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Yen_The_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Tan_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lang Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Lang_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Luc Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Luc_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Luc Ngan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Luc_Ngan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Son_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Dung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Yen_Dung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Viet Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Viet_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hiep Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Giang_Huyen_Hiep_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Viet Tri": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Thanh_pho_Viet_Tri_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Phu Tho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Thi_xa_Phu_Tho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Doan Hung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Doan_Hung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ha Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Ha_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Ba": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Thanh_Ba_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Ninh":
                                        if (selectedProvince == "Tinh Phu Tho")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Phu_Tho_Huyen_Phu_Ninh_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Quang_Nam_Huyen_Phu_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Lap": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Yen_Lap_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Khe": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Cam_Khe_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tam Nong":
                                        if (selectedProvince == "Tinh Phu Tho")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Phu_Tho_Huyen_Tam_Nong_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Dong_Thap_Huyen_Tam_Nong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lam Thao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Lam_Thao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Thanh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Thanh_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Tho_Huyen_Tan_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Vinh Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Thanh_pho_Vinh_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Phuc Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Thanh_pho_Phuc_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lap Thach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Lap_Thach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tam Dao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Tam_Dao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Binh_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Lac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Yen_Lac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Tuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Vinh_Tuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Song Lo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Phuc_Huyen_Song_Lo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Bac Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Thanh_pho_Bac_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Phong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Yen_Phong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Que Vo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Que_Vo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Du": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Tien_Du_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Tu Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Thi_xa_Tu_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuan Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Thuan_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Gia Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Gia_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Luong Tai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Ninh_Huyen_Luong_Tai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hai Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Thanh_pho_Hai_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Chi Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Thanh_pho_Chi_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Sach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Nam_Sach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Kinh Mon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Thi_xa_Kinh_Mon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kim Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Kim_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Thanh_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Cam_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Binh_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Gia Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Gia_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tu Ky": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Tu_Ky_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ninh Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Ninh_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Mien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hai_Duong_Huyen_Thanh_Mien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hong Bang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Hong_Bang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Ngo Quyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Ngo_Quyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Le Chan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Le_Chan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hai An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Hai_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Kien An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Kien_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Do Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Do_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Duong Kinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Quan_Duong_Kinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuy Nguyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Thuy_Nguyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_An_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Lao":
                                        if (selectedProvince == "Thanh pho Hai Phong")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Thanh_pho_Hai_Phong_Huyen_An_Lao_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Binh_Dinh_Huyen_An_Lao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kien Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Kien_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Lang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Tien_Lang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Bao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Vinh_Bao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cat Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Cat_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bach Long Vi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Hai_Phong_Huyen_Bach_Long_Vi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hung Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Thanh_pho_Hung_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Lam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Van_Lam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Van_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Yen_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa My Hao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Thi_xa_My_Hao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Thi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_An_Thi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Khoai Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Khoai_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kim Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Kim_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Lu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Tien_Lu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Cu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hung_Yen_Huyen_Phu_Cu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thai Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Thanh_pho_Thai_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quynh Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Quynh_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hung Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Hung_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Hung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Dong_Hung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thai Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Thai_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Tien_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kien Xuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Kien_Xuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vu Thu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thai_Binh_Huyen_Vu_Thu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Phu Ly": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Thanh_pho_Phu_Ly_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Duy Tien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Thi_xa_Duy_Tien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kim Bang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Huyen_Kim_Bang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Liem": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Huyen_Thanh_Liem_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Luc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Huyen_Binh_Luc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ly Nhan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Nam_Huyen_Ly_Nhan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Nam Dinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Thanh_pho_Nam_Dinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen My Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_My_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vu Ban": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Vu_Ban_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Y Yen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Y_Yen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nghia Hung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Nghia_Hung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Truc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Nam_Truc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Truc Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Truc_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Xuan Truong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Xuan_Truong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Giao Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Giao_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hai Hau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nam_Dinh_Huyen_Hai_Hau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ninh Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Thanh_pho_Ninh_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tam Diep": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Thanh_pho_Tam_Diep_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nho Quan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Nho_Quan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Gia Vien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Gia_Vien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoa Lu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Hoa_Lu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Khanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Yen_Khanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kim Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Kim_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Mo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Binh_Huyen_Yen_Mo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thanh Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Thanh_pho_Thanh_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Bim Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Thi_xa_Bim_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Sam Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Thanh_pho_Sam_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Muong Lat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Muong_Lat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quan Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Quan_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba Thuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Ba_Thuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quan Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Quan_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lang Chanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Lang_Chanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ngoc Lac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Ngoc_Lac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Cam_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thach Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Thach_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ha Trung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Ha_Trung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Vinh_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Dinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Yen_Dinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tho Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Tho_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuong Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Thuong_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Trieu Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Trieu_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thieu Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Thieu_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoang Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Hoang_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hau Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Hau_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nga Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Nga_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nhu Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Nhu_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nhu Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Nhu_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nong Cong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Nong_Cong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Dong_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Xuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Huyen_Quang_Xuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Nghi Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thanh_Hoa_Thi_xa_Nghi_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Vinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Thanh_pho_Vinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Cua Lo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Thi_xa_Cua_Lo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Thai Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Thi_xa_Thai_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Que Phong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Que_Phong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quy Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Quy_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ky Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Ky_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuong Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Tuong_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nghia Dan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Nghia_Dan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quy Hop": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Quy_Hop_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quynh Luu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Quynh_Luu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Con Cuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Con_Cuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Ky": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Tan_Ky_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Anh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Anh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dien Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Dien_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Yen Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Yen_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Do Luong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Do_Luong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Chuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Thanh_Chuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nghi Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Nghi_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Dan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Nam_Dan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hung Nguyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Huyen_Hung_Nguyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Hoang Mai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Nghe_An_Thi_xa_Hoang_Mai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ha Tinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Thanh_pho_Ha_Tinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Hong Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Thi_xa_Hong_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Huong Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Huong_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Tho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Duc_Tho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vu Quang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Vu_Quang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nghi Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Nghi_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Can Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Can_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Huong Khe": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Huong_Khe_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thach Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Thach_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Cam_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ky Anh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Ky_Anh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Loc Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Huyen_Loc_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Ky Anh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ha_Tinh_Thi_xa_Ky_Anh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh Pho Dong Hoi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Thanh_Pho_Dong_Hoi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Minh Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Minh_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuyen Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Tuyen_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Trach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Quang_Trach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bo Trach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Bo_Trach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Quang_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Le Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Huyen_Le_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Ba Don": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Binh_Thi_xa_Ba_Don_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Dong Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Thanh_pho_Dong_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Quang Tri": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Thi_xa_Quang_Tri_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Vinh_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Huong Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Huong_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Gio Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Gio_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Da Krong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Da_Krong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Lo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Cam_Lo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Trieu Phong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Trieu_Phong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hai Lang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Hai_Lang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Con Co": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Tri_Huyen_Con_Co_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hue": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Thanh_pho_Hue_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phong Dien":
                                        if (selectedProvince == "Tinh Thua Thien Hue")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Thua_Thien_Hue_Huyen_Phong_Dien_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Thanh_pho_Can_Tho_Huyen_Phong_Dien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Quang Dien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Huyen_Quang_Dien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Vang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Huyen_Phu_Vang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Huong Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Thi_xa_Huong_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Huong Tra": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Thi_xa_Huong_Tra_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen A Luoi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Huyen_A_Luoi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Huyen_Phu_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Thua_Thien_Hue_Huyen_Nam_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Lien Chieu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Lien_Chieu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Thanh Khe": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Thanh_Khe_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Hai Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Hai_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Son Tra": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Son_Tra_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Ngu Hanh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Ngu_Hanh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Cam Le": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Quan_Cam_Le_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoa Vang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Huyen_Hoa_Vang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoang Sa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Da_Nang_Huyen_Hoang_Sa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tam Ky": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Thanh_pho_Tam_Ky_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hoi An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Thanh_pho_Hoi_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tay Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Tay_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Dong_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dai Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Dai_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Dien Ban": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Thi_xa_Dien_Ban_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duy Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Duy_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Que Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Que_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Giang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Nam_Giang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phuoc Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Phuoc_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hiep Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Hiep_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thang Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Thang_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tien Phuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Tien_Phuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Tra My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Bac_Tra_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Tra My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Nam_Tra_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nui Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Nui_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nong Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Nam_Huyen_Nong_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Quang Ngai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Thanh_pho_Quang_Ngai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Binh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tra Bong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Tra_Bong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Tinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Son_Tinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tu Nghia": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Tu_Nghia_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Son_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Tay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Son_Tay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Minh Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Minh_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nghia Hanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Nghia_Hanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mo Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Mo_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Duc Pho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Thi_xa_Duc_Pho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba To": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Ba_To_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ly Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Quang_Ngai_Huyen_Ly_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Quy Nhon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Thanh_pho_Quy_Nhon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Hoai Nhon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Thi_xa_Hoai_Nhon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoai An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Hoai_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Phu_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Thanh":
                                        if (selectedProvince == "Tinh Binh Dinh")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Binh_Dinh_Huyen_Vinh_Thanh_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Thanh_pho_Can_Tho_Huyen_Vinh_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tay Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Tay_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Cat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Phu_Cat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa An Nhon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Thi_xa_An_Nhon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuy Phuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Tuy_Phuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Canh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Dinh_Huyen_Van_Canh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tuy Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Thanh_pho_Tuy_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Song Cau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Thi_xa_Song_Cau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Xuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Dong_Xuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuy An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Tuy_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Son Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Son_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Song Hinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Song_Hinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tay Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Tay_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Huyen_Phu_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Dong Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Phu_Yen_Thi_xa_Dong_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Nha Trang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Thanh_pho_Nha_Trang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Cam Ranh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Thanh_pho_Cam_Ranh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam Lam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Cam_Lam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Van Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Van_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Ninh Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Thi_xa_Ninh_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Khanh Vinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Khanh_Vinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dien Khanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Dien_Khanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Khanh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Khanh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Truong Sa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Khanh_Hoa_Huyen_Truong_Sa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Phan Rang-Thap Cham": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Thanh_pho_Phan_RangThap_Cham_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Ai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Bac_Ai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ninh Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Ninh_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ninh Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Ninh_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ninh Phuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Ninh_Phuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuan Bac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Thuan_Bac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thuan Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ninh_Thuan_Huyen_Thuan_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Phan Thiet": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Thanh_pho_Phan_Thiet_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa La Gi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Thi_xa_La_Gi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuy Phong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Tuy_Phong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Bac_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ham Thuan Bac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Ham_Thuan_Bac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ham Thuan Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Ham_Thuan_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tanh Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Tanh_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Duc_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ham Tan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Ham_Tan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Qui": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Thuan_Huyen_Phu_Qui_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Kon Tum": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Thanh_pho_Kon_Tum_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Glei": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Dak_Glei_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ngoc Hoi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Ngoc_Hoi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak To": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Dak_To_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kon Plong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Kon_Plong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kon Ray": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Kon_Ray_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Dak_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Sa Thay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Sa_Thay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tu Mo Rong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Tu_Mo_Rong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ia H' Drai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kon_Tum_Huyen_Ia_H_Drai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Pleiku": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Thanh_pho_Pleiku_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa An Khe": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Thi_xa_An_Khe_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Ayun Pa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Thi_xa_Ayun_Pa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen KBang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_KBang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Doa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Dak_Doa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chu Pah": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Chu_Pah_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ia Grai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Ia_Grai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mang Yang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Mang_Yang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kong Chro": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Kong_Chro_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Co": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Duc_Co_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chu Prong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Chu_Prong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chu Se": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Chu_Se_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Po": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Dak_Po_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ia Pa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Ia_Pa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong Pa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Krong_Pa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Thien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Phu_Thien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chu Puh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Gia_Lai_Huyen_Chu_Puh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Buon Ma Thuot": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Thanh_pho_Buon_Ma_Thuot_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi Xa Buon Ho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Thi_Xa_Buon_Ho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ea H'leo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Ea_Hleo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ea Sup": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Ea_Sup_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Buon Don": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Buon_Don_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cu M'gar": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Cu_Mgar_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong Buk": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Krong_Buk_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong Nang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Krong_Nang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ea Kar": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Ea_Kar_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen M'Drak": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_MDrak_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong Bong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Krong_Bong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong Pac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Krong_Pac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong A Na": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Krong_A_Na_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lak": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Lak_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cu Kuin": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Lak_Huyen_Cu_Kuin_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Gia Nghia": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Thanh_pho_Gia_Nghia_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Glong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Dak_Glong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cu Jut": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Cu_Jut_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Mil": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Dak_Mil_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Krong No": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Krong_No_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak Song": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Dak_Song_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dak R'Lap": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Dak_RLap_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tuy Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dak_Nong_Huyen_Tuy_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Da Lat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Thanh_pho_Da_Lat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Bao Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Thanh_pho_Bao_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dam Rong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Dam_Rong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lac Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Lac_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lam Ha": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Lam_Ha_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Don Duong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Don_Duong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Trong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Duc_Trong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Di Linh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Di_Linh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Da Huoai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Da_Huoai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Da Teh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Da_Teh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cat Tien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Lam_Dong_Huyen_Cat_Tien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Phuoc Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Thi_xa_Phuoc_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Dong Xoai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Thanh_pho_Dong_Xoai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Binh Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Thi_xa_Binh_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bu Gia Map": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Bu_Gia_Map_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Loc Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Loc_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bu Dop": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Bu_Dop_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hon Quan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Hon_Quan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Dong_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bu Dang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Bu_Dang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chon Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Chon_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Rieng": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Phuoc_Huyen_Phu_Rieng_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tay Ninh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Thanh_pho_Tay_Ninh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Bien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Huyen_Tan_Bien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Huyen_Tan_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duong Minh Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Huyen_Duong_Minh_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chau Thanh":
                                        if (selectedProvince == "Tinh Tay Ninh")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Tay_Ninh_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Long An")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Long_An_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Tien Giang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Tien_Giang_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Ben Tre")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Ben_Tre_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Tra Vinh")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Tra_Vinh_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Dong Thap")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Dong_Thap_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh An Giang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_An_Giang_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Kien Giang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Kien_Giang_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else if (selectedProvince == "Tinh Hau Giang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Hau_Giang_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_Soc_Trang_Huyen_Chau_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Hoa Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Thi_xa_Hoa_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Go Dau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Huyen_Go_Dau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ben Cau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Huyen_Ben_Cau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Trang Bang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tay_Ninh_Thi_xa_Trang_Bang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thu Dau Mot": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Thanh_pho_Thu_Dau_Mot_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bau Bang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Huyen_Bau_Bang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dau Tieng": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Huyen_Dau_Tieng_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Ben Cat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Thi_xa_Ben_Cat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Giao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Huyen_Phu_Giao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Tan Uyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Thi_xa_Tan_Uyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Di An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Thanh_pho_Di_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thuan An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Thanh_pho_Thuan_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Bac Tan Uyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Binh_Duong_Huyen_Bac_Tan_Uyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Bien Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Thanh_pho_Bien_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Long Khanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Thanh_pho_Long_Khanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Tan_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Cuu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Vinh_Cuu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dinh Quan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Dinh_Quan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Trang Bom": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Trang_Bom_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thong Nhat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Thong_Nhat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cam My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Cam_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Long Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Long_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Xuan Loc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Xuan_Loc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nhon Trach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Nai_Huyen_Nhon_Trach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Vung Tau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Thanh_pho_Vung_Tau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ba Ria": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Thanh_pho_Ba_Ria_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chau Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Huyen_Chau_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Xuyen Moc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Huyen_Xuyen_Moc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Long Dien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Huyen_Long_Dien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dat Do": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Huyen_Dat_Do_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Phu My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Thi_xa_Phu_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Con Dao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ba_Ria_Vung_Tau_Huyen_Con_Dao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 1": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_1_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 12": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_12_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Go Vap": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Go_Vap_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Binh Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Binh_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Tan Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Tan_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Tan Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Tan_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Phu Nhuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Phu_Nhuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Thu Duc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Thanh_pho_Thu_Duc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 3": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_3_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 10": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_10_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 11": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_11_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 4": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_4_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 5": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_5_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 6": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_6_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 8": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_8_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Binh Tan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_Binh_Tan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan 7": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Quan_7_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cu Chi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Huyen_Cu_Chi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoc Mon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Huyen_Hoc_Mon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Chanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Huyen_Binh_Chanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nha Be": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Huyen_Nha_Be_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Can Gio": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Ho_Chi_Minh_Huyen_Can_Gio_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tan An": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Thanh_pho_Tan_An_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Kien Tuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Thi_xa_Kien_Tuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Hung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Tan_Hung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Hung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Vinh_Hung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Moc Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Moc_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Tan_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Thanh_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Hue": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Duc_Hue_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duc Hoa": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Duc_Hoa_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ben Luc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Ben_Luc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thu Thua": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Thu_Thua_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Tru": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Tan_Tru_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Can Duoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Can_Duoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Can Giuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Long_An_Huyen_Can_Giuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho My Tho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Thanh_pho_My_Tho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Go Cong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Thi_xa_Go_Cong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Cai Lay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Thi_xa_Cai_Lay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Phuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Tan_Phuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cai Be": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Cai_Be_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cai Lay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Cai_Lay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cho Gao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Cho_Gao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Go Cong Tay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Go_Cong_Tay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Go Cong Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Go_Cong_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Phu Dong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tien_Giang_Huyen_Tan_Phu_Dong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ben Tre": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Thanh_pho_Ben_Tre_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cho Lach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Cho_Lach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mo Cay Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Mo_Cay_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Giong Trom": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Giong_Trom_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Dai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Binh_Dai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ba Tri": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Ba_Tri_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Thanh_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mo Cay Bac": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ben_Tre_Huyen_Mo_Cay_Bac_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Tra Vinh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Thanh_pho_Tra_Vinh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cang Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Cang_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cau Ke": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Cau_Ke_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tieu Can": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Tieu_Can_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cau Ngang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Cau_Ngang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tra Cu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Tra_Cu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Duyen Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Huyen_Duyen_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Duyen Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Tra_Vinh_Thi_xa_Duyen_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Vinh Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Thanh_pho_Vinh_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Long Ho": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen_Long_Ho_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Mang Thit": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen_Mang_Thit_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen  Vung Liem": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen__Vung_Liem_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tam Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen_Tam_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Binh Minh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Thi_xa_Binh_Minh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tra On": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen_Tra_On_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Binh Tan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Vinh_Long_Huyen_Binh_Tan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Cao Lanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Thanh_pho_Cao_Lanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Sa Dec": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Thanh_pho_Sa_Dec_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Hong Ngu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Thanh_pho_Hong_Ngu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Hong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Tan_Hong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hong Ngu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Hong_Ngu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thap Muoi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Thap_Muoi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cao Lanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Cao_Lanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thanh Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Thanh_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lap Vo": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Lap_Vo_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Lai Vung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Dong_Thap_Huyen_Lai_Vung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Long Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Thanh_pho_Long_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Chau Doc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Thanh_pho_Chau_Doc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Huyen_An_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Tan Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Thi_xa_Tan_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phu Tan":
                                        if (selectedProvince == "Tinh An Giang")
                                            communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                    R.array.array_Tinh_An_Giang_Huyen_Phu_Tan_communes, R.layout.spinner_layout);
                                        else communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                                R.array.array_Tinh_Ca_Mau_Huyen_Phu_Tan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chau Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Huyen_Chau_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tinh Bien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Huyen_Tinh_Bien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tri Ton": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Huyen_Tri_Ton_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thoai Son": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_An_Giang_Huyen_Thoai_Son_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Rach Gia": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Thanh_pho_Rach_Gia_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ha Tien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Thanh_pho_Ha_Tien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kien Luong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Kien_Luong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hon Dat": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Hon_Dat_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tan Hiep": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Tan_Hiep_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Giong Rieng": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Giong_Rieng_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Go Quao": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Go_Quao_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Bien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_An_Bien_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen An Minh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_An_Minh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Thuan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Vinh_Thuan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Phu Quoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Thanh_pho_Phu_Quoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Kien Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Kien_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen U Minh Thuong": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_U_Minh_Thuong_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Giang Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Kien_Giang_Huyen_Giang_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Ninh Kieu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Quan_Ninh_Kieu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan O Mon": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Quan_O_Mon_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Binh Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Quan_Binh_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Cai Rang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Quan_Cai_Rang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Quan Thot Not": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Quan_Thot_Not_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Co Do": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Huyen_Co_Do_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thoi Lai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Thanh_pho_Can_Tho_Huyen_Thoi_Lai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Vi Thanh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Thanh_pho_Vi_Thanh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Nga Bay": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Thanh_pho_Nga_Bay_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Chau Thanh A": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Huyen_Chau_Thanh_A_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phung Hiep": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Huyen_Phung_Hiep_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vi Thuy": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Huyen_Vi_Thuy_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Long My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Huyen_Long_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Long My": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Hau_Giang_Thi_xa_Long_My_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Soc Trang": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Thanh_pho_Soc_Trang_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ke Sach": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_Ke_Sach_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen My Tu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_My_Tu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cu Lao Dung": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_Cu_Lao_Dung_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Long Phu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_Long_Phu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen My Xuyen": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_My_Xuyen_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Nga Nam": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Thi_xa_Nga_Nam_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Vinh Chau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Thi_xa_Vinh_Chau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tran De": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Soc_Trang_Huyen_Tran_De_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Bac Lieu": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Thanh_pho_Bac_Lieu_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hong Dan": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Huyen_Hong_Dan_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Phuoc Long": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Huyen_Phuoc_Long_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Vinh Loi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Huyen_Vinh_Loi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thi xa Gia Rai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Thi_xa_Gia_Rai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dong Hai": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Huyen_Dong_Hai_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Hoa Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Bac_Lieu_Huyen_Hoa_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Thanh pho Ca Mau": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Thanh_pho_Ca_Mau_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen U Minh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_U_Minh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Thoi Binh": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Thoi_Binh_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Tran Van Thoi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Tran_Van_Thoi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Cai Nuoc": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Cai_Nuoc_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Dam Doi": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Dam_Doi_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Nam Can": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Nam_Can_communes, R.layout.spinner_layout);
                                        break;
                                    case "Huyen Ngoc Hien": communeAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                            R.array.array_Tinh_Ca_Mau_Huyen_Ngoc_Hien_communes, R.layout.spinner_layout);
                                        break;
                                    default:
                                        break;
                                }
                                communeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                                communeSpinner.setAdapter(communeAdapter);        //Populate the list of Districts in respect of the State selected

                                //To obtain the selected District from the spinner
                                communeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedCommune = communeSpinner.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        selectedCommune = "";
                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            selectedDistrict = "";
                        }
                    });
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedProvince = "";
            }
        });
        //

        //-----------------------Create Button----------------------------------------------
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
        //--------------------------------------------------------------------------------------------
    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phoneNumber = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String detailAddress = InputDetailAddress.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumber))
        {
            Toast.makeText(this, "Please write your phone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedProvince.isEmpty())
        {
            Toast.makeText(this, "Please select province...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedDistrict.isEmpty())
        {
            Toast.makeText(this, "Please select district...", Toast.LENGTH_SHORT).show();
        }
        else if (selectedCommune.isEmpty())
        {
            Toast.makeText(this, "Please select commune...", Toast.LENGTH_SHORT).show();
        }
        else if (detailAddress.isEmpty())
        {
            Toast.makeText(this, "Please write your address...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, we are checking credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
        }

        ValidatePhoneNumber(name, phoneNumber, password, selectedProvince, selectedDistrict, selectedCommune, detailAddress);
    }

    private void ValidatePhoneNumber(String name, String phoneNumber, String password, String province, String district, String commune, String detailAddress) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Customer").child(phoneNumber).exists())) {
                    Address address = new Address(detailAddress, province, district, commune);
                    Customer newCustomer = new Customer(phoneNumber, name, password, address);

                    RootRef.child("Customer").child(phoneNumber).updateChildren(newCustomer.toMap())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Congratulation, your account is ready now.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
                else {
                    Toast.makeText(RegisterActivity.this, "This " + phoneNumber + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}