package com.example.pendaftaranonlinepasien.Utils;

import android.content.Context;

import java.util.Stack;

public class DataFormatConverterUtils {

    private static DataFormatConverterUtils mDataFormatConverter;
    protected Context mContext;

    public DataFormatConverterUtils(Context context){
        mContext = context;
    }

    public static synchronized DataFormatConverterUtils getInstance(Context context){
        if (mDataFormatConverter == null)
            mDataFormatConverter = new DataFormatConverterUtils(context);
        return mDataFormatConverter;
    }

    public String convertToFormated (String date){
        String formatedDate;
        char[] mDate = date.toCharArray();
        CharStack mChar = new CharStack();
        for (int i = mDate.length - 1; i > -1; i--){
            if (i==6){
                for (int j=i; j < mDate.length; j++)
                    mChar.push(mDate[j]);
                mChar.push('-');
            }
            else if (i==4){
                for (int j=i; j < 6; j++)
                    mChar.push(mDate[j]);
                mChar.push('-');
            } else if (i==0){
                for (int j=i; j <4; j++)
                    mChar.push(mDate[j]);
            }
        }
        formatedDate = mChar.sb.toString();
        return formatedDate;
    }

    public String convertToDBFormat(String date){
        String formatedDate;
        char[] mDate = date.toCharArray();
        CharStack mChar = new CharStack();
        for (int i = mDate.length - 1; i > -1; i--){
            if (i==4){
                for (int j=i; j < mDate.length; j++)
                    mChar.push(mDate[j]);
            }
            else if (i==2){
                for (int j=i; j < 4; j++)
                    mChar.push(mDate[j]);
            } else if (i==0){
                for (int j=i; j <2; j++)
                    mChar.push(mDate[j]);
            }
        }
        formatedDate = mChar.sb.toString();
        return formatedDate;
    }

    class CharStack {
        final StringBuilder sb = new StringBuilder();

        public void push(char ch) {
            sb.append(ch);
        }

        public char pop() {
            int last = sb.length() -1;
            char ch= sb.charAt(last);
            sb.setLength(last);
            return ch;
        }

        public int size() {
            return sb.length();
        }
    }
}
