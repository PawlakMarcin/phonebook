package pl.agh.phonebook.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import pl.agh.phonebook.R;

public class FragmentDialer extends Fragment {

    private View v;

    EditText input;
    Button bDel;
    Button bOne;
    Button bTwo;
    Button bThree;
    Button bFour;
    Button bFive;
    Button bSix;
    Button bSeven;
    Button bEight;
    Button bNine;
    Button bStar;
    Button bZero;
    Button bHash;
    Button bCall;


    public FragmentDialer(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_dialer, container, false);

        input = v.findViewById(R.id.numText);

        bDel = v.findViewById(R.id.bDel);
        bDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int length = input.getText().length();
                if(length>0){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(input.getText().toString());
                    stringBuilder.deleteCharAt(length-1);
                    input.setText(stringBuilder.toString());
                }
            }
        });
        bOne = v.findViewById(R.id.bOne);
        bOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bOne, input, "1");
            }
        });
        bTwo = v.findViewById(R.id.bTwo);
        bTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bTwo, input, "2");
            }
        });
        bThree = v.findViewById(R.id.bThree);
        bThree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bThree, input, "3");
            }
        });
        bFour = v.findViewById(R.id.bFour);
        bFour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bFour, input, "4");
            }
        });
        bFive = v.findViewById(R.id.bFive);
        bFive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bFive, input, "5");
            }
        });
        bSix = v.findViewById(R.id.bSix);
        bSix.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bSix, input, "6");
            }
        });
        bSeven = v.findViewById(R.id.bSeven);
        bSeven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bSeven, input, "7");
            }
        });
        bEight = v.findViewById(R.id.bEight);
        bEight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bEight, input, "8");
            }
        });
        bNine = v.findViewById(R.id.bNine);
        bNine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bNine, input, "9");
            }
        });
        bStar = v.findViewById(R.id.bStar);
        bStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bStar, input, "*");
            }
        });
        bZero = v.findViewById(R.id.bZero);
        bZero.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bZero, input, "0");
            }
        });
        bHash = v.findViewById(R.id.bHash);
        bHash.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onButtonClick(bHash, input, "#");
            }
        });
        bCall = v.findViewById(R.id.bCall);
        bCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(input.getText().length() < 3){
                    Toast.makeText(getActivity(),"Please enter the valid number", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    String num = input.getText().toString();
                    intent.setData(Uri.parse("tel:" + num));

                    if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                    } else{
                        startActivity(intent);
                    }
                }
            }
        });


        return v;
    }

//    public void bDel(View v){
//        int length = input.getText().length();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(input.getText().toString());
//        stringBuilder.deleteCharAt(length);
//        input.setText(stringBuilder.toString());
//    }
//    public void bOne(View v){
//        onButtonClick(bOne, input, "1");
//    }
//    public void bTwo(View v){
//        onButtonClick(bTwo, input, "2");
//    }
//    public void bThree(View v){
//        onButtonClick(bThree, input, "3");
//    }
//    public void bFour(View v){
//        onButtonClick(bFour, input, "4");
//    }
//    public void bFive(View v){
//        onButtonClick(bFive, input, "5");
//    }
//    public void bSix(View v){
//        onButtonClick(bSix, input, "6");
//    }
//    public void bSeven(View v){
//        onButtonClick(bSeven, input, "7");
//    }
//    public void bEight(View v){
//        onButtonClick(bEight, input, "8");
//    }
//    public void bNine(View v){
//        onButtonClick(bNine, input, "9");
//    }
//    public void bStar(View v){
//        onButtonClick(bStar, input, "*");
//    }
//    public void bZero(View v){
//        onButtonClick(bZero, input, "0");
//    }
//    public void bHash(View v){
//        onButtonClick(bHash, input, "#");
//    }
//    public void bCall(View v){
//        if(input.getText().length() < 3){
//            Toast.makeText(getActivity(),"Please enter the valid number", Toast.LENGTH_SHORT).show();
//        } else{
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            String num = input.getText().toString();
//            intent.setData(Uri.parse("tel:" + num));
//
//            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
//            } else{
//                startActivity(intent);
//            }
//        }
//    }

    public void onButtonClick(Button button, EditText inputNumber, String number){
        String cache = input.getText().toString();
        inputNumber.setText(cache + number);
    }
}
