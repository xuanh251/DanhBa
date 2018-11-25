package com.example.virtus.danhba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.virtus.danhba.Model.Contact;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class DBManager extends SQLiteOpenHelper {
    private Context context;

    public DBManager(Context context) {
        super(context, "danh_ba_dien_thoai", null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + "danh_ba" + " (" +
                "id" + " integer primary key, " +
                "name" + " TEXT, " +
                "sodienthoai" + " TEXT, " +
                "gioitinh" + " integer)";
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getmName());
        values.put("sodienthoai", contact.getmNumber());
        values.put("gioitinh", contact.isMale());

        db.insert("danh_ba", null, values);
        db.close();
    }

    public List<Contact> getAllContact() {
        List<Contact> listContact = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM danh_ba";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setmName(cursor.getString(1));
                contact.setmNumber(cursor.getString(2));
                contact.setMale(cursor.getInt(3));
                listContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listContact;
    }

    public Contact getContactById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("danh_ba", new String[]{"id",
                        "gioitinh", "name", "sodienthoai"}, "id" + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getInt(1), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return contact;
    }
    public int Update(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contact.getmName());
        values.put("sodienthoai", contact.getmNumber());
        values.put("gioitinh", contact.isMale());
        return db.update("danh_ba",values,"id" +"=?",new String[] { String.valueOf(contact.getId())});
    }
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("danh_ba", "id" + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
