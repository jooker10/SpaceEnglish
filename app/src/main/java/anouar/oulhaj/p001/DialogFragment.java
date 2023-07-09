package anouar.oulhaj.p001;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_MSG = "msg";
    private static final String ARG_ICON = "icon";
    private static final String ARG_ET_HINT_FR = "et_hint_fr";
    private static final String ARG_ET_HINT_ENG = "icon";


     private String title;
     private String msg;
     private int icon;
     private String hint_fr;
     private String hint_eng;

     private onDialogPositiveClickListener positiveListener;
     private onDialogNegativeClickListener negativeListener;
     private onDialogNeutralClickListener neutralListener;

    public DialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onDialogPositiveClickListener){
            positiveListener = (onDialogPositiveClickListener) context;}

        if(context instanceof onDialogNegativeClickListener) {
            negativeListener = (onDialogNegativeClickListener) context;
        }
        if(context instanceof onDialogNeutralClickListener){
            neutralListener = (onDialogNeutralClickListener) context;}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        positiveListener = null;
        negativeListener = null;
        neutralListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null) {
           title = bundle.getString(ARG_TITLE);
           msg = bundle.getString(ARG_MSG);
           icon = bundle.getInt(ARG_ICON);
           hint_fr = bundle.getString(ARG_ET_HINT_FR);
           hint_eng = bundle.getString(ARG_ET_HINT_ENG);
        }

    }

    public static DialogFragment newInstance(String title, String msg, int icon,String hint_fr,String hint_eng){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE,title);
        bundle.putString(ARG_MSG,msg);
        bundle.putInt(ARG_ICON,icon);
        bundle.putString(ARG_ET_HINT_FR,hint_fr);
        bundle.putString(ARG_ET_HINT_ENG,hint_eng);
        DialogFragment dialog_fragment = new DialogFragment();
        dialog_fragment.setArguments(bundle);
        return dialog_fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.custom_dialog_holder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //------inflate views---------
        TextView holder_title = view.findViewById(R.id.holder_title);
        TextView holder_msg = view.findViewById(R.id.holder_msg);
        EditText holderHint_Fr = view.findViewById(R.id.holder_verb_fr);
        EditText holderHint_Eng = view.findViewById(R.id.holder_verb_eng);
        ImageView holder_img = view.findViewById(R.id.holder_img);
        Button holder_btn_send = view.findViewById(R.id.btn_ok);
        //______set Txt and Events----------------
        holder_title.setText(title);
        holder_msg.setText(msg);
        holder_img.setImageResource(icon);
        holderHint_Fr.setText(hint_fr);
        holderHint_Eng.setText(hint_eng);
        holder_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positiveListener.onDialogPositiveClick(holderHint_Fr.getText().toString()
                                                    ,holderHint_Eng.getText().toString());
                dismiss();
            }
        });
    }

    //--------------Interfaces-------------------
    public interface onDialogPositiveClickListener {
        void onDialogPositiveClick(String fr, String eng);
    }
    public interface onDialogNegativeClickListener {
        void onDialogNegativeClick();
    }
    public interface onDialogNeutralClickListener {
        void onDialogNeutralClick();
    }
}