package pl.agh.phonebook.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.agh.phonebook.R;
import pl.agh.phonebook.adapters.RvCallsAdapter;
import pl.agh.phonebook.dao.ContactDAO;
import pl.agh.phonebook.model.ModelCall;

public class FragmentCalls extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private ContactDAO contactDao;

    public FragmentCalls(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_calls, container, false);
        recyclerView = v.findViewById(R.id.callsRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        contactDao = new ContactDAO(getContext());

        RvCallsAdapter adapter = new RvCallsAdapter(getContext(), getCallsLogs());
        recyclerView.setAdapter(adapter);

        return v;
    }

    private List<ModelCall> getCallsLogs(){

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG},1);
        }

        List<ModelCall> list = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");

        cursor.moveToFirst();

        do{
            if(contactDao.existsContactByNumber(cursor.getString(number))){

                Date dateExtracted = new Date(Long.valueOf(cursor.getString(date)));
                String dateParsed = new SimpleDateFormat("dd.MM").format(dateExtracted);

                String numberTmp = cursor.getString(number);
                list.add(new ModelCall(
                        numberTmp,
                        contactDao.getNameByNumber(numberTmp),
                        cursor.getString(duration),
                        dateParsed,
                        cursor.getInt(type))
                );
            }
        }while(cursor.moveToNext());

//        while(cursor.moveToNext()){
//
//            if(contactDao.existsContactByNumber(cursor.getString(number))){
//
//                Date dateExtracted = new Date(Long.valueOf(cursor.getString(date)));
//                String dateParsed = new SimpleDateFormat("dd.MM").format(dateExtracted);
//
//                String numberTmp = cursor.getString(number);
//                list.add(new ModelCall(
//                        numberTmp,
//                        contactDao.getNameByNumber(numberTmp),
//                        cursor.getString(duration),
//                        dateParsed,
//                        cursor.getInt(type))
//                );
//            }
//        }
        return list;
    }
}
