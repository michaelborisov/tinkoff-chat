package borisov.ru.tinkoff_chat.activities;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.adapters.DialogAdapter;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.db.models.Message;


public class DialogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Message>> {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private AppCompatButton btnSend;
    private AppCompatEditText etMessage;

    private List<Message> messages;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        etMessage = (AppCompatEditText)findViewById(R.id.edit_text_message);

        btnSend = (AppCompatButton)findViewById(R.id.btn_send_message);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message mes = new Message(etMessage.getText().toString(), 1, new Date());
                mes.save();
                updateMessages(mes);
                etMessage.getText().clear();
            }
        });
        showLoader();


        setSupportActionBar(toolbar);
        getLoaderManager().initLoader(0, null, this);
    }


    private void initRecyclerView(List<Message> dataSet) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        messages = dataSet;
        adapter = new DialogAdapter(messages, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(DialogActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    private void updateMessages(final Message message){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                messages.add(message);
                adapter.notifyDataSetChanged();
            }
        }, 1);
    }

    @Override
    public Loader<List<Message>> onCreateLoader(int id, Bundle args) {


            Loader<List<Message>> mLoader = new Loader<List<Message>>(this){
                @Override
                protected void onStartLoading() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            }catch (InterruptedException ex){
                                ex.printStackTrace();
                            }
                            deliverResult(loadDataset());
                        }
                    }).start();

                }

                @Override
                protected void onStopLoading() {

                }

            };
        return mLoader;

    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, final List<Message> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView(data);
                hideLoader();
            }
        });

    }



    @Override
    public void onLoaderReset(Loader<List<Message>> loader) {

    }


    public void showLoader(){
        progress = new ProgressDialog(this);
        progress.setTitle("Загружаем");
        progress.setMessage("Сообщения в диалоге");
        progress.setCancelable(false);
        progress.show();
    }

    public void hideLoader(){
        progress.dismiss();
    }

    private List<Message> loadDataset() {
        return SQLite.select().from(Message.class).queryList();
    }
}
