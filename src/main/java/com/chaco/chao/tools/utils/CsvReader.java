package com.chaco.chao.tools.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

/**
 * 解析csv文件转成bean
 * author:zhaopeiyan001
 * Date:2019-06-26 17:24
 */
public @Slf4j
class CsvReader {

    public <T> List<T> getCsvData(MultipartFile file, Class<T> clazz) throws Exception {
        log.info("读取csv文件开始。。。");
        InputStreamReader in;
        try {
            in = new InputStreamReader(file.getInputStream(), "gbk");
        } catch (Exception e) {
            log.error("读取csv文件错误，异常信息:" + e.getMessage());
            throw new Exception("读取csv文件错误，异常信息:" + e.getMessage());
        }

        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
            .withSeparator(',')
            .withQuoteChar('\'')
            .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }
}
