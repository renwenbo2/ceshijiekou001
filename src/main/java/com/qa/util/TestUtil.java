package com.qa.util;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.qa.base.TestBase;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TestUtil extends TestBase {
    //获取返回的token ,使用JsonPath获取json路径
    public static HashMap<String, String> getToken(CloseableHttpResponse closeableHttpResponse, String jsonPath) throws Exception {
        HashMap<String, String> responseToken = new HashMap<String, String>();
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        ReadContext ctx = JsonPath.parse(responseString);
        String Token = ctx.read(jsonPath); //"$.EFPV3AuthenticationResult.Token"
        if (null == Token || "".equals(Token)) {
            new Exception("token不存在");
        }

        responseToken.put("x-ba-token", Token);
        return responseToken;
    }


    //遍历excel
    public static Object[][] dtt(String filePath) throws IOException {

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheetAt(0);
        int numberrow = sh.getPhysicalNumberOfRows();
        int numbercell = sh.getRow(0).getLastCellNum();

        Object[][] dttData = new Object[numberrow][numbercell];
        for (int i = 0; i < numberrow; i++) {
            if (null == sh.getRow(i) || "".equals(sh.getRow(i))) {
                continue;
            }
            for (int j = 0; j < numbercell; j++) {
                if (null == sh.getRow(i).getCell(j) || "".equals(sh.getRow(i).getCell(j))) {
                    continue;
                }
                XSSFCell cell = sh.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                dttData[i][j] = cell.getStringCellValue();
            }
        }

        return dttData;
    }

    //遍历excel，sheet参数
    public static Object[][] dtt(String filePath, int sheetId) throws IOException {

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh = wb.getSheetAt(sheetId);
        int numberrow = sh.getPhysicalNumberOfRows();
        int numbercell = sh.getRow(0).getLastCellNum();

        Object[][] dttData = new Object[numberrow][numbercell];
        for (int i = 0; i < numberrow; i++) {
            if (null == sh.getRow(i) || "".equals(sh.getRow(i))) {
                continue;
            }
            for (int j = 0; j < numbercell; j++) {
                if (null == sh.getRow(i).getCell(j) || "".equals(sh.getRow(i).getCell(j))) {
                    continue;
                }
                XSSFCell cell = sh.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                dttData[i][j] = cell.getStringCellValue();
                System.out.println(dttData[i][j] = cell.getStringCellValue());
            }
        }


        return dttData;
    }

    //获取状态码
    public static int getStatusCode(CloseableHttpResponse closeableHttpResponse) {
        int StatusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        return StatusCode;
    }

    //获取boby
    public static String getEntity(CloseableHttpResponse closeableHttpResponse) throws IOException {
        HttpEntity entity = closeableHttpResponse.getEntity();
        String entity1 = EntityUtils.toString(entity);
        if (entity1 == null || "".equals(entity1)) {
            new Exception("token不存在");
        }
        return entity1;

    }

    //配置请求头
    public static Map getheader() {
        TestBase testBase = new TestBase();
        Map<String, String> map = new HashMap<String, String>();
        map.put(testBase.prop.getProperty("App_codename"), testBase.prop.getProperty("App_code"));
        map.put(testBase.prop.getProperty("content_type_name"), testBase.prop.getProperty("content_type_json"));
        map.put(testBase.prop.getProperty("charset_name"), testBase.prop.getProperty("charset_utf"));
        return map;
    }


}

