package borisov.ru.tinkoff_chat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import borisov.ru.tinkoff_chat.adapters.DialogsAdapter;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.items.DialogItem;

public class DialogActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initRecyclerView();

        subscribeOnAboutButton();
    }

    private void subscribeOnAboutButton(){
        ImageButton imAbout = (ImageButton)findViewById(R.id.image_button_about);
        imAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent goToAbout = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(goToAbout);

            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_dialogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DialogsAdapter(createDataset(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(DialogActivity.this, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

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
