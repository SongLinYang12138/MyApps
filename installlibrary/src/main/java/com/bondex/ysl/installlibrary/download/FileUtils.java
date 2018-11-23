package com.bondex.ysl.installlibrary.download;


import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * date: 2018/11/12
 * Author: ysl
 * description:
 */
public class FileUtils {

    private static int sBufferSize = 8192;

    public static void writeResponseToDisk(String path, Response<ResponseBody> response, DownloadListener downloadListener) {
        //从response获取输入流以及总大小
        writeFileFromIS(new File(path), response.body().byteStream(), response.body().contentLength(), downloadListener);
    }


    //将输入流写入文件
    private static void writeFileFromIS(File file, InputStream is, long totalLength, DownloadListener downloadListener) {
        //开始下载
        downloadListener.onStart();

        //创建文件
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdir();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                downloadListener.onFail("createNewFile IOException");
            }
        }

        OutputStream os = null;
        long currentLength = 0;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[sBufferSize];
            byte[] md5Data = new byte[1024];

            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                //计算当前下载进度

//                md5Data = spilet(md5Data, data);
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
            }


            //下载完成，并返回保存的文件路径
            downloadListener.onFinish(file.getAbsolutePath(), md5Data);
        } catch (IOException e) {
            e.printStackTrace();
            downloadListener.onFail("IOException");
        } finally {
            try {

                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static byte[] spilet(byte[] first, byte[] second) {

        byte[] c = new byte[first.length + second.length];

        System.arraycopy(first, 0, c, 0, first.length);

        System.arraycopy(second, 0, c, first.length, second.length);

        return c;
    }


}
