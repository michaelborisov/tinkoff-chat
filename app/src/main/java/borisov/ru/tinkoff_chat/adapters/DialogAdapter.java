package borisov.ru.tinkoff_chat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

import borisov.ru.tinkoff_chat.R;
import borisov.ru.tinkoff_chat.interfaces.OnItemClickListener;
import borisov.ru.tinkoff_chat.db.models.Message;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.MessageViewHolder> {


    private static final int TYPE_MY_MESSAGE = 0;
    private static final int TYPE_RECEIVED_MESSAGE = 1;

    private List<Message> dataset;
    private OnItemClickListener clickListener;

    public DialogAdapter(List<Message> dataset, OnItemClickListener clickListener) {
        this.dataset = dataset;
        this.clickListener = clickListener;
    }

    @Override
    public DialogAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MY_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_dialog,
                    parent,
                    false
            );
            return new MyMessageViewHolder(view, clickListener);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_dialog,
                    parent,
                    false
            );
            return new ReceivedMessageViewHolder(view, clickListener);
        }
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        if(holder instanceof MyMessageViewHolder) {
            ((MyMessageViewHolder) holder).title.setText(
                    String.valueOf(dataset.get(position).getAuthorId())
            );
            ((MyMessageViewHolder) holder).desc.setText(dataset.get(position).getMessageText());
        }else{
            ((ReceivedMessageViewHolder) holder).title.setGravity(Gravity.END);
            ((ReceivedMessageViewHolder) holder).desc.setGravity(Gravity.END);
            ((ReceivedMessageViewHolder) holder).title.setText(
                    String.valueOf(dataset.get(position).getAuthorId())
            );
            ((ReceivedMessageViewHolder) holder).desc.setText(dataset.get(position).getMessageText());
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_MY_MESSAGE;
        }

        return TYPE_RECEIVED_MESSAGE;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        public MessageViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class MyMessageViewHolder extends MessageViewHolder {

        public TextView title;
        public TextView desc;

        public MyMessageViewHolder(View view, OnItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_dialog_title);
            desc = (TextView) view.findViewById(R.id.tv_dialog_desc);
            setListener(listener);
        }

        private void setListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public static class ReceivedMessageViewHolder extends MessageViewHolder {

        public TextView title;
        public TextView desc;

        public ReceivedMessageViewHolder(View view, OnItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_dialog_title);
            desc = (TextView) view.findViewById(R.id.tv_dialog_desc);
            setListener(listener);
        }

        private void setListener(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}