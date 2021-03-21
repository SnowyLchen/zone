package com.cjl.basci.zone.common.utils.poi.xss;

import com.cjl.basci.zone.common.utils.poi.xss.title.GroupTitleStyle;
import com.cjl.basci.zone.common.utils.poi.xss.title.TableTitleStyle;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

/**
 * 样式构建器
 *
 * @author zhu
 */
public class XssStyleHandler {

    private BorderStyle defaultBorderStyle = BorderStyle.THIN;

    /**
     * 单行标题默认样式
     */
    private GroupStyle groupStyle = new GroupStyle() {
        @Override
        public XSSFCellStyle style(XSSFWorkbook workbook, XSSFRow row, Direction[] directions) {
            XSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(true);
            addBorderByDirection(style, directions);
            return style;
        }
    };
    /**
     * 单行内容默认样式
     */
    private GroupTitleStyle groupTitleStyle = new GroupTitleStyle() {
        @Override
        public XSSFCellStyle style(XSSFWorkbook workbook, XSSFRow row, Direction[] directions) {
            XSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            addBorderByDirection(style, directions);
            return style;
        }
    };
    ;
    /**
     * 表格标题默认样式
     */
    private TableStyle tableStyle = new TableStyle() {
        @Override
        public XSSFCellStyle style(XSSFWorkbook workbook, XSSFRow row, Direction[] directions) {
            return groupStyle.style(workbook, row, directions);
        }
    };
    /**
     * 表格内容默认样式
     */
    private TableTitleStyle tableTitleStyle = new TableTitleStyle() {
        @Override
        public XSSFCellStyle style(XSSFWorkbook workbook, XSSFRow row, Direction[] directions) {
            return groupTitleStyle.style(workbook, row, directions);
        }
    };

    /**
     * 标题样式默认样式
     */
    private TitleStyle titleStyle = new TitleStyle() {
        @Override
        public XSSFCellStyle style(XSSFWorkbook workbook, XSSFSheet sheet, int rowNum, int colspan, Direction[] directions) {
            XSSFCellStyle style = workbook.createCellStyle();
            // 合并站点格
            CellRangeAddress rangeAddress = new CellRangeAddress(rowNum, rowNum, 1, colspan);
            sheet.addMergedRegion(rangeAddress);

            // 字体
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = 1; i <= colspan; i++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row.getCell(i) == null) {
                    directions = new Direction[]{Direction.UP};
                    if (i == colspan) {
                        directions = ArrayUtils.add(directions, Direction.RIGHT);
                    }
                    addBorderByDirection(style, directions);
                    row.createCell(i).setCellStyle(style);
                } else {
                    addBorderByDirection(style, directions);
                    row.getCell(i).setCellStyle(style);
                }
            }
            return style;
        }
    };

    /**
     * 添加边框
     *
     * @param style      cell样式
     * @param directions
     */
    private void addBorderByDirection(XSSFCellStyle style, Direction[] directions) {
        for (Direction direction : directions) {
            if (direction == Direction.UP) {
                style.setBorderTop(defaultBorderStyle);
            }
            if (direction == Direction.LEFT) {
                style.setBorderLeft(defaultBorderStyle);
            }
            if (direction == Direction.RIGHT) {
                style.setBorderRight(defaultBorderStyle);
            }
            if (direction == Direction.DOWN) {
                style.setBorderBottom(defaultBorderStyle);
            }
        }
    }

//    /**
//     * 设置边框
//     *
//     * @param cell      目标站点格
//     * @param direction 方向
//     */
//    public void setBorder(XSSFWorkbook workbook, XSSFCell cell, Direction direction) {
//        XSSFCellStyle style = cell.getCellStyle();
//        if (direction == Direction.UP && cell.getCellStyle().getBorderTopEnum() != defaultBorderStyle) {
//            style.setBorderTop(defaultBorderStyle);
//        }
//        if (direction == Direction.LEFT && cell.getCellStyle().getBorderLeftEnum() != defaultBorderStyle) {
//            style.setBorderLeft(defaultBorderStyle);
//        }
//        if (direction == Direction.RIGHT && cell.getCellStyle().getBorderRightEnum() != defaultBorderStyle) {
//            style.setBorderRight(defaultBorderStyle);
//        }
//        if (direction == Direction.LEFT && cell.getCellStyle().getBorderLeftEnum() != defaultBorderStyle) {
//            style.setBorderLeft(defaultBorderStyle);
//        }
//        cell.setCellStyle(style);
//    }

    public GroupStyle getGroupStyle() {
        return groupStyle;
    }

    public GroupTitleStyle getGroupTitleStyle() {
        return groupTitleStyle;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public TableTitleStyle getTableTitleStyle() {
        return tableTitleStyle;
    }

    public TitleStyle getTitleStyle() {
        return titleStyle;
    }

    public XssStyleHandler(GroupStyle groupStyle, GroupTitleStyle groupTitleStyle, TableStyle tableStyle, TableTitleStyle tableTitleStyle, TitleStyle titleStyle) {
        this.groupStyle = groupStyle;
        this.groupTitleStyle = groupTitleStyle;
        this.tableStyle = tableStyle;
        this.tableTitleStyle = tableTitleStyle;
        this.titleStyle = titleStyle;
    }

    public XssStyleHandler(TitleStyle titleStyle) {
        this.titleStyle = titleStyle;
    }

    public XssStyleHandler(TableTitleStyle tableTitleStyle) {
        this.tableTitleStyle = tableTitleStyle;
    }

    public XssStyleHandler(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    public XssStyleHandler(GroupTitleStyle groupTitleStyle) {
        this.groupTitleStyle = groupTitleStyle;
    }

    public XssStyleHandler(GroupStyle groupStyle) {
        this.groupStyle = groupStyle;
    }

    public XssStyleHandler(TableStyle tableStyle, TableTitleStyle tableTitleStyle, TitleStyle titleStyle) {
        this.tableStyle = tableStyle;
        this.tableTitleStyle = tableTitleStyle;
        this.titleStyle = titleStyle;
    }

    public XssStyleHandler(GroupStyle groupStyle, GroupTitleStyle groupTitleStyle, TitleStyle titleStyle) {
        this.groupStyle = groupStyle;
        this.groupTitleStyle = groupTitleStyle;
        this.titleStyle = titleStyle;
    }

    public XssStyleHandler() {
    }
}
