package pl.agh.phonebook.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import pl.agh.phonebook.R;
import pl.agh.phonebook.adapters.RvContactsAdapter;
import pl.agh.phonebook.dao.ContactDAO;
import pl.agh.phonebook.dialogs.DialogCreateContact;
import pl.agh.phonebook.dialogs.DialogEditContact;
import pl.agh.phonebook.model.ModelContact;

public class FragmentContacts extends Fragment
        implements DialogCreateContact.DialogListener,
        DialogEditContact.DialogListener,
        RvContactsAdapter.RecyclerViewOnClickListener{

    private View v;
    private RecyclerView recyclerView;
    private ContactDAO contactDAO;

    public FragmentContacts(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = v.findViewById(R.id.contactsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        contactDAO = new ContactDAO(getContext());

        reloadAdapter();

        FloatingActionButton button = v.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCreateDialog();
            }
        });

        return v;
    }



    private void reloadAdapter(){
        List contacts = contactDAO.getAllContacts();
        recyclerView.setAdapter(new RvContactsAdapter(getContext(), contacts, recyclerView, this));
    }

    @Override
    public void recyclerViewOnClick(int position, List<ModelContact> listContact) {
         String number = listContact.get(position).getPhoneNumber().toString();

        if(number.length() < 3){
            Toast.makeText(getActivity(),"Entered number is incorrect", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));

            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
            } else{
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean recyclerViewOnLongClick(int position, List<ModelContact> listContact) {
        ModelContact contact = listContact.get(position);
        callEditDialog(contact);
        return true;
    }

    private void callCreateDialog(){
        DialogCreateContact dialogCreateContact = new DialogCreateContact();
        dialogCreateContact.setTargetFragment(this, 0);
        dialogCreateContact.show(getFragmentManager().beginTransaction(), "DialogCreateContact");
    }

    private void callEditDialog(ModelContact contact){
        DialogEditContact dialogEditContact = new DialogEditContact(contact);
        dialogEditContact.setTargetFragment(this, 0);
        dialogEditContact.show(getFragmentManager().beginTransaction(), "DialogEditContact");
    }

    @Override
    public void createContactSignal(String contactName, String contactEmail, String contactPhoneNumber) {
        ModelContact contact = new ModelContact();
        contact.setName(contactName);
        contact.setEmail(contactEmail);
        contact.setPhoneNumber(contactPhoneNumber);

        contactDAO.insertContact(contact);
        reloadAdapter();
    }

    @Override
    public void updateContactSignal(int contactId, String contactName, String contactEmail, String contactPhoneNumber) {
        ModelContact modelContact = new ModelContact();
        modelContact.setId(contactId);
        modelContact.setName(contactName);
        modelContact.setEmail(contactEmail);
        modelContact.setPhoneNumber(contactPhoneNumber);
        contactDAO.updateContact(modelContact);
        reloadAdapter();
    }

    @Override
    public void deleteContactSignal(int contactId) {
        contactDAO.deleteContactById(contactId);
        reloadAdapter();
    }
}
