package com.cjl.basic.zone.common.utils.poi;

import com.cjl.basic.zone.common.utils.poi.xss.Direction;
import com.cjl.basic.zone.common.utils.poi.xss.XssStyleHandler;
import com.cjl.basic.zone.framework.aspectj.lang.annotation.Excel;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.cjl.basic.zone.utils.StringUtils;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 模板导出
 *
 * @author huade_zhu
 */
public class ExportExcelTemplate<T> {

    /**
     * 导出表单对象
     *
     * @param data      实体对象
     * @param groupSize 固定或列
     * @return 对应名称
     */
    public AjaxResult exportExcel(String sheetName, T data, int groupSize, XssStyleHandler handler) {
        OutputStream out = null;
        XSSFWorkbook workbook = null;
        int rowIndex = 1;
        XSSFRow row = null;
        try {
            Class clazz = data.getClass();
            // 产生工作簿
            workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(0, sheetName);
            // 顶部空行
            sheet.createRow(0);

            Group single = null;
            List<Group> groups = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Excel attr = field.getAnnotation(Excel.class);
                if (field.getType() == List.class) {
                    groups.add(new Group(attr.name(), field.get(data)));
                    continue;
                }
                single = new Group(attr.name(), field.get(data));
            }

            Direction[] directions;
            if (single != null) {
                /*初始化基本部分*/
                rowIndex = initGroup(groupSize, handler, workbook, rowIndex, sheet, single);


            }

            /*初始化列表部分*/
            for (Group group : groups) {
                // 插入空行
                sheet.createRow(rowIndex++);

                row = sheet.createRow(rowIndex++);
                XSSFCell titleCell = row.createCell(1);
                titleCell.setCellValue(group.getTitle());
                boolean blnLoadTitle = false;
                List t = (List) group.getObject();
                for (int j = 0; j < t.size(); j++) {
                    Object object = t.get(j);
                    List<Field> fields = getFields(object.getClass().getDeclaredFields());

                    // 合并标题
                    if (!blnLoadTitle) {
                        directions = new Direction[]{Direction.UP, Direction.LEFT};
                        handler.getTitleStyle().style(workbook, sheet, rowIndex - 1, fields.size(), directions);

                        // 设置表头
                        row = sheet.createRow(rowIndex++);
                        // 空列
                        row.createCell(0);
                        for (int i = 0; i < fields.size(); i++) {
                            directions = new Direction[0];
                            Field field = fields.get(i);
                            Excel attr = field.getAnnotation(Excel.class);
                            if (i == 0) {
                                directions = new Direction[]{Direction.LEFT};
                            } else if (i == fields.size() - 1) {
                                directions = new Direction[]{Direction.RIGHT};
                            }
                            createCell(sheet, row, attr.name(), i + 1, attr.width(), attr.height(), handler.getTableTitleStyle().style(workbook, row, directions));
                        }
                        blnLoadTitle = true;
                    }

                    // 设置内容
                    row = sheet.createRow(rowIndex++);
                    // 空列
                    row.createCell(0);
                    for (int i = 0; i < fields.size(); i++) {
                        directions = new Direction[0];
                        Field field = fields.get(i);
                        field.setAccessible(true);
                        Excel attr = field.getAnnotation(Excel.class);
                        String colValue = "";
                        if (StringUtils.isNotEmpty(attr.dateFormat()) && field.get(object) != null) {
                            SimpleDateFormat format = new SimpleDateFormat(attr.dateFormat());
                            // 写入列名
                            colValue = format.format(field.get(object));
                        } else {
                            // 写入列名
                            colValue = String.valueOf(field.get(object));
                        }
                        if (StringUtils.isNotEmpty(attr.readConverterExp()) && field.get(object) != null) {
                            colValue = ExcelUtil.convertByExp(attr.readConverterExp(), colValue);
                        }
                        if (i == 0) {
                            directions = new Direction[]{Direction.LEFT};
                        } else if (i == fields.size() - 1) {
                            directions = new Direction[]{Direction.RIGHT};
                        }

                        if (t.size() - 1 == j) {
                            directions = ArrayUtils.add(directions, Direction.DOWN);
                        }
                        createCell(sheet, row, colValue, i + 1, attr.width(), attr.height(), handler.getTableStyle().style(workbook, row, directions));
                    }

                }
            }

            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(ExcelUtil.getAbsoluteFile(filename));
            workbook.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ObjectUtils.allNotNull(workbook)) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ObjectUtils.allNotNull(out)) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return AjaxResult.error("导出失败");
    }

    /**
     * 初始化单表部分
     *
     * @throws IllegalAccessException
     */
    private int initGroup(int groupSize, XssStyleHandler handler, XSSFWorkbook workbook, int rowIndex, XSSFSheet sheet, Group single) throws IllegalAccessException {

        XSSFRow row = sheet.createRow(rowIndex++);
        XSSFCell titleCell = row.createCell(1);
        titleCell.setCellValue(single.getTitle());
        Direction[] directions = new Direction[]{Direction.UP, Direction.LEFT};
        handler.getTitleStyle().style(workbook, sheet, rowIndex - 1, (groupSize * 2), directions);

        XSSFCell cell;
        // 得到所有字段
        Field[] allFields = single.getObject().getClass().getDeclaredFields();
        List<Field> fields = getFields(allFields);
        int cellIndex = 1;
        for (Field field : fields) {
            directions = new Direction[0];
            // 下边框
            int borderBottomRowIndex = (int) Math.ceil((double) fields.size() / (double) groupSize) + 2;
            // 每次换行重置列，偏向左偏移一列
            if ((cellIndex - 1) % (groupSize * 2) == 0) {
                row = sheet.createRow(rowIndex++);
                // 空列
                row.createCell(0);
                cellIndex = 1;
            }

            Excel attr = field.getAnnotation(Excel.class);
            // 左边框
            if (borderBottomRowIndex == rowIndex) {
                directions = new Direction[]{Direction.DOWN};
            }
            if (cellIndex == 1) {
                directions = ArrayUtils.add(directions, Direction.LEFT);
            }
            createCell(sheet, row, attr.name(), cellIndex++, attr.width(), attr.height(), handler.getGroupTitleStyle().style(workbook, row, directions));

            field.setAccessible(true);
            String colValue = "";
            if (StringUtils.isNotEmpty(attr.dateFormat())) {
                SimpleDateFormat format = new SimpleDateFormat(attr.dateFormat());
                // 写入列名
                colValue = format.format(field.get(single.getObject()));
            } else {
                // 写入列名
                colValue = String.valueOf(field.get(single.getObject()));
            }

            // 右边框
            directions = new Direction[0];
            if (cellIndex == groupSize * 2) {
                directions = ArrayUtils.add(directions, Direction.RIGHT);
            }
            if (borderBottomRowIndex == rowIndex) {
                directions = new Direction[]{Direction.DOWN};
            }
            createCell(sheet, row, colValue, cellIndex++, attr.width(), attr.height(), handler.getGroupStyle().style(workbook, row, directions));

            if (borderBottomRowIndex == rowIndex) {
                int nullRows = fields.size() % (borderBottomRowIndex - 2) - 1;
                for (int i = 0; i < nullRows; i++) {
                    if (cellIndex == groupSize * 2) {
                        directions = ArrayUtils.add(directions, Direction.RIGHT);
                    }
                    row.createCell(cellIndex++).setCellStyle(handler.getGroupStyle().style(workbook, row, directions));
                }
            }
        }
        return rowIndex;
    }

    /**
     * 获取所有导出字段
     *
     * @return
     */
    @NotNull
    private List<Field> getFields(Field[] allFields) {
        List<Field> fields = new ArrayList<>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Excel.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * 创建列
     */
    public XSSFCell createCell(XSSFSheet sheet, XSSFRow row, String name, int index, double width, double height, XSSFCellStyle style) {
        XSSFCell cell = row.createCell(index);
        // 写入列名
        cell.setCellValue(name.equals("null") ? "" : name);
        // 设置列宽
        sheet.setColumnWidth(index, (int) ((width + 0.72) * 256));
        row.setHeight((short) (height * 20));
        // 设置样式
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 设置站点格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public static void setHSSFPrompt(XSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
                                     int endRow, int firstCol, int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
        dataValidationView.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(dataValidationView);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static XSSFSheet setHSSFValidation(XSSFSheet sheet, String[] textlist, int firstRow, int endRow,
                                              int firstCol, int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个站点格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidationList);
        return sheet;
    }

    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }
}
