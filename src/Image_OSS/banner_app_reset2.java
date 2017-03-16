package Image_OSS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.FinanceFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class banner_app_reset2 {
	 public static void main(String[] args) 
	    {        
	        getDataFromExcel2("E:"+ File.separator +"class.xlsx");
	    }
	
	/**
     * 读取出filePath中的所有数据信息
     * @param filePath excel文件的绝对路径
     * 
     */
	 /**
	     *     
	     * @param cell 一个单元格的对象
	     * @return 返回该单元格相应的类型的值
	     */
	    public static Object getRightTypeCell(Cell cell){
	    
	        Object object = null;
	        switch(cell.getCellType())
	        {
	            case Cell.CELL_TYPE_STRING :
	            {
	                object=cell.getStringCellValue();
	                break;
	            }
	            case Cell.CELL_TYPE_NUMERIC :
	            {
	                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                object=cell.getNumericCellValue();
	                break;
	            }
	                
	            case Cell.CELL_TYPE_FORMULA :
	            {
	                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                object=cell.getNumericCellValue();
	                break;
	            }
	            
	            case Cell.CELL_TYPE_BLANK :
	            {
	                cell.setCellType(Cell.CELL_TYPE_BLANK);
	                object=cell.getStringCellValue();
	                break;
	            }
	        }
	        return object;
	    }    
	    
	    
	    
	    /**
	     * 读取出filePath中的所有数据信息
	     * @param filePath excel文件的绝对路径
	     * 
	     */
	    
	    public static void getDataFromExcel2(String filePath)
	    {
	        List<Map<String,Integer>> list = new ArrayList<Map<String, Integer>>();
	        //判断是否为excel类型文件
	        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx"))
	        {
	            System.out.println("文件不是excel类型");
	        }
	        
	        FileInputStream fis =null;
	        Workbook wookbook = null;
	        int flag = 0;
	        
	        try
	        {
	            //获取一个绝对地址的流
	              fis = new FileInputStream(filePath);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	       
	        try 
	        {
	            //2003版本的excel，用.xls结尾
	            wookbook = new HSSFWorkbook(fis);//得到工作簿
	             
	        } 
	        catch (Exception ex) 
	        {
	            //ex.printStackTrace();
	            try
	            {
	                //这里需要重新获取流对象，因为前面的异常导致了流的关闭—————————————————————————————加了这一行
	                 fis = new FileInputStream(filePath);
	                //2007版本的excel，用.xlsx结尾
	                
	                wookbook = new XSSFWorkbook(filePath);//得到工作簿
	            } catch (IOException e)
	            {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        
	        //得到一个工作表
	        Sheet sheet = wookbook.getSheetAt(0);
	        
	        //获得表头
	        Row rowHead = sheet.getRow(0);
	        
	      //根据不同的data放置不同的表头
	        Map<Object,Integer> headMap = new HashMap<Object, Integer>();
	        
	        
	        //判断表头是否合格  ------------------------这里看你有多少列
	        if(rowHead.getPhysicalNumberOfCells() != 2)
	        {
	            System.out.println("表头列数与要导入的数据库不对应");
	        }
	        
	        try
	        {
	            //----------------这里根据你的表格有多少列
	            while (flag < 2)
	            {
	                Cell cell = rowHead.getCell(flag);
	                if (getRightTypeCell(cell).toString().equals("ID"))
	                {
	                    headMap.put("ID", flag);
	                }
	                if (getRightTypeCell(cell).toString().equals("IMAGE"))
	                {
	                    headMap.put("IMAGE", flag);
	                }
	                flag++;
	            }
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	            System.out.println("表头不合规范，请修改后重新导入");
	        }
	        
	        
	        //获得数据的总行数
	        int totalRowNum = sheet.getLastRowNum();
	        
	        
	        
	        //要获得属性
	        String ID = "";
	        Set<String> IMAGE = null ;
	        
	        if(0 == totalRowNum)
	        {
	            System.out.println("Excel内没有数据！");
	        }
	        
	        Cell cell_1 = null;
	        Cell cell_2 = null;
	        
	        //测试图片路径解码
	        for(int i = 1 ; i <= totalRowNum ; i++)
	        {
	            //获得第i行对象
	            Row row = sheet.getRow(i);
	            
	            try
	            {
	                cell_1 = row.getCell(headMap.get("ID"));
	                cell_2 = row.getCell(headMap.get("IMAGE"));
	            } catch (Exception e)
	            {
	                e.printStackTrace();
	                System.out.println("获取单元格错误");
	            }
	            
	            try
	            {
	                //ID
	                ID = (String) getRightTypeCell(cell_1);
	                ID = ID.replaceAll("A", "");
	                //PATH
	                String IMAGE_PATH = (String) getRightTypeCell(cell_2);
	                if(null != IMAGE_PATH){
	                	IMAGE = getImgStr(IMAGE_PATH);
	                }	                
	                //IMAGE = "http://img.moehot.com/images/"+ IMAGE;
	                
	            } catch (ClassCastException e)
	            {
	                e.printStackTrace();
	                System.out.println("数据不全是数字或全部是文字!");
	            }
	            //System.out.println(ID+"\t"+IMAGE);
	            
	        }
	    }
	    
	    
	    
	    
	    
/**
 *Base64编码解码
 */   
	    /**
	     * 得到网页中图片的地址
	     */
	    public static Set<String> getImgStr(String htmlStr) {
	        Set<String> pics = new HashSet<>();
	        String img = "";
	        Pattern p_image;
	        Matcher m_image;
	        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
	        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
	        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
	        m_image = p_image.matcher(htmlStr);
	        while (m_image.find()) {
	            // 得到<img />数据
	            img = m_image.group();
	            // 匹配<img>中的src数据
	            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
	            while (m.find()) {
//	            	Matcher m2 = Pattern.compile("/storage/files///s*=\\s*\")
	                pics.add(m.group(1));
	                htmlStr = htmlStr.replace(m.group(1), decodePath(m.group(1)));
	            }
	        }
	        //System.out.println(htmlStr);
	        Wirtefile(htmlStr);
	        return pics;
	    }
	    
	    
	    public static final String ENCODE_PREFIX = "ILS";
	    public static final String ENCODE_PREFIX_1 = "FLS";
	    public static final String ENCODE_SEPARATOR = "!!";
	    public static final String HEAD = "<img src=";
	    /*public static String encodePath(String path) {
	    	path = path.replace("http://139.196.84.154/am-admin/storage/files/", "");
	    	path = path.replace("http://121.40.102.225:6060/am/storage/files/", "");
	    	path = path.replace("/am-admin/storage/files/", "");
	    	path = path.replace("/am/storage/files/", "");
	    	path = path.replace("/am-v23/storage/files/", "");
	    	path = path.replace("/am-v24/storage/files/", "");
	    	
	    	
	    	if(path.startsWith(ENCODE_PREFIX)){
	    		path = path.replace(ENCODE_PREFIX, "");
	    		path = !path.endsWith("/") ? path : path.substring(0, path.length() - 1);
		        int i = path.lastIndexOf("/");
		        if (-1 < i) {
		            path = Base64.encodeBase64URLSafeString(path.substring(0, i).getBytes(Charsets.UTF_8)) + "!!" + path.substring(i + 1);
		            
		        }
	    	} 
	        return path;
	    }*/
	    
	    public static String decodePath(String encodedPath) {
	    	encodedPath = encodedPath.replace("http://139.196.84.154/am-admin/storage/files/", "/am-admin-test/storage/files/");
	    	encodedPath = encodedPath.replace("http://121.40.102.225:6060/am/storage/files/", "/am-admin-test/storage/files/");
	    	encodedPath = encodedPath.replace("/am-admin/storage/files/", "/am-admin-test/storage/files/");
	    	encodedPath = encodedPath.replace("/am/storage/files/", "/am-admin-test/storage/files/");
	    	encodedPath = encodedPath.replace("/am-v23/storage/files/", "/am-admin-test/storage/files/");
	    	encodedPath = encodedPath.replace("/am-v24/storage/files/", "/am-admin-test/storage/files/");
	        int i = encodedPath.indexOf(ENCODE_SEPARATOR);
	        if (-1 < i && encodedPath.startsWith(ENCODE_PREFIX_1)) {
	            String fname = encodedPath.substring(i + 2);
	            encodedPath = encodedPath.substring(ENCODE_PREFIX_1.length(), i);
	            encodedPath = new String(Base64.decodeBase64(encodedPath), Charsets.UTF_8) + "/" + fname;
	            encodedPath = encodedPath.replaceAll(".jpg", "");
	            encodedPath = encodedPath.replaceAll(".png", "");
	            encodedPath = encodedPath.replaceAll(".gif", "");
	            encodedPath = "http://img.moehot.com/files/" + encodedPath;
	        }
	        
	        encodedPath = encodedPath.replace("http://139.196.84.154/am-admin/storage/images/", "/am-admin-test/storage/images/");
	    	encodedPath = encodedPath.replace("http://121.40.102.225:6060/am/storage/images/", "/am-admin-test/storage/images/");
	    	encodedPath = encodedPath.replace("/am-admin/storage/images/", "/am-admin-test/storage/images/");
	    	encodedPath = encodedPath.replace("/am/storage/images/", "/am-admin-test/storage/images/");
	    	encodedPath = encodedPath.replace("/am-v23/storage/images/", "/am-admin-test/storage/images/");
	    	encodedPath = encodedPath.replace("/am-v24/storage/images/", "/am-admin-test/storage/images/");
	        i = encodedPath.indexOf(ENCODE_SEPARATOR);
	        if (-1 < i && encodedPath.startsWith(ENCODE_PREFIX)) {
	            String fname = encodedPath.substring(i + 2);
	            encodedPath = encodedPath.substring(ENCODE_PREFIX.length(), i);
	            encodedPath = new String(Base64.decodeBase64(encodedPath), Charsets.UTF_8) + "/" + fname;
	            encodedPath = encodedPath.replaceAll(".jpg", "");
	            encodedPath = encodedPath.replaceAll(".png", "");
	            encodedPath = encodedPath.replaceAll(".gif", "");
	            encodedPath = "http://img.moehot.com/images/" + encodedPath;
	        }
	        

	        return encodedPath;
	    }
	    
	    public static void Wirtefile(String data) {
	    		FileWriter fw = null;
	    		//如果文件存在，则追加内容；如果文件不存在，则创建文件
	    		try {
	    			//如果文件存在，则追加内容；如果文件不存在，则创建文件
	    			File f=new File("E:\\class_content.txt");
	    			fw = new FileWriter(f, true);
	    			} catch (IOException e) {
	    			e.printStackTrace();
	    			}
	    			PrintWriter pw = new PrintWriter(fw);
	    			pw.println(data);
	    			pw.flush();
	    			try {
	    			fw.flush();
	    			pw.close();
	    			fw.close();
	    			} catch (IOException e) {
	    			e.printStackTrace();
	    			}
		}
   
}
