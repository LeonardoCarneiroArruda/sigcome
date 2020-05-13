package br.com.sig.sigcome.helper;

import android.app.Activity;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class TextInputLayoutHelper {

    public static EditText getEditText(Activity viewCriada, int idTextInputLayout) {
        TextInputLayout textInputLayout = viewCriada.findViewById(idTextInputLayout);
        return textInputLayout.getEditText();
    }

}
