package com.chaco.chao.tools.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author voidsun
 * @Date 2015/7/2
 * @Email voidsun@126.com
 */
public @Slf4j
class CsvExport<T> {
    private static final String SPLIT = ",";
    private static final String BREAK_LINE = "\n";

    public void exportAsCsv(List<T> data, OutputStream outputStream, String... fieldNames){
        exportAsCsv(new ArrayList<String>(), data, outputStream, fieldNames);
    }

    public void exportAsCsv(List<String> headList, List<T> dataList, OutputStream outputStream,
                            String... fieldNames) {
        log.info("CsvExport开始");
        StringBuilder builder = new StringBuilder();
        try {
            if(!headList.isEmpty()){
                for(String head : headList){
                    builder.append(head).append(SPLIT);
                }
                breakLine(builder);
            }
            if(!dataList.isEmpty()) {
                ReflectUtil<T> reflectUtil = new ReflectUtil<>(dataList.get(0).getClass());
                for (T t : dataList) {
                    for(String fieldName : fieldNames){
                        Object fieldValue = reflectUtil.invokeGetter(t, fieldName);
                        if(fieldValue == null){
                            fieldValue = "";
                        }
                        builder.append(fieldValue).append(SPLIT);
                    }
                    breakLine(builder);
                }
            }
            outputStream.write(builder.toString().getBytes(Charset.forName("GBK")));
            outputStream.flush();
            log.info("CsvExport结束");
        } catch (IOException e) {
            log.error("", e);
        } finally {
            this.silenceClose(outputStream);
        }
    }

    private void breakLine(StringBuilder builder){
        builder.deleteCharAt(builder.length() - 1);
        builder.append(BREAK_LINE);
    }


    public void silenceClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception var2) {
                log.error("", var2);
            }
        }

    }

}
