package com.bbg.textalbum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class NameAlbumActivity extends AppCompatActivity {
    GridView gridView;
    ContentResolver contentResolver;
    ArrayList<String> paths = new ArrayList<>();
    List<String> parentDirs = new ArrayList<>();
    List<String> parentImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_album);
        gridView = (GridView) findViewById(R.id.gridView);
        contentResolver = this.getContentResolver();
        getImage();
        initAdapter();

    }
    private void initAdapter(){
        AlbumAdapter albumAdapter = new AlbumAdapter(NameAlbumActivity.this,parentDirs,parentImage);
        gridView.setAdapter(albumAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NameAlbumActivity.this,ChildPhotoActivity.class);
                intent.putStringArrayListExtra("paths",paths);
                intent.putExtra("dirName",parentDirs.get(i));
                startActivity(intent);
            }
        });

    }
   private  void  getImage(){
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;//从内存中获得图片
        Cursor mCursor = contentResolver.query(mImageUri,null,
                null,null, MediaStore.Images.Media.DATE_MODIFIED);

        while (mCursor.moveToNext()){
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));//路径
            paths.add(path);
            //获取到本机中所有的图片后，要对图片进行分类，因此通过路径中的parentDir文件来问分类
            File file = new File(path);
            File parentFile= file.getParentFile();
            String parentFileString = parentFile.getAbsolutePath();
            String ParentFileName = parentFileString.substring(parentFileString.lastIndexOf("/")+1);
            if(parentDirs.contains(ParentFileName)){
                continue;
            }else{
                parentImage.add(path);
                parentDirs.add(ParentFileName);
            }
        }

   }


}
