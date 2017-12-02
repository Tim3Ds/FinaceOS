package comtim3ds.github.financeos.data;

import android.provider.BaseColumns;

/**
 * Created by TinMa on 11/28/2017.
 */

public class financeOSContract{
    public static final class financeOSEntry implements BaseColumns{
        public static final String TABLE_NAME = "finance_item";
        public static final String COLUMN_Type = "type";
        public static final String COLUMN_Item_Name = "item_name";
        public static final String COLUMN_Item_Value = "item_value";
        public static final String COLUMN_Expected_Date = "expected_date";
    }
}

