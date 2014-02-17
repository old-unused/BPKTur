package com.retor.p000001;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ExpandableListView;
import com.perm.kate.api.*;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by retor on 19.01.14.
 */
public class Browser extends Activity {

    private final int REQUEST_LOGIN=1;
    private final int groupId = Cons.groupId;

    Context conti;

    public String name;
    public String picUrl;

    ArrayList<massToView> myTitle = new ArrayList<massToView>();
    ArrayList<Comment> myComment = new ArrayList<Comment>();

    DoListView dlv = new DoListView();
    Api api;
    Account acc = new Account();
    ExpandableListView lv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = this.getActionBar();
//        actionBar.hide();
        conti = this.getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.browser);

        lv = (ExpandableListView) findViewById(R.id.expandableListView);
        progressDialog = new ProgressDialog(this).show(this, null, "Loading...", true, true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent = new Intent(conti, main.class);
                Browser.this.finish();
                dlv.cancel(true);
                startActivity(intent);
            }
        });


        acc.restore(this);
        //Если сессия есть создаём API для обращения к серверу
        if(acc.access_token == null){
            startAuth(this);
        }else{
            api=new Api(acc.access_token, Cons.API_ID);
            if (myTitle.size() == 0){
                dlv.execute();
            }
        }
    }

    protected void startAuth(Context context){
        Intent intent = new Intent();
        intent.setClass(context, auth.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch( requestCode ) {
            //Получили авторизацию от контакта
            case REQUEST_LOGIN:
                if( resultCode == RESULT_OK ) {
                    //авторизовались успешно
                    acc.access_token = data.getStringExtra("token");
                    acc.user_id = data.getLongExtra("user_id", 0);
                    acc.save(Browser.this);
                    api = new Api(acc.access_token, Cons.API_ID);
                    dlv.execute();
                }
            break;
        }
    }

    private class DoListView extends AsyncTask<ArrayList<GroupTopic>, Void, ArrayList<massToView>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         *
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<massToView> doInBackground(ArrayList<GroupTopic>... params) {
            try {
                CommentList groupComment;

                ArrayList<GroupTopic> groupTop = new ArrayList<GroupTopic>(api.getGroupTopics(groupId, 0, 0, 10, 0));
                if (groupTop.size()!=0){
                    massToView tmp;
                    String title;
                    String author;
                    String pic;
                    String data;
                    int comCount;
                    long tid;
                    for (int i = 0; i < groupTop.size(); i++){
                        tid = groupTop.get(i).tid;
                        data = dataConvert(Long.valueOf(groupTop.get(i).created));
                        title = groupTop.get(i).title.toString();
                        comCount = groupTop.get(i).comments;
                        authorInf(String.valueOf(groupTop.get(i).created_by));
                        author = name;
                        pic = picUrl;

                        //начнем сбор коментариев по tid в отдельный массив

                        if (comCount!=0){
                            String comMessage;
                            String comAuthor;
                            String comPic;
                            String comData;
                            groupComment = api.getGroupTopicComments(((long) groupId), tid, 0, 0, 10, 0, false);
                            Comment tmpComment;
                            if (groupComment.comments.size()!=0) {
                                for (int k = 0; k < groupComment.comments.size(); k++) {
                                    comMessage = groupComment.comments.get(k).message.toString();
                                    comData = dataConvert(Long.valueOf(groupComment.comments.get(k).date)).toString();
                                    authorInf(String.valueOf(groupComment.comments.get(k).from_id));
                                    comAuthor = name;
                                    comPic = picUrl;
                                    tmpComment = new Comment(comMessage, comAuthor, comData, tid, comPic);
                                    myComment.add(k, tmpComment);
//                                    Log.i("Comment", myComment.get(k).getMessage());
                                }
                                Log.i("myComment", String.valueOf(myComment.size()));
                            }
                        }
                        tmp = new massToView(title, tid, comCount, author, data, pic, myComment);
                        myTitle.add(i, tmp); //Собрали и добавили информацию о заголовке и авторе
                        Log.i("myTitle", title + " " + author +" "+ data +" "+ myTitle.get(i).getCount());
                        myComment.clear();
                    }
                }
            } catch (KException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (isCancelled()){
                return null;
            }

        return myTitle;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<massToView> aVoid) {
            Browser.super.runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    setMyAdapter(myTitle);
                }
            });
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

    public void setMyAdapter(ArrayList<massToView> groups){
        Log.i("VV", "Delaem ListView");
        MyExpandListAdapter adapter = new MyExpandListAdapter(this, this, groups);
        lv.setAdapter(adapter);
    }

    public void authorInf(String authorId) throws JSONException, IOException, KException {
        ArrayList<User> user = api.searchUserExtended(authorId, "photo_100", null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null);
        name = user.get(0).first_name + " " + user.get(0).last_name;
        picUrl = user.get(0).photo_medium_rec.toString();
    }

    public String dataConvert(long dataString){
        long temp = dataString*1000;// its need to be in milisecond
        Date data = new java.util.Date(temp);
        String dataStr = new SimpleDateFormat("dd.MM.yyyy").format(data);
        return dataStr;
    }
/*    public String authorName(String authorId) throws JSONException, IOException, KException {
        ArrayList<User> user = api.searchUserExtended(authorId, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null);
        String tmp;
        String tmpUrl;
        tmp = user.get(0).first_name + " " + user.get(0).last_name;
        tmpUrl = user.get(0).photo.toString();
        Log.i("Picture", tmpUrl);
        return tmp, tmpUrl;
    }*/
}
