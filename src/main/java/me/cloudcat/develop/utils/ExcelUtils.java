package me.cloudcat.develop.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * excel表格处理工具
 * @Author: zhenzhong.wang
 * @Time: 2017/12/25 13:34
 */
public class ExcelUtils {

    /**
     * 得到Excel，并解析内容，对2007及以上版本 使用XSSF解析
     * @param filePath
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void dataClean(String filePath) throws IOException, InvalidFormatException {
        // 通过流创建XSSF WorkBook
        InputStream ins=new FileInputStream(new File(filePath));
        XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(ins);
        // workbook加入写工作流
        OutputStream out = new FileOutputStream(new File(filePath));

        // 目标sheet初始化
        Sheet defSheet = wb.getSheetAt(1);
        Row defRow0 = defSheet.createRow(0);
        defRow0.createCell(0).setCellValue("密保手机");
        defRow0.createCell(1).setCellValue("对公账户");

        Sheet sourceSheet = wb.getSheetAt(0);
        // 总行数
        int totalRow = sourceSheet.getLastRowNum() + 1;
        // 遍历sourceSheet中的每个单元格，从第二行开始
        for (int i = 1; i < totalRow; i++) {
            Row row = sourceSheet.getRow(i);
            if (row != null) {
                Row defRow = defSheet.createRow(i);
                // 获取第一列
                Cell cell = row.getCell(0);
                if (cell != null) {
                    String value = cell.getStringCellValue();
                    if (StringUtils.isNotBlank(value)) {
                        String phone = "";
                        String account = "";
                        // 数据清洗
                        // 获取手机号
                        int po1 = value.indexOf("密保");
                        if (po1 >= 0) {
                            for (int p = po1; p < po1 + 10; p++) {
                                char c = value.charAt(p);
                                if (Character.isDigit(c)) {
                                    phone = value.substring(p, p + 11);
                                    break;
                                }
                            }
                        }
                        int start , end;
                        // 获取公司名
                        int po2 = value.indexOf("有限公司");
                        end = po2+4;
                        if (po2 < 0) {
                            po2 = value.indexOf("事务所");
                            end = po2+3;
                        }
                        if (po2 < 0) {
                            po2 = value.indexOf("经营部");
                            end = po2+3;
                        }
                        start = po2-20;

                        if (po2 > 0){
                            if (start <= 0) {
                                start = 0 ;
                            }
                            account = value.substring(start, end);
                            if (account.indexOf("上海") < 0){
                                account = "";
                            }
                            if (StringUtils.isNotBlank(account)) {
                                // 剔除脏字符
                                for (int num = 0; num< 2; num++) {
                                    if (account.lastIndexOf("&&") >= 0) {
                                        account = account.substring(account.indexOf("&&") + 2, account.length());
                                    }
                                    if (account.lastIndexOf("&") >= 0) {
                                        account = account.substring(account.indexOf("&") + 1, account.length());
                                    }
                                    if (account.lastIndexOf(" ") >= 0) {
                                        account = account.substring(account.indexOf(" ") + 1, account.length());
                                    }
                                    if (account.lastIndexOf("：") >= 0) {
                                        account = account.substring(account.indexOf("：") + 1, account.length());
                                    }
                                    if (account.lastIndexOf(":") >= 0) {
                                        account = account.substring(account.indexOf(":") + 1, account.length());
                                    }
                                    if (account.lastIndexOf("，") >= 0) {
                                        account = account.substring(account.indexOf("，") + 1, account.length());
                                    }
                                    if (account.lastIndexOf("。") >= 0) {
                                        account = account.substring(account.indexOf("。") + 1, account.length());
                                    }
                                    if (account.lastIndexOf("名字") >= 0) {
                                        account = account.substring(account.indexOf("名字") + 2, account.length());
                                    }
                                }
                            }
                        }
//                        System.out.println(phone + "    " + account);
                        // 写入值
                        defRow.createCell(0).setCellValue(phone);
                        defRow.createCell(1).setCellValue(account);
                    }
                }
            }
        }
        wb.write(out);
        out.flush();
        out.close();
        wb.close();
        ins.close();
    }


    /**
     * 创建Excel，并写入内容
     */
    public static void CreateExcel(){

        //1.创建Excel工作薄对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //2.创建Excel工作表对象
        HSSFSheet sheet = wb.createSheet("new Sheet");
        //3.创建Excel工作表的行
        HSSFRow row = sheet.createRow(6);
        //4.创建单元格样式
        CellStyle cellStyle =wb.createCellStyle();
        // 设置这些样式
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);



        //5.创建Excel工作表指定行的单元格
        row.createCell(0).setCellStyle(cellStyle);
        //6.设置Excel工作表的值
        row.createCell(0).setCellValue("aaaa");

        row.createCell(1).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("bbbb");


        //设置sheet名称和单元格内容
        wb.setSheetName(0,"第一张工作表");
        //设置单元格内容   cell.setCellValue("单元格内容");

        // 最后一步，将文件存到指定位置
        try
        {
            FileOutputStream fout = new FileOutputStream("E:/students.xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
