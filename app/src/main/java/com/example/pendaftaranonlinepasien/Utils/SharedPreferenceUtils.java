package com.example.pendaftaranonlinepasien.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pendaftaranonlinepasien.API.POJO.Pasien;
import com.example.pendaftaranonlinepasien.API.POJO.UserObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils mSharedPreferenceUtils;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;
    private Gson gson;

    private SharedPreferenceUtils(Context context){
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
        gson = new Gson();
    }

    /**
     * Creates single instance of SharedPreferenceUtils
     *
     * @param context context of Activity or Service
     * @return Returns instance of SharedPreferenceUtils
     */
    public static synchronized SharedPreferenceUtils getInstance(Context context){
        if (mSharedPreferenceUtils == null)
            mSharedPreferenceUtils = new SharedPreferenceUtils(context);
        return mSharedPreferenceUtils;
    }

    /** ======================== Setter Value ========================== **/
    // Store String
    public void setValue(String key, String value){
        mSharedPreferencesEditor.putString(key, value).commit();
    }

    // Store Int
    public void setValue(String key, int value){
        mSharedPreferencesEditor.putInt(key, value).commit();
    }

    // Store Boolean
    public void setValue(String key, boolean value){
        mSharedPreferencesEditor.putBoolean(key, value).commit();
    }

    // Store UserObject profile
    public void setValue(UserObject<Pasien> value){
        String json = gson.toJson(value);
        mSharedPreferencesEditor.putString("UserObject", json).commit();
    }
    /** ===================== End of Setter Value ====================== **/

    /** ======================== Getter Value ========================== **/
    // Get String
    public String getStringValue(String key, String defaultValue){
        return mSharedPreferences.getString(key, defaultValue);
    }

    // Get Int
    public int getIntValue(String key, int defaultValue){
        return mSharedPreferences.getInt(key, defaultValue);
    }
    // Get Boolean
    public boolean getBooleanValue(String key, boolean deafultValue){
        return mSharedPreferences.getBoolean(key, deafultValue);
    }
    // Get UserObject Pasien Profile
    public UserObject getUserProfileValue(){
        UserObject<Pasien> user = gson.fromJson(mSharedPreferences.getString("UserObject", ""), new TypeToken<UserObject<Pasien>>(){}.getType());
        return user;
    }
    /** ===================== End of Getter Value ====================== */

    /** ===================== Remove Key ===================== **/
    public void removeKey(String key){
        if (mSharedPreferencesEditor != null){
            mSharedPreferencesEditor.remove(key).commit();
        }
    }

    /** ===================== Clear SharedPreference =====================  */
    public void clear(){
        mSharedPreferencesEditor.clear().commit();
    }
}
