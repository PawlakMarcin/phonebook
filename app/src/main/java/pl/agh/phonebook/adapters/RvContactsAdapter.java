package pl.agh.phonebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.agh.phonebook.R;
import pl.agh.phonebook.dao.ContactDAO;
import pl.agh.phonebook.dialogs.DialogCreateContact;
import pl.agh.phonebook.dialogs.DialogEditContact;
import pl.agh.phonebook.model.ModelContact;

public class RvContactsAdapter extends RecyclerView.Adapter<RvContactsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelContact> mListContacts;

    private RecyclerView mRecyclerView;
    private ContactDAO mContactDAO;
    private RecyclerViewOnClickListener mListener;

    public RvContactsAdapter(Context context, List<ModelContact> listContacts,
                             RecyclerView recyclerView, RecyclerViewOnClickListener listener){
        mContext = context;
        mListContacts = listContacts;
        mRecyclerView = recyclerView;
        mContactDAO = new ContactDAO(context);
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_contacts, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mListener.recyclerViewOnClick(mRecyclerView.getChildAdapterPosition(view), mListContacts);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mListener.recyclerViewOnLongClick(mRecyclerView.getChildAdapterPosition(view), mListContacts);
            }
        });

        return viewHolder;
    }

    public interface RecyclerViewOnClickListener{
        void recyclerViewOnClick(int position, List<ModelContact> listContact);
        boolean recyclerViewOnLongClick(int position, List<ModelContact> listContact);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView name, number, email;
        name = holder.name;
        email = holder.email;
        number = holder.number;


        name.setText(mListContacts.get(position).getName());
        email.setText("Email: " +mListContacts.get(position).getEmail());
        number.setText("Tel: "+mListContacts.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, number, email;
        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.contactName);
            number = itemView.findViewById(R.id.contactNumber);
            email = itemView.findViewById(R.id.contactEmail);
        }

    }
}
