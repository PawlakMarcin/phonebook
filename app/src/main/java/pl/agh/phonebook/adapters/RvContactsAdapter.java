package pl.agh.phonebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.agh.phonebook.R;
import pl.agh.phonebook.model.ModelContact;

public class RvContactsAdapter extends RecyclerView.Adapter<RvContactsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelContact> mListContacts;

    public RvContactsAdapter(Context context, List<ModelContact> listContacts){
        mContext = context;
        mListContacts = listContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_contacts, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView name, number, id;
        name = holder.name;
        number = holder.number;
        id = holder.id;

        name.setText(mListContacts.get(position).getName());
        number.setText(mListContacts.get(position).getPhoneNumber());
        id.setText(Integer.toString(mListContacts.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, number, id;
        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.contactName);
            number = itemView.findViewById(R.id.contactNumber);
            id = itemView.findViewById(R.id.contactId);
        }

    }
}
