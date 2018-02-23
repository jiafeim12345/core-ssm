package me.cloudcat.develop.excel;

import me.cloudcat.develop.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;

/**
 * 清洗数据
 *
 * @Author: zhenzhong.wang
 * @Time: 2017/12/25 14:22
 */
public class DataClean {

    @Test
    public void testOne() throws IOException, InvalidFormatException {
        ExcelUtils.dataClean("C:\\Users\\ZZWang\\Desktop\\2017上半年上线.xlsx");
    }
}
