package borisov.ru.tinkoff_chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.activities.DialogActivity;
import borisov.ru.tinkoff_chat.activities.NavigationActivity;
import borisov.ru.tinkoff_chat.adapters.DialogAdapter;
import borisov.ru.tinkoff_chat.adapters.DialogsListAdapter;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.items.DialogItem;

public class DialogListFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    private String title;

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
        RecyclerView dialogsRecycler = (RecyclerView) view.findViewById(R.id.dialogs_recycler_view);
        dialogsRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dialogsRecycler.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DialogsListAdapter(createDataset(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goToDialog = new Intent(getContext(), DialogActivity.class);
                startActivity(goToDialog);
            }
        });
        dialogsRecycler.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), layoutManager.getOrientation()
        );
        dialogsRecycler.addItemDecoration(dividerItemDecoration);
        return view;
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
