package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    private static final String ARG_CITY = "city";

    public interface EditCityDialogListener {
        void onCityUpdated();
    }

    private EditCityDialogListener listener;
    private City city;

    // Factory method to create fragment with City argument
    public static EditCityFragment newInstance(City city) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);  // pass City object
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the new layout
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Get arguments (City to edit)
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CITY);
            if (city != null) {
                editCityName.setText(city.getName());
                editProvinceName.setText(city.getProvince());
            }
        }

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String newName = editCityName.getText().toString();
                    String newProvince = editProvinceName.getText().toString();

                    city.setName(newName);
                    city.setProvince(newProvince);

                    if (listener != null) {
                        listener.onCityUpdated();
                    }
                })
                .create();
    }
}
