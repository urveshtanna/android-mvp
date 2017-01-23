package com.urvesh.android_arch_mvp.wrapper;

/**
 * Created by urveshtanna on 21/01/17.
 */

public class FavoriteWrapper {

    //Wrapper to quickly convert to model
    /*public static ContentValues modelToContent(ItemModel itemModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID, itemModel.getItemId());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY, itemModel.getCategoryName());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY_ID, itemModel.getCategoryId());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_IMAGE_URL, itemModel.getImageUrl());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY_ID, itemModel.getSubCategoryId());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY, itemModel.getSubCategoryName());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT_ID, itemModel.getItemUnitId());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_NAME, itemModel.getItemName());
        contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT, itemModel.getItemUnit());
        if (itemModel.isItemSelected())
            contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SELECTED, 1);
        else
            contentValues.put(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SELECTED, 0);

        return contentValues;
    }

    public static ItemModel cursorToModel(Cursor cursor) {
        ItemModel itemModel = new ItemModel();
        itemModel.setItemId(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID)));
        itemModel.setCategoryName(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY)));
        itemModel.setCategoryId(cursor.getInt(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_CATEGORY_ID)));
        itemModel.setImageUrl(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_IMAGE_URL)));
        itemModel.setSubCategoryId(cursor.getInt(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY_ID)));
        itemModel.setSubCategoryName(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SUBCATEGORY)));
        itemModel.setItemUnitId(cursor.getInt(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT_ID)));
        itemModel.setItemUnit(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_UNIT)));
        itemModel.setItemName(cursor.getString(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_NAME)));

        if (cursor.getInt(cursor.getColumnIndex(FavoriteProvider.FavoriteColumns.COLUMN_ITEM_SELECTED)) == 1)
            itemModel.setItemSelected(true);
        else
            itemModel.setItemSelected(false);
        return itemModel;
    }*/
}
