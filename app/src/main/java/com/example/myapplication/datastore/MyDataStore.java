package com.example.myapplication.datastore;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import io.reactivex.Flowable;
import io.reactivex.Single;


public class MyDataStore {
    private static final String TAG = MyDataStore.class.getSimpleName();
    //保存在磁盘上的DataStore文件名称
    private static final String MY_DATA_STORE_NAME = "MyData.pb";
    //DataStore中保存的数据对应的key名称，示例中只有一个，实际可以有多个key-value对
    private final Preferences.Key<String> DATA_ID = PreferencesKeys.stringKey("DATA_ID");

    private Context mContext;
    //数据存储对象
    private RxDataStore<Preferences> mDataStore;

    //使用单例来访问本类
    private static MyDataStore INSTANCE;

    private MyDataStore(Context context) {
        if (context == null) {
            throw new RuntimeException("Application context cannot be null");
        }
        mContext = context.getApplicationContext();
        //创建数据存储对象
        mDataStore = new RxPreferenceDataStoreBuilder(mContext, MY_DATA_STORE_NAME).build();
    }

    public static MyDataStore getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (MyDataStore.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyDataStore(context);
                }
            }
        }
        return INSTANCE;
    }

    //从DataStore中查询键对应的值
    public String getDataValue() {
        Log.d(TAG, "getDataValue");
        Flowable<String> carIdFlow = mDataStore.data().map(prefs -> prefs.get(DATA_ID));
        try {
            //同步等待返回值
            return carIdFlow.blockingFirst();
        } catch (Exception e) {
            //查询不到默认返回null
            Log.e(TAG, "none data value found in data store");
            return null;
        }
    }

    //为DataStore中的键设置新的值
    public void setDataValue(String dataValue) {
        Log.d(TAG, "setDataValue: " + dataValue);
        Single<Preferences> setResult = mDataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            String oldValue = preferences.get(DATA_ID);
            mutablePreferences.set(DATA_ID, dataValue);
            return Single.just(mutablePreferences);
        });
    }

}
