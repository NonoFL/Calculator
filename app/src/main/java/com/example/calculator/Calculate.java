package com.example.calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Calculate {

    public HashMap<String, Integer> site;

    public Calculate() {
        init();
    }

    private void init() {
        // 优先级越高，数值越大。
        // （：0
        // +，-：1
        // *， / ：2
        site = new HashMap<>();
        site.put("*", 2);
        site.put("/", 2);
        site.put("+", 1);
        site.put("-", 1);
        site.put("(", 0);
        site.put(")", 0);
    }
    public String splitTransCalcu(String str){
        List<String> spilt = spilt(str);
        return calculate(trans(spilt));
    }

    public  List<String> spilt(String str){
        List<String> afterSpilt = new ArrayList<>();

        for(int i = 0; i < str.length(); i++) {
            if (isFuhao(String.valueOf(str.charAt(i)))) {
                afterSpilt.add(str.substring(i, i + 1));
            } else {
                int endIndex = i + 1;
                while (endIndex < str.length() && !isFuhao(String.valueOf(str.charAt(endIndex)))) {
                    endIndex++;
                }
                afterSpilt.add(str.substring(i, endIndex));
                i = endIndex - 1;
            }
        }
        return afterSpilt;

    }


    public List<String> trans(List<String> afterSpilt){
        List<String> afterTrans = new ArrayList<>();
        Stack<String> st = new Stack<>();

        for(int i = 0; i < afterSpilt.size(); i++){
            String tem = afterSpilt.get(i);

            switch (tem){
                case "(":
                    st.push(tem);
                    break;
                case ")":
                    while(!"(".equals(st.peek())){
                        afterTrans.add(st.pop());
                    }
                    st.pop();
                    break;
                case "+":

                    while(!st.empty() && site.get(tem) <= site.get(st.peek())){
                        afterTrans.add(st.pop());
                    }

                    st.push(tem);
                    break;
                case "-":
                    while(!st.empty() && site.get(tem) <= site.get(st.peek())){
                        afterTrans.add(st.pop());
                    }
                    st.push(tem);
                    break;
                case "*":
                    while(!st.empty() && site.get(tem) <= site.get(st.peek())){
                        afterTrans.add(st.pop());
                    }
                    st.push(tem);
                    break;
                case "/":
                    while(!st.empty() && site.get(tem) <= site.get(st.peek())){
                        afterTrans.add(st.pop());
                    }
                    st.push(tem);
                    break;
                default:
                    afterTrans.add(tem);
                    break;
            }
        }
        while(!st.isEmpty()){
            afterTrans.add(st.pop());
        }
        return afterTrans;
    }

    public boolean isFuhao(String str){
        if(site.containsKey(str)){
            return true;
        }
        return false;
    }

    public String calculate(List<String> list){
        Stack<String> st = new Stack<>();
        String num1 = "";
        String num2 = "";
        String temRes = "";
        for(int i = 0; i < list.size(); i++){
            String tem = list.get(i);
            switch (tem){
                case "+":
                    num2 = st.pop();
                    num1 = st.pop();
//                    temRes = String.valueOf(Double.parseDouble(num1) + Double.parseDouble(num2));
                    temRes = String.valueOf(new BigDecimal(num1).add(new BigDecimal(num2)));
                    st.push(temRes);
                    break;
                case "-":
                    num2 = st.pop();
                    num1 = st.pop();
//                    temRes = String.valueOf(Double.parseDouble(num1) - Double.parseDouble(num2));
                    temRes = String.valueOf(new BigDecimal(num1).subtract(new BigDecimal(num2)));
                    st.push(temRes);

                    break;
                case "*":
                    num2 = st.pop();
                    num1 = st.pop();
//                    temRes = String.valueOf(Double.parseDouble(num1) * Double.parseDouble(num2));
                    temRes = String.valueOf(new BigDecimal(num1).multiply(new BigDecimal(num2)));
                    st.push(temRes);
                    break;
                case "/":
                    num2 = st.pop();
                    num1 = st.pop();
//                    temRes = String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));
                    temRes = String.valueOf(new BigDecimal(num1).divide(new BigDecimal(num2)));
                    st.push(temRes);
                    break;
                default:
                    st.push(tem);
            }
        }
        Log.d("d", st.peek());
        double res = Double.parseDouble(st.pop());
        if(res < 0 && res > Integer.MIN_VALUE || res >= 0 && res < Integer.MAX_VALUE){
            if(Math.floor(res) == res){
                return String.valueOf((int) res);
            }else{
                return String.valueOf(res);
            }
        }else{
            return String.valueOf(res);
        }
    }

}
