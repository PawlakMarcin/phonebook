package pl.agh.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.agh.phonebook.dao.ContactDAO;
import pl.agh.phonebook.model.Contact;

public class MainActivity extends AppCompatActivity
        implements CreateContactDialog.DialogListener {

    private ContactDAO contactDAO;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.contacts);
        //optimization
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        contactDAO = new ContactDAO(this);
        reloadContactList();
    }

    public void addContact(View view){

        openDialog();
        reloadContactList();
    }

    public void openDialog(){
        CreateContactDialog createContactDialog = new CreateContactDialog();
        createContactDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void reloadContactList(){
        List allContacts = contactDAO.getAllContacts();
        recyclerView.setAdapter(new MyAdapter(allContacts, recyclerView));
    }

    @Override
    public void applyTexts(String contactName, String contactEmail, String contactPhoneNumber) {
        Contact contact = new Contact();
        contact.setName(contactName);
        contact.setEmail(contactEmail);
        contact.setPhoneNumber(contactPhoneNumber);

        contactDAO.insertContact(contact);
        reloadContactList();
    }
}