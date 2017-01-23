package com.urvesh.android_arch_mvp.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.urvesh.android_arch_mvp.BuildConfig;
import com.urvesh.android_arch_mvp.tools.SelectionBuilder;


/**
 * Created by urveshtanna on 21/01/17.
 */

public class FavoriteProvider extends BaseProvider{

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider.item.favorite";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final int ROUTE_ITEMS = 1;
    public static final int ROUTE_ITEM_ID = 2;
    private static final String PATH_ITEM = "items.favorite";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TAG = "FavoriteProvider";

    static {
        sUriMatcher.addURI(AUTHORITY, "items.favorite", ROUTE_ITEMS);
        sUriMatcher.addURI(AUTHORITY, "items.favorite/*", ROUTE_ITEM_ID);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ROUTE_ITEMS:
                return FavoriteProvider.FavoriteColumns.CONTENT_TYPE;
            case ROUTE_ITEM_ID:
                return FavoriteProvider.FavoriteColumns.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SelectionBuilder builder = new SelectionBuilder(TAG);
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case ROUTE_ITEM_ID:
                // Return a single entry, by ID.
                String id = uri.getLastPathSegment();
                builder.where(FavoriteProvider.FavoriteColumns._ID + "=?", id);
            case ROUTE_ITEMS:
                // Return all known entries.
                builder.table(FavoriteProvider.FavoriteColumns.TABLE_NAME).where(selection, selectionArgs);
                Cursor cursor = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context context = getContext();
                assert context != null;
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Insert a new entry into the database.
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        assert db != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        switch (match) {
            case ROUTE_ITEMS:
                long id = db.insertOrThrow(FavoriteProvider.FavoriteColumns.TABLE_NAME, null, values);
                result = Uri.parse(FavoriteProvider.FavoriteColumns.CONTENT_URI + "/" + id);
                break;
            case ROUTE_ITEM_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    /**
     * Delete an entry by database by URI.
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder(TAG);
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ITEMS:
                count = builder.table(FavoriteProvider.FavoriteColumns.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            case ROUTE_ITEM_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(FavoriteProvider.FavoriteColumns.TABLE_NAME)
                        .where(FavoriteProvider.FavoriteColumns._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    /**
     * Update an entry in the database by URI.
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder(TAG);
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case ROUTE_ITEMS:
                count = builder.table(FavoriteProvider.FavoriteColumns.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            case ROUTE_ITEM_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(FavoriteProvider.FavoriteColumns.TABLE_NAME)
                        .where(FavoriteProvider.FavoriteColumns._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context context = getContext();
        assert context != null;
        context.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    public static class FavoriteColumns implements BaseColumns {

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.mvp.items.favorite";

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.mvp.items.favorite";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ITEM).build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_ITEM_CATEGORY_ID = "itemCategoryId";

        public static final String COLUMN_ITEM_CATEGORY = "itemCategory";

        public static final String COLUMN_ITEM_NAME = "itemName";

        public static final String COLUMN_IS_ACTIVE = "isActive";

        public static final String COLUMN_ITEM_ID = "itemId";

        public static final String COLUMN_ITEM_SELECTED = "itemSelected";

        public static final String COLUMN_IMAGE_URL = "imageUrl";

        public static final String COLUMN_ITEM_SUBCATEGORY_ID = "itemSubCategoryId";

        public static final String COLUMN_ITEM_SUBCATEGORY = "itemSubCategory";

        public static final String COLUMN_ITEM_UNIT_ID = "itemUnitId";

        public static final String COLUMN_ITEM_UNIT = "itemUnit";
    }
}
