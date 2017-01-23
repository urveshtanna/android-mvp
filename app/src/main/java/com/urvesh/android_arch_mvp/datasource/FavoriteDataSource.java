package com.urvesh.android_arch_mvp.datasource;

import android.content.Context;

/**
 * Created by urveshtanna on 21/01/17.
 */

public class FavoriteDataSource {

    private Context mContext;

    public FavoriteDataSource(Context context) {
        this.mContext = context;
    }

    /*public void insert(ItemModel ItemModel) {
        ContentValues contentValues = FavoriteWrapper.modelToContent(ItemModel);
        mContext.getContentResolver().insert(FavoriteProvider.FavoriteColumns.CONTENT_URI, contentValues);
    }

    public void update(ItemModel itemModel) {
        mContext.getContentResolver().update(FavoriteProvider.FavoriteColumns.CONTENT_URI, FavoriteWrapper.modelToContent(itemModel), FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID + " ='" + itemModel.getItemId() + "' ", null);
    }

    public void delete(ItemModel itemModel) {
        mContext.getContentResolver().delete(FavoriteProvider.FavoriteColumns.CONTENT_URI, FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID + " ='" + itemModel.getItemId() + "' ", null);
    }

    public static void deleteAllItems(Context context) {
        Cursor cursor = context.getContentResolver().query(FavoriteProvider.FavoriteColumns.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    context.getContentResolver().delete(FavoriteProvider.FavoriteColumns.CONTENT_URI, null, null);
                }
                cursor.close();
            }
        }
    }

    public ItemModel getProduct(String productId) {
        ItemModel ItemModel = new ItemModel();
        Cursor cursor = mContext.getContentResolver().query(FavoriteProvider.FavoriteColumns.CONTENT_URI, null, FavoriteProvider.FavoriteColumns.COLUMN_ITEM_ID + " = '" + productId + "'", null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                ItemModel = FavoriteWrapper.cursorToModel(cursor);
            }
            cursor.close();
        }
        return ItemModel;
    }

    public ArrayList<ItemModel> getAllProducts() {
        ArrayList<ItemModel> ItemModelList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(FavoriteProvider.FavoriteColumns.CONTENT_URI, null, null, null, FavoriteProvider.FavoriteColumns.COLUMN_ITEM_NAME);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ItemModel itemModel = FavoriteWrapper.cursorToModel(cursor);
                    if(itemModel.getItemName() != null && itemModel.getImageUrl() != null) {
                        ItemModelList.add(itemModel);
                    }
                }
            }
            cursor.close();
        }
        return ItemModelList;
    }*/
}
