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
import pl.agh.phonebook.model.ModelCall;

public class RvCallsAdapter extends RecyclerView.Adapter<RvCallsAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<ModelCall> mListCalls;

    public RvCallsAdapter(Context context, List<ModelCall> listCalls){
        mContext = context;
        mListCalls = listCalls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_calls, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView name, duration, date;
        name = holder.name;
        duration = holder.duration;
        date = holder.date;

        name.setText(mListCalls.get(position).getNumber());
        duration.setText(mListCalls.get(position).getDuration());
        date.setText(mListCalls.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mListCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, duration, date;
        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.callName);
            duration = itemView.findViewById(R.id.callDuration);
            date = itemView.findViewById(R.id.callDate);
        }

    }
}
