package com.cjl.basic.zone.common.utils.poi.xss;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author zhu
 */
public interface Style {
    /**
     * 自定义样式
     *
     * @param workbook 工作簿
     * @return
     */
    XSSFCellStyle style(XSSFWorkbook workbook, XSSFRow row, Direction[] directions);
}
