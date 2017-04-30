package borisov.ru.tinkoff_chat.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.UpdateTableMigration;

import java.util.List;

import borisov.ru.tinkoff_chat.db.models.Dialog;
import borisov.ru.tinkoff_chat.db.models.Dialog_Table;
import borisov.ru.tinkoff_chat.db.models.Message;

/**
 * Created by michaelborisov on 29.04.17.
 */
@Database(name = "chat_db", version = 2)
public final class ChatDatabase {

    @Migration(version = 2, priority = 2, database = ChatDatabase.class)
    public static class AddLastMessageColumnDialogMigration extends AlterTableMigration<Dialog> {

        public AddLastMessageColumnDialogMigration(){
            super(Dialog.class);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            addColumn(SQLiteType.TEXT, "lastMessage");
        }

    }

    @Migration(version = 2, priority = 1, database = ChatDatabase.class)
    public static class LastMessageColumnUpdateMigration extends UpdateTableMigration<Dialog> {

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();
            set(Dialog_Table.lastMessage.eq(""));

        }

        public LastMessageColumnUpdateMigration(Class<Dialog> table) {
            super(table);
        }
    }
}
