package pl.agh.phonebook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pl.agh.phonebook.database.DBHelper;
import pl.agh.phonebook.model.Contact;

public class ContactDAO {

    private DBHelper dbHelper;

    public ContactDAO(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertContact(final Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact_name", contact.getName());
        contentValues.put("contact_email", contact.getEmail());
        contentValues.put("contact_phone_number", contact.getPhoneNumber());

        dbHelper.getWritableDatabase().insert("contacts", null, contentValues);
    }

    public Contact getContactById(final int id){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "select * from contacts where _id = " + id, null);
        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            return mapCursorToContact(cursor);
        }
        return null;
    }

    public Contact mapCursorToContact(final Cursor cursor){
        int idColumnId = cursor.getColumnIndex("_id");
        int textNameColumnId = cursor.getColumnIndex("contact_name");
        int textEmailColumnId = cursor.getColumnIndex("contact_email");
        int textPhoneNumberColumnId = cursor.getColumnIndex("contact_phone_number");

        Contact contact = new Contact();
        contact.setId(cursor.getInt(idColumnId));
        contact.setName(cursor.getString(textNameColumnId));
        contact.setEmail(cursor.getString(textEmailColumnId));
        contact.setPhoneNumber(cursor.getString(textPhoneNumberColumnId));

        return contact;
    }

    public void updateContact(final Contact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact_name", contact.getName());
        contentValues.put("contact_email", contact.getEmail());
        contentValues.put("contact_phone_number", contact.getPhoneNumber());

        dbHelper.getWritableDatabase().update("contacts", contentValues,
                " _id = ? ", new String[]{String.valueOf(contact.getId())}
                );
    }

    public void deleteContactById(final int id){
        System.out.println("deleting: " + id);
        dbHelper.getWritableDatabase().delete("contacts",
                    " _id = ? ", new String[]{String.valueOf(id)}
                );
    }

    public List getAllContacts(){
        Cursor cursor = dbHelper.getReadableDatabase().query(
                "contacts",
                new String[]{"_id", "contact_name", "contact_email", "contact_phone_number"},
                null, null, null, null, null
        );

        List results = new ArrayList<>();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                results.add(mapCursorToContact(cursor));
            }
        }

        return results;
    }

}
