package com.cjl.basci.zone.common.utils.poi.xss;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author huade_zhu
 */
public interface TitleStyle {
    /**
     * 自定义样式
     *
     * @param workbook 工作簿
     * @return
     */
    XSSFCellStyle style(XSSFWorkbook workbook, XSSFSheet sheet, int rowNum, int colspan, Direction[] directions);
}
