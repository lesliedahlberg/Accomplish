package com.lesliedahlberg.accomplish;

import android.provider.BaseColumns;

/**
 * Created by lesliedahlberg on 16/06/16.
 */
public final class AccomplishmentContract {
    public AccomplishmentContract() {
    }

    public static abstract class AccomplishmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "accomplishments";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
    }



}
