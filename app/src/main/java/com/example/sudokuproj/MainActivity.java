package com.example.sudokuproj;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    CustomButton clickedCustomButton=null;
    CustomButton[][] numButton;

    TableLayout numberPad;
    TableLayout dialogMemo;
    FrameLayout parentFrame;
    LinearLayout parentLinear;
    int valueMark;
    //Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BoardGenerator board = new BoardGenerator();

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT, 1);
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);

        parentFrame = (FrameLayout) findViewById(R.id.frame);
        parentLinear = (LinearLayout) findViewById(R.id.linear);
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        table.setPadding(3,0,3,0);

        numButton = new CustomButton[9][9];
        numberPad = (TableLayout) findViewById(R.id.numberpad);
        numberPad.setVisibility(View.INVISIBLE);

        //resetButton = (Button)findViewById(R.id.resetButton);

        dialogMemo = (TableLayout) findViewById(R.id.memo);
        dialogMemo.setVisibility(View.INVISIBLE);

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            layoutParams.setMargins(3,0,3,0);
            tableRow.setLayoutParams(tableLayoutParams);

            for (int j = 0; j < 9; j++) {
                numButton[i][j] = new CustomButton(this, i, j);

                numButton[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickedCustomButton = (CustomButton) view;
                        numberPad.setVisibility(View.VISIBLE);
                        dialogMemo.setVisibility(View.INVISIBLE);
                        valueMark = clickedCustomButton.value;
                        isClear();
                    }
                });
                numButton[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clickedCustomButton = (CustomButton) view;
                        setDialogMemo();
                        dialogMemo.setVisibility(View.VISIBLE);
                        numberPad.setVisibility(View.INVISIBLE);
                        return true;
                    }
                });

                int number = board.get(i, j);

                if (Math.random() <= 0.8) {
                    numButton[i][j].setB(number);
                    numButton[i][j].generatedCustomButton = true;
                }

                numButton[i][j].setLayoutParams(layoutParams);
                tableRow.addView(numButton[i][j]);

            }
            table.addView(tableRow);

        }

        Button resetButton = new Button(this);
        resetButton.setText("Reset");
        resetButton.setBackgroundColor(Color.parseColor("#BF9CCE"));
        resetButton.setTextColor(Color.parseColor("#FFFFFF"));
        resetButton.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view){
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
                }
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if(numButton[i][j].check == false){
                        numButton[i][j].set(0);
                    }
                    numButton[i][j].textView.setBackgroundResource(R.drawable.button_selector);
                }
            }
            TextView com = (TextView) findViewById(R.id.clear);
            com.setVisibility(View.INVISIBLE);
        }
        });
        table.addView(resetButton);

        LinearLayout.LayoutParams newB = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        newB.setMargins(0,8,0,14);
        resetButton.setLayoutParams(newB);

        /* 누르면 새로운 보드가 생성*/
        Button newButton = new Button(this);
        newButton.setText("New Game");
        newButton.setBackgroundColor(Color.parseColor("#7FAEEC"));
        newButton.setTextColor(Color.parseColor("#FFFFFF"));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //리셋과 동시에 메모했던 내용도 초기화
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        numButton[i][j].setB(0);
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int number = board.get(i, j);

                        if (Math.random() <= 0.8) {
                            numButton[i][j].setB(number);
                            numButton[i][j].generatedCustomButton = true;
                        }
                    }
                }

                TextView com = (TextView) findViewById(R.id.clear);
                com.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "RESTART", Toast.LENGTH_SHORT).show();
            }
        });
        table.addView(newButton);
    }


    public void onClickNum1(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(1);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum2(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(2);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum3(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(3);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum4(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(4);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum5(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(5);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum6(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(6);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum7(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(7);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum8(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(8);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickNum9(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        clickedCustomButton.set(9);
        numberPad.setVisibility(View.INVISIBLE);
        unsetConflict();
        setConflict();
        isClear();

    }
    public void onClickDelete(View view) {
        clickedCustomButton.set(0);
        unsetConflict();
        numberPad.setVisibility(View.INVISIBLE);
    }

    public void onClickCancel(View view) {
        numberPad.setVisibility(View.INVISIBLE);
        dialogMemo.setVisibility(View.INVISIBLE);
    }


    public void memoOnClickNum1(View view) {
        int visibility = clickedCustomButton.memoValue[0][0].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[0][0].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[0][0].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum2(View view) {
        int visibility = clickedCustomButton.memoValue[0][1].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[0][1].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[0][1].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum3(View view) {
        int visibility = clickedCustomButton.memoValue[0][2].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[0][2].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[0][2].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum4(View view) {
        int visibility = clickedCustomButton.memoValue[1][0].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[1][0].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[1][0].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum5(View view) {
        int visibility = clickedCustomButton.memoValue[1][1].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[1][1].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[1][1].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum6(View view) {
        int visibility = clickedCustomButton.memoValue[1][2].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[1][2].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[1][2].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum7(View view) {
        int visibility = clickedCustomButton.memoValue[2][0].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[2][0].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[2][0].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum8(View view) {
        int visibility = clickedCustomButton.memoValue[2][1].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[2][1].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[2][1].setVisibility(View.VISIBLE);
        }
    }
    public void memoOnClickNum9(View view) {
        int visibility = clickedCustomButton.memoValue[2][2].getVisibility();
        if (visibility == 0) {
            clickedCustomButton.memoValue[2][2].setVisibility(View.INVISIBLE);
        } else {
            clickedCustomButton.memoValue[2][2].setVisibility(View.VISIBLE);
        }
    }

    public void memoOnClickDelete(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                clickedCustomButton.memoValue[i][j].setVisibility(View.INVISIBLE);
            }
        }
        setDialogMemo();
    }

    public void memoOnClickCancel(View view) {
        dialogMemo.setVisibility(View.INVISIBLE);
    }
    public void memoOnClickOK(View view) {
        dialogMemo.setVisibility(View.INVISIBLE);
    }


    private void setDialogMemo() {
        for (int i = 0; i < 3; i++) {
            TableRow dialogMemoTableRow = (TableRow) dialogMemo.getChildAt(i+1);
            for (int j = 0; j < 3; j++) {
                ToggleButton toggleButton = (ToggleButton) dialogMemoTableRow.getChildAt(j);
                if (clickedCustomButton.memoValue[i][j].getVisibility() == View.VISIBLE) {
                    toggleButton.setChecked(true);
                } else {
                    toggleButton.setChecked(false);
                }
            }
        }
    }

    public void setConflict() {

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        int row = clickedCustomButton.row;
        int col = clickedCustomButton.col;
        int clickedValue = clickedCustomButton.value;
        int flag = 0;
        //행, 열 검사
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(row);
            CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(i);
            if (clickedValue == customButton.value) {
                flag++;
                customButton.setBackgroundResource(R.drawable.conflict);
                customButton.setAlpha(0.99f);
            }
        }

        //열
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(col);
            if (clickedValue == customButton.value) {
                flag++;
                customButton.setBackgroundResource(R.drawable.conflict);
                customButton.setAlpha(0.99f);

            }
        }

        //같은 박스 검사
        int boxRow = row / 3;
        int boxCol = col / 3;

        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(boxRow*3+i);
            for (int j = 0; j < 3; j++) {
                CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(boxCol*3+j);
                if (clickedValue == customButton.value) {
                    flag++;
                    customButton.setBackgroundResource(R.drawable.conflict);
                    customButton.setAlpha(0.99f);
                }
            }
        }
        if (flag == 3) {
            TableRow tableRow = (TableRow) table.getChildAt(row);
            CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(col);
            customButton.setBackgroundColor(Color.rgb(255, 255, 255));
        }

    }


    public void unsetConflict() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        int row = clickedCustomButton.row;
        int col = clickedCustomButton.col;
        //행
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(row);
            CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(i);
            if (valueMark==customButton.value){
                customButton.setBackgroundColor(Color.rgb(255,255,255));
                clickedCustomButton.setBackgroundColor(Color.rgb(255, 255, 255));
                customButton.setAlpha(1f);
            }
        }
//열
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(col);
            if (valueMark==customButton.value){
                customButton.setBackgroundColor(Color.rgb(255,255,255));
                clickedCustomButton.setBackgroundColor(Color.rgb(255, 255, 255));
                customButton.setAlpha(1f);
            }
        }
        //한 박스
        int boxRow = row / 3;
        int boxCol = col / 3;

        for (int i = 0; i < 3; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(boxRow*3+i);
            for (int j = 0; j < 3; j++) {
                CustomButton customButton = (CustomButton) tableRow.getVirtualChildAt(boxCol*3+j);
                if (valueMark==customButton.value){
                    customButton.setBackgroundColor(Color.rgb(255, 255, 255));
                    clickedCustomButton.setBackgroundColor(Color.rgb(255, 255, 255));
                    customButton.setAlpha(1f);
                }
            }

        }
    }

    public void isClear() {
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        int cnt=0;
        int cnt1 = 0;
        int cnt2 = 0;
        int cnt3 = 0;
        int cnt4 = 0;
        int cnt5 = 0;
        int cnt6 = 0;
        int cnt7 = 0;
        int cnt8 = 0;
        int cnt9 = 0;

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < 9; j++) {
                int value = numButton[i][j].getValue();
                if (value != 0)
                    cnt++;
                switch (value) {
                    case 1:
                        cnt1++;
                        break;
                    case 2:
                        cnt2++;
                        break;
                    case 3:
                        cnt3++;
                        break;
                    case 4:
                        cnt4++;
                        break;
                    case 5:
                        cnt5++;
                        break;
                    case 6:
                        cnt6++;
                        break;
                    case 7:
                        cnt7++;
                        break;
                    case 8:
                        cnt8++;
                        break;
                    case 9:
                        cnt9++;
                        break;
                }
            }
        }
        if (cnt == 81 && cnt1 == 9 && cnt2 == 9 && cnt3 == 9 && cnt4 == 9
                && cnt5 == 9 && cnt6 == 9 && cnt7 == 9 && cnt8 == 9 && cnt9 == 9) {
            TextView clear = (TextView) findViewById(R.id.clear);
            clear.setVisibility(View.VISIBLE);

        }
    }

}