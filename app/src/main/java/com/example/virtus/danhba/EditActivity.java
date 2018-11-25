package com.example.virtus.danhba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtus.danhba.Model.Contact;

public class EditActivity extends AppCompatActivity {

    Intent intent;
    int id;
    TextView tv_thongtin;
    EditText edt_username, edt_sodienthoai;
    RadioButton rad_gioitinhnam;
    RadioButton rad_gioitinhnu;
    Button btnCapNhat, btnXoa;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tv_thongtin = findViewById(R.id.tv_ten);
        edt_username = findViewById(R.id.edt_ud_name);
        edt_sodienthoai = findViewById(R.id.edt_ud_number);
        btnCapNhat = findViewById(R.id.btUpdateContact);
        btnXoa = findViewById(R.id.btnDelete);
        rad_gioitinhnam = findViewById(R.id.rad_ud_nam);
        rad_gioitinhnu = findViewById(R.id.rad_ud_nu);
        dbManager = new DBManager(this);
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Contact contact = dbManager.getContactById(id);
        tv_thongtin.setText("Cập nhật cho " + contact.getmName());
        edt_username.setText(contact.getmName());
        edt_sodienthoai.setText(contact.getmNumber());
        if (contact.isMale() == 0) rad_gioitinhnam.setChecked(true);
        else rad_gioitinhnu.setChecked(true);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact ct = new Contact();
                ct.setmName(edt_username.getText().toString());
                ct.setmNumber(edt_sodienthoai.getText().toString());
                ct.setMale(rad_gioitinhnam.isChecked() ? 0 : 1);
                ct.setId(id);
                dbManager.Update(ct);
                Toast.makeText(EditActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Xác nhận xoá");
                builder.setMessage("Bạn chắc chắn muốn xoá liên hệ này?");
                builder.setPositiveButton("Xác nhận",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Contact ct = new Contact();
                                ct.setId(id);
                                dbManager.deleteContact(ct);
                                Toast.makeText(EditActivity.this, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditActivity.this, MainActivity.class));
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
