package com.chaco.chao.tools.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author:zhaopeiyan001
 * Date:2019-01-09 10:47
 */

public @Slf4j
class DownLoadFileUtils {

    /**
     * 根据url下载文件
     * @param urlStr  下载文件的url
     * @return 返回字节数组
     */
    public static byte[] downloadFile(String urlStr) {

        byte[] data = null;
        InputStream inputStream = null;
        if (StringUtils.isBlank(urlStr)) {
            log.error("The url is blank");
            return data;
        }
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);       //设置超时时间为3s
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
            inputStream = conn.getInputStream();
            data = readInputStream(inputStream);
        } catch (IOException e) {
            log.error("Error at downloading the file! url:{}", urlStr, e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private static byte[] readInputStream(InputStream inputStream) {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("Error at reading data from inputStream!", e);
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }


    /**
     * 返回参数url对应的输入流
     *
     * @param url "ftp://sun:F@127.0.0.1:21/ftp/img_1.jpg"
     * @return boolean
     */
    public static boolean downloadByUrl(URL url, String localPath) {
        boolean isSuccess = false;
        try {
            InputStream in = url.openStream();

            File localFile = new File(localPath);
            OutputStream out = new FileOutputStream(localFile);
            byte[] data = new byte[1024];
            int length = -1;
            while ((length = in.read(data)) != -1) {
                out.write(data, 0, length);
            }
            out.flush();
            out.close();
            in.close();
            isSuccess = true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }


    public static void main(String arg[]) throws MalformedURLException {
        URL url = new URL("https://cn.bing.com/th?id=OHR.FujiSakura_ZH-CN8005792871_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
        DownLoadFileUtils.downloadByUrl(url, "/Users/jaychao/Desktop/mg_3.png");
    }
}
