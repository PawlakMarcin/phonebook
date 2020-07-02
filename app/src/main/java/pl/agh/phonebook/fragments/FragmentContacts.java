package pl.agh.phonebook.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import pl.agh.phonebook.model.ModelContact;

public class FragmentContacts extends Fragment implements DialogCreateContact.DialogListener{

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
                callTheDialog();
            }
        });

        return v;
    }

    private void callTheDialog(){
        DialogCreateContact dialogCreateContact = new DialogCreateContact();
        dialogCreateContact.setTargetFragment(this, 0);
        dialogCreateContact.show(getFragmentManager().beginTransaction(), "MyProgressDialog");
    }

    @Override
    public void applyTexts(String contactName, String contactEmail, String contactPhoneNumber) {
        Log.d("MiC:: ", "contactName: " +contactName+ ", contactEmail: " +contactEmail+ ", contactPhoneNumber: " +contactPhoneNumber);
        ModelContact contact = new ModelContact();
        contact.setName(contactName);
        contact.setEmail(contactEmail);
        contact.setPhoneNumber(contactPhoneNumber);

        contactDAO.insertContact(contact);
        reloadAdapter();
    }

    private void reloadAdapter(){
        List contacts = contactDAO.getAllContacts();
        recyclerView.setAdapter(new RvContactsAdapter(getContext(), contacts));
    }
}
