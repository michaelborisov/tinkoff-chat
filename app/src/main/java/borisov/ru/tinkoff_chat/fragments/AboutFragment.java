package borisov.ru.tinkoff_chat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import borisov.ru.tinkoff_chat.R;

public class AboutFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    private String title;

    public static AboutFragment newInstance(String title) {
        AboutFragment fragment = new AboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textViewTitle = (TextView) view.findViewById(R.id.text_view_stub);
        textViewTitle.setText("Этот чудесный мессенджер является разработкой в " +
                "рамках курса Android Финтех Тинькофф");
        return view;
    }
}
