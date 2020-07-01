package pl.agh.phonebook;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.agh.phonebook.dao.ContactDAO;
import pl.agh.phonebook.model.Contact;

public class MyAdapter extends RecyclerView.Adapter {

    // data source
    private List<Contact> mContacts;

    // contact list object
    private RecyclerView mRecyclerView;

    private ContactDAO mContactDAO;

    private class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mContactId;
        public TextView mContactName;
        public TextView mContactPhoneNumber;

        public MyViewHolder(View pItem){
            super(pItem);
            mContactId = (TextView) pItem.findViewById(R.id.contact_id);
            mContactName = (TextView) pItem.findViewById(R.id.contact_name);
            mContactPhoneNumber = (TextView) pItem.findViewById(R.id.contact_phone_number);
        }
    }

    public MyAdapter(List<Contact> pContacts, RecyclerView pRecyclerView){
        mContacts = pContacts;
        mRecyclerView = pRecyclerView;
        mContactDAO = new ContactDAO(pRecyclerView.getContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i){
        // creating layout of one contact and ViewHolder obj
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_layout, viewGroup, false);


        //TODO - delete
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                int positionClicked = mRecyclerView.getChildAdapterPosition(v);
//                int idToDelete = mContacts.get(positionClicked).getId();
//
//                mContactDAO.deleteContactById(idToDelete);
//
//                mContacts.remove(positionClicked);
//                notifyItemRemoved(positionClicked);
//            }
//        });

        view.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                int positionClicked = mRecyclerView.getChildAdapterPosition(v);
                int idToDelete = mContacts.get(positionClicked).getId();

                mContactDAO.deleteContactById(idToDelete);

                mContacts.remove(positionClicked);
                notifyItemRemoved(positionClicked);

                return true;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  int positionClicked = mRecyclerView.getChildAdapterPosition(v);
                  String number = mContacts.get(positionClicked).getPhoneNumber();

                  Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                  v.getContext().startActivity(intent);
            }
        });


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i){
        // completing layout of the one contact
        Contact contact = mContacts.get(i);
        ((MyViewHolder) viewHolder).mContactId.setText(String.valueOf(contact.getId()));
        ((MyViewHolder) viewHolder).mContactName.setText(contact.getName());
        ((MyViewHolder) viewHolder).mContactPhoneNumber.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}
