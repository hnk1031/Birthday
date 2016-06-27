package com.example.hashizumeakitoshi.navigationdrawersampleapp;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TabHost;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {


    ArrayList<ListContent> mPlanetTitles;
    ListView mDrawerList;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar mToolbar;
    CustomAdapter mCustomAdapter;
    ListView listView;
    ArrayAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);



        mToolbar.setTitle("Birthday");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);


        mPlanetTitles = new ArrayList<ListContent>();
        mPlanetTitles.add(new ListContent("add", R.drawable.plus));
        mPlanetTitles.add(new ListContent("setting", R.drawable.setting));
        mCustomAdapter = new CustomAdapter(this, R.layout.custom_layout, mPlanetTitles);
        mDrawerList.setAdapter(mCustomAdapter);


        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.open,
                R.string.close
        ) {


        };


        ArrayList<String> mList = new ArrayList<String>();

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.DISPLAY_NAME, ContactsContract.CommonDataKinds.Event.DATA, ContactsContract.CommonDataKinds.Event.TYPE };
        String selection = ContactsContract.Contacts.Data.MIMETYPE + "=? AND (" + ContactsContract.CommonDataKinds.Event.TYPE + "=? OR "
                + ContactsContract.CommonDataKinds.Event.TYPE + "=?) ";
        String[] selectionArgs = new String[] { ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY),
                String.valueOf(ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) };

        Cursor c1 = getContentResolver().query(uri, projection, selection,
                selectionArgs, null);


        if (c1 != null) {
            try {
                while (c1.moveToNext()) {
                    // コンタクトユーザのリストを作成
                    String displayName = c1.getString(c1
                            .getColumnIndex(ContactsContract.CommonDataKinds.Event.DISPLAY_NAME));
                    String date = c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Event.DATA));

                    // 誕生日情報をフォーマット変換
                    String date_tmp = new BirthdayFormat().DateCheck(date);


                    mList = new ArrayList<>();





                }
            } finally {
                c1.close();
            }

        }


        //listに表示
        listView =(ListView) findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);


        listView.setAdapter(adapter);


    }

    public class BirthdayFormat {
        final Boolean logstatus = false;

        // 今日の日付取得
        final Calendar mCalendar = Calendar.getInstance();
        final int mYear = mCalendar.get(Calendar.YEAR);
        final int mMonth = mCalendar.get(Calendar.MONTH);
        final int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        @SuppressWarnings("unused")
        public String DateCheck(String date) {
            int check_year = 0;
            int check_month = 0;
            int check_day = 0;
            int check_date = 0;
            String date_tmp = null;

            if (date.length() > 10) { // 文字数が多い場合は対象外
                return null;

            } else if (date.length() == 10) { // 文字数は正しい
                if (date.indexOf("-") != -1 && date.substring(4, 5).equals("-")
                        && date.substring(7, 8).equals("-")) {

                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(5, 7));
                        check_day = Integer.parseInt(date.substring(8, 10));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            return date; // 正しいのでそのまま返す
                        }

                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }

                } else { // 10文字だけど”-”がない
                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(5, 7));
                        check_day = Integer.parseInt(date.substring(8, 10));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            date_tmp = date.substring(0, 4) + "-"
                                    + date.substring(5, 7) + "-"
                                    + date.substring(8, 10);
                            return date_tmp;
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }
                }

            } else if (date.length() < 10) { // 文字数が正しくない
                int hit[] = { 0, 0, 0 };
                int hit_point = 0;

                for (int i = 0; i < date.length(); i++) {
                    try {
                        // 1文字ずつ拾ってきて文字か数値か判定。文字ならエラーになる。
                        check_date = Integer.parseInt(date.substring(i, i + 1));
                    } catch (Exception e) {
                        // 文字ならヒットした位置とヒットした数を記録
                        try {
                            hit[hit_point] = i;
                            hit_point++;

                            // ヒットが3つ以上になった場合は対象外
                            if (hit_point > 2) {
                                return null;
                            }
                        } catch (Exception e2) {
                            Log.e("Birth2Cal", e + " [target] " + date);
                            return null; // 範囲外になったときように念のため
                        }
                    }
                }

                if (hit_point == 2) {
                    try {
                        if (hit[0] < 4 || (hit[1] - hit[0]) < 1
                                || date.length() - hit[1] < 1) {
                            return null; // 年が4桁以下か月/日が0桁以下のとき
                        } else {
                            NumberFormat nf1 = new DecimalFormat("0000");
                            NumberFormat nf2 = new DecimalFormat("00");
                            check_year = Integer
                                    .parseInt(date.substring(0, hit[0]));
                            check_month = Integer.parseInt(date.substring(
                                    hit[0] + 1, hit[1]));
                            check_day = Integer.parseInt(date.substring(hit[1] + 1,
                                    date.length()));

                            if (check_month == 0 || check_month > 12) {
                                return null;
                            } else if (check_day == 0 || check_day > 31) {
                                return null;
                            } else {
                                date_tmp = nf1.format(check_year) + "-"
                                        + nf2.format(check_month) + "-"
                                        + nf2.format(check_day);
                                return date_tmp;
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }

                } else if (hit_point == 0 && date.length() == 8) { // 区切り文字がないけど20000101なので判定できる
                    try {
                        check_year = Integer.parseInt(date.substring(0, 4));
                        check_month = Integer.parseInt(date.substring(4, 6));
                        check_day = Integer.parseInt(date.substring(6, 8));

                        if (check_month == 0 || check_month > 12) {
                            return null;
                        } else if (check_day == 0 || check_day > 31) {
                            return null;
                        } else {
                            date_tmp = date.substring(0, 4) + "-"
                                    + date.substring(4, 6) + "-"
                                    + date.substring(6, 8);
                            return date_tmp;
                        }
                    } catch (Exception e) {
                        Log.e("Birth2Cal", e + " [target] " + date);
                        return null;
                    }
                } else { // 2000111みたいな1月11日なのか11月1日なのか判定不可
                    return null;
                }
            } else {
                return null;
            }
        }
    }


    public void reload(View v) {
        //初期設定
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.icon);

        //CustomLayout
        RemoteViews customView = new RemoteViews(getPackageName(), R.layout.notification);
        customView.setTextViewText(R.id.text1, "今日誕生日の人は2人です");
        customView.setTextViewText(R.id.text2, "Birthday");
        customView.setTextViewText(R.id.text3, "2016/07/02");
        customView.setTextColor(R.id.text1, Color.WHITE);
        customView.setTextColor(R.id.text2, Color.WHITE);
        customView.setTextColor(R.id.text3, Color.WHITE);
        customView.setImageViewResource(R.id.imageView, R.drawable.cake);
        builder.setContent(customView);

        //クリック時の処理
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        //作成
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(0, builder.build());

    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

}


