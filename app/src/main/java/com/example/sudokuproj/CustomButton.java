package com.example.sudokuproj;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class CustomButton extends FrameLayout {
    int row;
    int col;
    int value;
//    int bRow;
//    int bCol;

    TextView textView;
    TextView memoValue[][];
    boolean generatedCustomButton;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(Context context, int row, int col) {
        super(context);
        this.row=row;
        this.col=col;
        this.textView= new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        addView(textView);

        TableLayout tableLayout = new TableLayout(context);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f);

        memoValue=new TextView[3][3];

        int k=0;
        for (int i = 0; i < 3; i++) {
            TableRow tableRow = new TableRow(context);

            for (int j = 0; j < 3; j++) {
                memoValue[i][j] = new TextView(context);
                memoValue[i][j].setTextSize(10);
                memoValue[i][j].setTypeface(Typeface.DEFAULT_BOLD);
                memoValue[i][j].setLayoutParams(layoutParams);
                memoValue[i][j].setTextColor(Color.parseColor("#2F546C"));
                memoValue[i][j].setText(String.valueOf(++k));
                memoValue[i][j].setGravity(Gravity.CENTER);
                memoValue[i][j].setVisibility(INVISIBLE);
                memoValue[i][j].setBackgroundResource(R.drawable.button_selector);

                tableRow.addView(memoValue[i][j]);
            }
            tableLayout.setLayoutParams(layoutParams);
            tableLayout.addView(tableRow);
        }
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f);
        tableLayout.setLayoutParams(tableLayoutParams);
        addView(tableLayout);

        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);
    }


    public void setRandom(int a){
        if(a==0) {
            this.value = 0;
            textView.setText(null);
        }else {
            this.value=a;
            textView.setText(String.valueOf(a));
            textView.setTextColor(Color.parseColor("#000000"));
        }
    }

    public void set(int a){
        if(a==0) {
            this.value = 0;
            textView.setText(null);
        }else {
            this.value=a;
            textView.setText(String.valueOf(a));
            textView.setTextColor(Color.parseColor("#0080FF"));
        }
    }
    public int getValue() {
        return this.value;
    }
}