package com.example.video_info;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterNativeView;

/** VideoInfoPlugin */
public class VideoInfoPlugin implements MethodChannel.MethodCallHandler {

    private Result permissionResult;
    private final PluginRegistry.Registrar registrar;

    private VideoInfoPlugin(PluginRegistry.Registrar registrar) {
        this.registrar = registrar;
    }
    /** Plugin registration. */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "moon/video_info");
        final VideoInfoPlugin plugin = new VideoInfoPlugin(registrar);
        channel.setMethodCallHandler(plugin);
        registrar.addViewDestroyListener(
            new PluginRegistry.ViewDestroyListener() {
            @Override
            public boolean onViewDestroy(FlutterNativeView view) {
                plugin.onDestroy();
                return false; // We are not interested in assuming ownership of the NativeView.
            }
        });
    }
    private void onDestroy() {
    }
    public ArrayList<EntityVideo> getVideoList()  {
        ArrayList<EntityVideo> sysVideoList = new ArrayList<>();
        Context context = registrar.context();
        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        final String[] TNUMB_COLUMNS = {
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };
        // 视频其他信息的查询条件
        String[] mediaColumns = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.ALBUM,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DURATION};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media
                        .EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        if (cursor == null) {
            return sysVideoList;
        }
        if (cursor.moveToFirst()) {
            do {
                EntityVideo info = new EntityVideo();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        TNUMB_COLUMNS, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    info.setThumbPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
                info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));
                info.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
                        .DATA)));
                info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
                info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DURATION)));
                info.setDateAdded(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DATE_ADDED)));
                info.setDateModified(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DATE_MODIFIED)));
                info.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.ALBUM)));
                info.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.ARTIST)));
                info.setResolution(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.RESOLUTION)));
                info.setHeight(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.HEIGHT)));
                info.setWidth(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.WIDTH)));
                sysVideoList.add(info);
            } while (cursor.moveToNext());
        }
        return sysVideoList;
    }
  @Override
  public void onMethodCall(MethodCall call, final Result result) {
    switch (call.method) {
        case  "getVideoList":
            ArrayList<EntityVideo> videoList = getVideoList();
            Gson gson = new GsonBuilder().create();
            String videoStr = gson.toJson(videoList);
            result.success(videoStr);
          break;
        default:
            result.notImplemented();
    }
  }
}
