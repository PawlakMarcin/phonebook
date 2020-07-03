package pl.agh.phonebook.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        ImageView type;

        name = holder.name;
        duration = holder.duration;
        date = holder.date;
        type = holder.type;

        String nameTmp = mListCalls.get(position).getName();
        if (nameTmp.length() > 0){
            name.setText(nameTmp);
        } else{
            name.setText(mListCalls.get(position).getNumber());
        }


        int durationTmp = Integer.parseInt((mListCalls.get(position).getDuration()));
        if (durationTmp < 60){
            duration.setText(mListCalls.get(position).getDuration() + "s ");
        } else {
            int minTmp = (durationTmp / 60);
            int secTmp = (durationTmp % 60);
            if (secTmp == 0){
                duration.setText(String.valueOf(minTmp) + "m ");
            } else{
                duration.setText(String.valueOf(minTmp) + "m " + String.valueOf(secTmp) + "s ");
            }
        }

        date.setText(mListCalls.get(position).getDate());

        int imageFlag = mListCalls.get(position).getImageFlag();

        if (imageFlag == CallLog.Calls.INCOMING_TYPE){
            type.setImageResource(android.R.drawable.sym_call_incoming);
        } else if (imageFlag == CallLog.Calls.OUTGOING_TYPE){
            type.setImageResource(android.R.drawable.sym_call_outgoing);
        } else if (imageFlag == CallLog.Calls.MISSED_TYPE){
            type.setImageResource(android.R.drawable.sym_call_missed);
        }
    }

    @Override
    public int getItemCount() {
        return mListCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, duration, date;
        ImageView type;

        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.callName);
            duration = itemView.findViewById(R.id.callDuration);
            date = itemView.findViewById(R.id.callDate);

            type = itemView.findViewById(R.id.imageFlag);
        }

    }
}
