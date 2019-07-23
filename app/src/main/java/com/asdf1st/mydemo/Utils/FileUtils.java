package com.asdf1st.mydemo.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wanghao on 2016/7/14.
 */
public class FileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/";
    public static String VIDEOPATH="bs02Video/";
    //public static String IMAGEPATH="zhijiaimage/";


    /**
     * 获取到sd卡的根目录，并以String形式返回
     *
     * @return
     */
    public static String getSDCardPath() {
        SDPATH = Environment.getExternalStorageDirectory() + "/";
        return SDPATH;
    }

    /**
     * 创建文件或文件夹
     *
     * @param fileName
     *            文件名或问文件夹名
     */
    public void createFile(String fileName) {
        File file = new File(getSDCardPath() + fileName);
        if (fileName.indexOf(".") != -1) {
            // 说明包含，即使创建文件, 返回值为-1就说明不包含.,即使文件
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("创建了文件");
        } else {
            // 创建文件夹
            boolean result=file.mkdir();
            if (result){
                System.out.println("创建了文件夹");
            }else {
                System.out.println("创建文件夹失败");
            }
        }

    }


    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static File mkdirsOnSDCard(String pathName) {
        File file = new File(Environment.getExternalStorageDirectory(), pathName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static void saveString(String str, String path) {
        File jpg = new File(path);
        FileOutputStream b = null;

        try {
            b = new FileOutputStream(jpg);
            b.write(str.getBytes());
        } catch (FileNotFoundException var14) {
            var14.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }
    }

    public static void saveBuffer(String path,String fileName,byte[] data) {
        String absolutePath = FileUtils.mkdirsOnSDCard(path).getAbsolutePath() + "/" + fileName;
        File jpg = new File(absolutePath);
        FileOutputStream b = null;

        try {
            b = new FileOutputStream(jpg);
            b.write(data);
        } catch (FileNotFoundException var14) {
            var14.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }
    }

    public static boolean copy(String input, String output) throws Exception {
        int BUFSIZE = 65536;
        try {
            FileInputStream fis = new FileInputStream(input);
            FileOutputStream fos = new FileOutputStream(output);

            int s;
            byte[] buf = new byte[BUFSIZE];
            while ((s = fis.read(buf)) > -1) {
                fos.write(buf, 0, s);
            }
        } catch (Exception ex) {
            throw new Exception("makehome" + ex.getMessage());
        }
        return true;
    }

    public static boolean checkSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 刷新sd内容
     */
    public static void refreshContent(Context context, String path) {
        Uri data = Uri.parse("file://" + path);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * @param
     * @return
     */
    public static double getSdCardContent() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            long blockSize = sf.getBlockSize();
            long freeBlocks = sf.getAvailableBlocks();
            return (freeBlocks * blockSize) / 1024 / 1024;
        } else {
            return 0;
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param inStream
     */
    public static String convertString(InputStream inStream) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inStream.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            return new String(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 创建临时文件
     *
     * @param srcPath
     * @param destPath
     * @return
     */
    public static boolean createTempFile(String srcPath, String destPath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            FileInputStream in = new FileInputStream(srcPath);
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(new FileOutputStream(destPath));
            int len = 0;
            byte[] buf = new byte[4098];
            while ((len = bis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] res2byte(Context context, int resId) {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }


//    public static File createFile(String url) {
//        File cacheDir = new File(Environment.getExternalStorageDirectory(), url);
//        if (!cacheDir.exists()) {
//            cacheDir.mkdirs();
//        }
//        return cacheDir;
//    }

    public static void deleteFile(String name, String url) {
        File cacheDir = new File(Environment.getExternalStorageDirectory(), url);
        File f = new File(cacheDir, name);
        if (f.exists()) {
            f.delete();
        }
    }

    public static void deletelistFiles(String url) {
        File cacheDir = new File(Environment.getExternalStorageDirectory(), url);
        if (null != cacheDir) {
            File[] file = cacheDir.listFiles();
            if (null != file) {
                if (file.length > 0) {
                    for (int i = 0; i < file.length; i++) {
                        file[i].delete();
                    }
                }
            }
        }
    }

    public static void deletelistFilesForLoading(String url) {
        File cacheDir = new File(url);
        if (null != cacheDir) {
            File[] file = cacheDir.listFiles();
            if (null != file) {
                if (file.length > 0) {
                    Log.i("LoadingActivity", "file.length:" + file.length);
                    for (int i = 0; i < file.length; i++) {
                        file[i].delete();
                    }
                }
            }
        }
    }

    public static File bitMap2File(Bitmap bitmap) {

        String path = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory() + File.separator;//保存到sd根目录下
        }

        //        File f = new File(path, System.currentTimeMillis() + ".jpg");
        File f = new File(path, "save" + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return f;
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
