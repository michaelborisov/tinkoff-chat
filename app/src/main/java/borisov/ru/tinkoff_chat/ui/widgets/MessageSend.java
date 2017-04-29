package borisov.ru.tinkoff_chat.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import borisov.ru.tinkoff_chat.R;

/**
 * Created by michaelborisov on 17.04.17.
 */

public class MessageSend extends LinearLayout {

    private AppCompatEditText editText;
    private AppCompatButton sendButton;

    public MessageSend(Context context) {
        super(context);
    }

    public MessageSend(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MessageSend(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_message_send, this);
        editText = (AppCompatEditText) findViewById(R.id.edit_text_message);
        sendButton = (AppCompatButton) findViewById(R.id.btn_send_message);
        requestLayout();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    sendButton.setEnabled(false);
                }else{
                    sendButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //empty
            }
        });



    }





}
