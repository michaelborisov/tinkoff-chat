package borisov.ru.tinkoff_chat.db.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import borisov.ru.tinkoff_chat.db.ChatDatabase;

/**
 * Created by michaelborisov on 29.04.17.
 */

@Table(database = ChatDatabase.class)
public class Message extends BaseModel{

    public Message(){
        //empty
    }

    public Message(String messageText, Integer authorId, Date creationTime){
        this.messageText = messageText;
        this.authorId = authorId;
        this.creationTime = creationTime;
    }

    public Message(Integer id, String messageText, Integer authorId, Date creationTime){
        this.id = id;
        this.messageText = messageText;
        this.authorId = authorId;
        this.creationTime = creationTime;
    }

    public Integer getId() {
        return id;
    }

    public String getMessageText() {
        return messageText;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @PrimaryKey(autoincrement = true)
    private Integer id;

    @Column
    private String messageText;

    @Column
    private Integer authorId;

    @Column
    private Date creationTime;
}
