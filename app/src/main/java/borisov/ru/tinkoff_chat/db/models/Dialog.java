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
public class Dialog extends BaseModel {

    public Dialog(){
        //empty
    }

    public Dialog (String title, String description, Date creationTime){
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
    }

    public Dialog (Integer id, String title, String description, Date creationTime){
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


    @PrimaryKey(autoincrement = true)
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Date creationTime;

    @Column
    private String lastMessage;



}
