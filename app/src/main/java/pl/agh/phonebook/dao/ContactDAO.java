package pl.agh.phonebook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.agh.phonebook.database.DbHelper;
import pl.agh.phonebook.model.ModelContact;

public class ContactDAO {

    private DbHelper dbHelper;

    public ContactDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public void insertContact(final ModelContact contact){
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact_name", contact.getName());
        contentValues.put("contact_email", contact.getEmail());
        contentValues.put("contact_phone_number", contact.getPhoneNumber());

        dbHelper.getWritableDatabase().insert("contacts", null, contentValues);
    }

    public ModelContact getContactById(final int id){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "select * from contacts where _id = " + id, null);
        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            return mapCursorToContact(cursor);
        }
        return null;
    }

    public String getNameByNumber(String number){

        String numberTmp = number;
        Log.d("numberTmpBEFORE:", numberTmp);
        if (number.contains("+48")) {
            numberTmp = number.substring(3);
            Log.d("numberTmpAFTER_IF:", numberTmp);
        }
        Log.d("numberTmpAFTER_WITHOUT_IF:", numberTmp);

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "select * from contacts where contact_phone_number = '"+numberTmp +"'", null);
        int index = cursor.getColumnIndex("contact_name");

        Log.d("cursor.getCount(): ", String.valueOf(cursor.getCount()));

        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            return mapCursorToContact(cursor).getName();
        }
        return "";
    }

    public ModelContact mapCursorToContact(final Cursor cursor){
        int idColumnId = cursor.getColumnIndex("_id");
        int textNameColumnId = cursor.getColumnIndex("contact_name");
        int textEmailColumnId = cursor.getColumnIndex("contact_email");
        int textPhoneNumberColumnId = cursor.getColumnIndex("contact_phone_number");

        ModelContact contact = new ModelContact();
        contact.setId(cursor.getInt(idColumnId));
        contact.setName(cursor.getString(textNameColumnId));
        contact.setEmail(cursor.getString(textEmailColumnId));
        contact.setPhoneNumber(cursor.getString(textPhoneNumberColumnId));

        return contact;
    }

    public void updateContact(final ModelContact contact){
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

    public boolean existsContactByNumber(String number){

        String numberTmp = number;
        if (number.contains("+48")){
            numberTmp = number.substring(3);
        }
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "select * from contacts where contact_phone_number = '"+numberTmp+"'",
                null
        );
        if(cursor.getCount() > 0){
            return true;
        } else return false;
    }

}
