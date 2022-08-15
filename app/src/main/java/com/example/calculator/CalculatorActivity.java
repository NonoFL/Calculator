package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    String firstLine = "";
    String secondLint = "";
    private TextView tvFirstLine;
    private TextView tvSecondLine;
    private String DivideZeroError = "不能除以0";
    private String kuohaoInValidError = "表达式不合法";
    Calculate calculate = new Calculate();
    private int cntLeftRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        tvFirstLine = findViewById(R.id.tv_process);
        tvSecondLine = findViewById(R.id.tv_result);
        init();

        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String inputText = ((TextView) view).getText().toString();

        switch (view.getId()){
            case R.id.btn_cancel:
//                if(!"".equals(firstLine)){
                if(firstLine.length() > 1){
                    char ch = lastChar(firstLine);
                    if(ch == '('){
                        cntLeftRight--;
                    }else if(ch == ')'){
                        cntLeftRight++;
                    }
                    refreshFirstLine(firstLine.substring(0, firstLine.length() - 1));
                    if(isDigit(ch)){
                        if(Pattern.matches("[(]*", firstLine)){
                            refreshSecondLine("");
                        }else{
                            refreshWhenClickNum();
                        }
                    }

                }else{
                    init();
                }
                break;
            case R.id.btn_clear:
                init();
                break;
            case R.id.btn_plus:
                clickOperator(inputText);
                break;
            case R.id.btn_divide:
                clickOperator(inputText);
                break;
            case R.id.btn_minus:
                clickOperator(inputText);
                break;
            case R.id.btn_multiply:
                clickOperator(inputText);
                break;
            case R.id.btn_equal:
                refreshFirstLine(secondLint);
                refreshSecondLine("");
                break;
            case R.id.btn_left:
                if("".equals(firstLine) || (lastChar(firstLine) != ')') && isFuhao(lastChar(firstLine))){
                    refreshFirstLine(firstLine + inputText);
                    cntLeftRight++;
                }else{
                    Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_right:
                if(cntLeftRight == 0){
                    Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
                }else{
                    char ch = lastChar(firstLine);

                    if(ch != ')' && isFuhao(ch)){
                        Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
                    }else{
                        refreshFirstLine(firstLine + inputText);
                        cntLeftRight--;
                    }
                }
                break;
            case R.id.btn_dot:
                if("".equals(firstLine) || isFuhaoInFour(lastChar(firstLine)) || '(' == lastChar(firstLine)){
                    refreshFirstLine(firstLine + "0" + inputText);
                }else if(')' == lastChar(firstLine)){
                    Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
                }else{
                        refreshFirstLine(firstLine + inputText);
                    }
                break;
            default:
                int len = firstLine.length();
                boolean isCalculate = true;
                if("".equals(firstLine)){
                    refreshFirstLine(firstLine + inputText);
                }else if("0".equals(firstLine)){
                    refreshFirstLine(inputText);
                }else if(lastChar(firstLine) == ')'){
                    Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
                }else if(len > 2){
                    if(lastChar(firstLine) == '0' && isFuhao(firstLine.charAt(firstLine.length() - 2))){
                        refreshFirstLine(firstLine.substring(0, len - 1) + inputText);
                    }else if(lastChar(firstLine) == '/' && "0".equals(inputText)){
                        refreshFirstLine(firstLine + inputText);
                        Toast.makeText(CalculatorActivity.this, DivideZeroError, Toast.LENGTH_SHORT).show();
                        isCalculate = false;
                    }else{
                        refreshFirstLine(firstLine + inputText);
                    }
                }else{
                    refreshFirstLine(firstLine + inputText);
                }
                if(isCalculate){
                    refreshWhenClickNum();
                }
                break;
        }
    }



    private void refreshWhenClickNum() {
        // 增加关于括号的自动匹配问题。
        String str = validFirstLine();
        String res = calculate.splitTransCalcu(str);
        refreshSecondLine(res);
    }

    public String validFirstLine(){
        StringBuilder sb = new StringBuilder();
        int index = firstLine.length() - 1;
        if(firstLine.charAt(index) != ')'){
            while(index >= 0 && isFuhao(firstLine.substring(index, index + 1))){
                index--;
                if('(' == firstLine.charAt(index)){
                    cntLeftRight--;
                }else if(')' == firstLine.charAt(index)){
                    cntLeftRight++;
                }
            }
        }

        sb.append(firstLine.substring(0, index + 1));

        for(int i = 0; i < cntLeftRight; i++){
            sb.append(')');
        }
        return sb.toString();
    }


    public void clickOperator(String inputText){

        if("".equals(firstLine)){
            Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
        }else{
            char preClick = lastChar(firstLine);
            if('(' == preClick){
                Toast.makeText(CalculatorActivity.this, kuohaoInValidError, Toast.LENGTH_SHORT).show();
            }else if(')' == preClick || isDigit(preClick) || preClick == '.'){
                refreshFirstLine(firstLine + inputText);
            }else{
                refreshFirstLine(firstLine.substring(0, firstLine.length() - 1) + inputText);
            }
        }
    }

    public boolean isFuhao(String str){
        if("+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str) || "(".equals(str) || ")".equals(str)){
            return true;
        }
        return false;
    }
    public boolean isFuhao(char ch){
        if('+' == ch || '-' == ch || '*' == ch || '/' == ch || '(' == ch || ')' == ch){
            return true;
        }
        return false;
    }
    public boolean isFuhaoInFour(char ch){
        if('+' == ch || '-' == ch || '*' == ch || '/' == ch){
            return true;
        }
        return false;
    }
    public boolean isDigit(char ch){
        if('0' <= ch && ch <= '9'){
            return true;
        }else{
            return false;
        }
    }
    public char lastChar(String str){
        return str.charAt(str.length() - 1);
    }
    public boolean isFuhaoInTwo(char ch){
        if('(' == ch || ')' == ch){
            return true;
        }
        return false;
    }

    public void init(){
        clear();
        cntLeftRight = 0;
    }

    public void clear(){
        refreshFirstLine("");
        refreshSecondLine("");
    }
    public void refreshFirstLine(String str){
        firstLine = str;
        tvFirstLine.setText(firstLine);
    }
    public void refreshSecondLine(String str){
        secondLint = str;
        tvSecondLine.setText(secondLint);
    }
}