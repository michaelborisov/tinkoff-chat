package borisov.ru.tinkoff_chat.items;

public class DialogItem {

    private String title;
    private String desc;

    public DialogItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}