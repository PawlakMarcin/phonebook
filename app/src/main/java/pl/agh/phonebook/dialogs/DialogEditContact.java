package pl.agh.phonebook.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatDialogFragment;

import pl.agh.phonebook.R;
import pl.agh.phonebook.model.ModelContact;

public class DialogEditContact extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;

    private ModelContact mContact;

    public DialogEditContact(ModelContact modelContact){
        mContact = modelContact;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_contact_edit, null);

        builder.setView(view)
                .setTitle("Contact details")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DialogListener dl = (DialogListener)getTargetFragment();
                        String contactName = editTextName.getText().toString();
                        String contactEmail = editTextEmail.getText().toString();
                        String contactPhoneNumber = editTextPhoneNumber.getText().toString();
                        dl.updateContactSignal(mContact.getId(), contactName, contactEmail, contactPhoneNumber);
                    }
                });

        Button button = view.findViewById(R.id.deleteContact);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogListener dl = (DialogListener)getTargetFragment();
                dl.deleteContactSignal(mContact.getId());
                dismiss();
            }
        });
        editTextName = view.findViewById(R.id.updateName);
        editTextEmail = view.findViewById(R.id.updateEmail);
        editTextPhoneNumber = view.findViewById(R.id.updateNumber);
        editTextName.setText(mContact.getName());
        editTextEmail.setText(mContact.getEmail());
        editTextPhoneNumber.setText(mContact.getPhoneNumber());

        return builder.create();
    }

    public interface DialogListener{
        void updateContactSignal(int contactId, String contactName, String contactEmail, String contactPhoneNumber);
        void deleteContactSignal(int contactId);
    }
}
