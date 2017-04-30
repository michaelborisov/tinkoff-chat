package borisov.ru.tinkoff_chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.activities.DialogActivity;
import borisov.ru.tinkoff_chat.adapters.DialogsListAdapter;
import borisov.ru.tinkoff_chat.db.models.Message;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.db.models.Dialog;

public class DialogListFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    FloatingActionButton fab;
    private String title;
    private List<Dialog> dialogs;
    private DialogsListAdapter mAdapter;

    public static DialogListFragment newInstance(String title) {
        DialogListFragment fragment = new DialogListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_list, container, false);
        final RecyclerView dialogsRecycler = (RecyclerView) view.findViewById(R.id.dialogs_recycler_view);
        dialogsRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dialogsRecycler.setLayoutManager(layoutManager);
        dialogs = loadDataset();
        mAdapter = new DialogsListAdapter(dialogs, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goToDialog = new Intent(getContext(), DialogActivity.class); //MainActivity.class);
                startActivity(goToDialog);
            }
        });

        dialogsRecycler.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), layoutManager.getOrientation()
        );
        dialogsRecycler.addItemDecoration(dividerItemDecoration);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_dialog_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long size = countDataset();
                Dialog mDialog = new Dialog("Чат " + size, "Описание " + size, new Date());
                mDialog.save();
                mAdapter.addDialog(mDialog);
            }
        });
        return view;
    }

    private List<Dialog> loadDataset() {
       return SQLite.select().from(Dialog.class).queryList();
    }

    private long countDataset(){
        //return SQLite.select().from(Dialog.class).count();
        return (long)SQLite.select().from(Dialog.class).queryList().size();
    }
}
