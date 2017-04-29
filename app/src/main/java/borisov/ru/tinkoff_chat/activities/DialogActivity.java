package borisov.ru.tinkoff_chat.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.adapters.DialogAdapter;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.items.DialogItem;

public class DialogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DialogItem>> {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private AppCompatButton btnSend;
    private AppCompatEditText etMessage;

    private List<DialogItem> messages;

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
                updateMessages(new DialogItem("Me", etMessage.getText().toString()));
                etMessage.getText().clear();
            }
        });


        setSupportActionBar(toolbar);
        getLoaderManager().initLoader(0, null, this);
    }


    private void initRecyclerView(List<DialogItem> dataSet) {
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

    private void updateMessages(DialogItem message){
        messages.add(message);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<DialogItem>> onCreateLoader(int id, Bundle args) {


            Loader<List<DialogItem>> mLoader = new Loader<List<DialogItem>>(this){
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
                            deliverResult(createDataset());
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
    public void onLoadFinished(Loader<List<DialogItem>> loader, final List<DialogItem> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView(data);
            }
        });

    }


    @Override
    public void onLoaderReset(Loader<List<DialogItem>> loader) {

    }



    private List<DialogItem> createDataset() {
        List<DialogItem> list = new ArrayList<>();
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        list.add(new DialogItem("title", "desc"));
        return list;
    }
}
