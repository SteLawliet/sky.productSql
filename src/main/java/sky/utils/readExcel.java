package sky.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sky.dao.DaoBean;

/**
 * Created by 赵子齐 on 17/12/30.
 */
public class readExcel {
    /**
     * 适用于第一行是标题行的excel，例如
     * 姓名   年龄  性别  身高
     * 张三   25  男   175
     * 李四   22  女   160
     * 每一行构成一个map，key值是列标题，value是列值。没有值的单元格其value值为null
     * 返回结果最外层的list对应一个excel文件，第二层的list对应一个sheet页，第三层的map对应sheet页中的一行
     *
     * @throws Exception
     */
    public static List<List<Map<String, String>>> read(String filepath) throws Exception {
        String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(filepath);

            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else {
                throw new Exception("读取的不是excel文件");
            }

            List<List<Map<String, String>>> result = new ArrayList<List<Map<String, String>>>();//对应excel文件

            int sheetSize = wb.getNumberOfSheets();
            for (int i = 0; i < sheetSize; i++) {//遍历sheet页
                Sheet sheet = wb.getSheetAt(i);
                List<Map<String, String>> sheetList = new ArrayList<Map<String, String>>();//对应sheet页

                List<String> titles = new ArrayList<String>();//放置所有的标题

                int rowSize = sheet.getLastRowNum() + 1;
                for (int j = 0; j < rowSize; j++) {//遍历行
                    Row row = sheet.getRow(j);
                    if (row == null) {//略过空行
                        continue;
                    }
                    int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列
                    if (j == 0) {//第一行是标题行
                        for (int k = 0; k < cellSize; k++) {
                            Cell cell = row.getCell(k);
                            titles.add(cell.toString());
                        }
                    } else {//其他行是数据行
                        LinkedHashMap<String, String> rowMap = new LinkedHashMap<>();//对应一个数据行
                        for (int k = 0; k < titles.size(); k++) {
                            Cell cell = row.getCell(k);
                            String key = titles.get(k);
                            String value = null;
                            if (cell != null) {
                                value = cell.toString();
                            }
                            rowMap.put(key, value);
                        }
                        sheetList.add(rowMap);
                    }
                }
                result.add(sheetList);
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    public static void main(String[] args) {
        List<List<Map<String, String>>> lists = null;
        try {
            lists = readExcel.read("/Users/zhaoziqi/Downloads/product-1/产品分类表0.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DaoBean daoBean = new DaoBean("product");
        daoBean.AddMap(lists.get(0));
    }
}
