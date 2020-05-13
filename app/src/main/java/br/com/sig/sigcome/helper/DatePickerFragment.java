package br.com.sig.sigcome.helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int Ano, Mes, Dia;
    private final EditText et_Data;

    public DatePickerFragment(EditText et_data) {
        et_Data = et_data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendario = Calendar.getInstance();
        Ano = calendario.get(Calendar.YEAR);
        Mes = calendario.get(Calendar.MONTH);
        Dia = calendario.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, Ano, Mes, Dia);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Ano = year;
        Mes = month;
        Dia = day;
        AtualizarData();
    }

    private void AtualizarData()
    {
        et_Data.setText(new StringBuilder()
                .append(FormatarDataHelper.padLeftZeros(String.valueOf(Dia), 2))
                .append("/")
                .append(FormatarDataHelper.padLeftZeros(String.valueOf(Mes + 1), 2))
                .append("/")
                .append(Ano).append(" "));
    }


}
