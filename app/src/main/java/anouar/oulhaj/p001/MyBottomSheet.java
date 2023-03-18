package anouar.oulhaj.p001;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheet extends BottomSheetDialogFragment {

    private SheetItemClickListener sheetListener;
    public static final String SHEET_TAG = "sheet";
    //----------- Instance-------------
    public static MyBottomSheet newInstance() {
        return new MyBottomSheet();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SheetItemClickListener){
            sheetListener = (SheetItemClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sheetListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.holder_sheet,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view_sheet, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view_sheet, savedInstanceState);

        view_sheet.findViewById(R.id.sheet_linear_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetListener.sheetItemClicked("Share clicked");
            }
        });
        view_sheet.findViewById(R.id.sheet_linear_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetListener.sheetItemClicked("Comment clicked");
            }
        });
        view_sheet.findViewById(R.id.sheet_linear_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetListener.sheetItemClicked("Phone clicked");
            }
        });
        view_sheet.findViewById(R.id.sheet_btn_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetListener.sheetBtnClick();
            }
        });
    }

    public interface SheetItemClickListener {
        void sheetBtnClick();
        void sheetItemClicked(String str);
    }
}
