package com.example.virtus.danhba;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.virtus.danhba.Adapter.ContactAdapter;
import com.example.virtus.danhba.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contact> arrayContact;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton radMale;
    private RadioButton radFemale;
    private Button btnAdd;
    private ListView lvContact;
    private ContactAdapter contactAdapter;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        radMale = (RadioButton) findViewById(R.id.rad_nam);
        radFemale = (RadioButton) findViewById(R.id.rad_nu);
        btnAdd = (Button) findViewById(R.id.btnAddContact);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        CapQuyen();
        dbManager = new DBManager(this);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                arrayContact.get(position);
                ShowDialogConfirm(position);
            }
        });


        arrayContact = new ArrayList<>();
        arrayContact = dbManager.getAllContact();
        contactAdapter = new ContactAdapter(this, R.layout.item_contact_listview, arrayContact);
        lvContact.setAdapter(contactAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddContact();
            }
        });
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "long click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Contact ct = arrayContact.get(position);

                intent.putExtra("id",ct.getId());
                startActivity(intent);
                return true;
            }
        });

    }

    private void AddContact() {
        String name = edtName.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        int isMale = 0;
        isMale = radMale.isChecked() ? 0 : 1;
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            Contact contact = new Contact(isMale, name, number);
            arrayContact.add(contact);
            dbManager.addContact(contact);
        }
        contactAdapter.notifyDataSetChanged();
    }

    public void ShowDialogConfirm(final int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        Button btnCall = (Button) dialog.findViewById(R.id.btnCall);
        Button btnSendMessage = (Button) dialog.findViewById(R.id.btnSendMessage);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMessage(position);
            }
        });
        dialog.show();
    }

    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void intentSendMessage(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void CapQuyen() {
        String[] quyen = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listQuyen = new ArrayList<>();
        for (String q : quyen) {
            if (ContextCompat.checkSelfPermission(this, q) != PackageManager.PERMISSION_GRANTED) {
                listQuyen.add(q);
            }
        }
        if (!listQuyen.isEmpty()) {
            ActivityCompat.requestPermissions(this, listQuyen.toArray(new String[listQuyen.size()]), 1);
        }
    }
}
