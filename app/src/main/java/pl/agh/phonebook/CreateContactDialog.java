package pl.agh.phonebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class CreateContactDialog extends AppCompatDialogFragment {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;

    private DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_contact_layout, null);

        builder.setView(view)
                .setTitle("Create new contact")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String contactName = editTextName.getText().toString();
                        String contactEmail = editTextEmail.getText().toString();
                        String contactPhoneNumber = editTextPhoneNumber.getText().toString();
                        listener.applyTexts(contactName, contactEmail, contactPhoneNumber);
                    }
                });
        editTextName = view.findViewById(R.id.create_name);
        editTextEmail = view.findViewById(R.id.create_email);
        editTextPhoneNumber = view.findViewById(R.id.create_phone_number);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "DialogListener must be implemented!");
        }
    }

    public interface DialogListener{
        void applyTexts(String contactName, String contactEmail, String contactPhoneNumber);
    }



}
