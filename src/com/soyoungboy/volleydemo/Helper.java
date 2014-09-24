package com.soyoungboy.volleydemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Helper {

	public static final String PHOTO_FOLDER = "/DCIM/100ANDRO/";
	
	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getPhotoFileName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss", Locale.getDefault());
		Date date = new Date();
		return Environment.getExternalStorageDirectory().getPath() + "/DCIM/100ANDRO/Todo_" + dateFormat.format(date) + ".jpg";
	}
	
	public static Bitmap zoomBitmap(Bitmap oldBitmap, int reqWidth, int reqHeight) {
		Matrix pMatrix = new Matrix();
		int oldWidth = oldBitmap.getWidth();
		int oldHeight = oldBitmap.getHeight();

		double diagnoal = Math.sqrt(oldWidth * oldWidth + oldHeight * oldHeight);
		float scaleX = (float) (reqWidth / diagnoal);
		float scaleY = (float) (reqHeight / diagnoal);

		float scale = scaleX < scaleY ? scaleX : scaleY;
		pMatrix.postScale(scale, scale);
		Bitmap bitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldWidth,
				oldHeight, pMatrix, true);

		return bitmap;
	}
	
	public static ImageView createImageViewFromBitmap(Context context, Bitmap bitmap){
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setImageBitmap(bitmap);
		return imageView;
	}
	
	public static String getImagePath(ContentResolver resolver, Uri uri){
		Cursor cursor = resolver.query(uri, null, null, null, null);
		if(cursor.moveToFirst()){
			do {				
				String imagePath = cursor.getString(cursor.getColumnIndex(Media.DATA));				
				return imagePath;
			}while(cursor.moveToNext());
		}		
		return null; 
	}
}
