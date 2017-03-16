package Image_OSS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class content_encode {
	 public static void main(String[] args) 
	    {        
	        getDataFromExcel2("E:"+ File.separator +"Theme.xlsx");
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
	                if (getRightTypeCell(cell).toString().equals("CONTENT"))
	                {
	                    headMap.put("CONTENT", flag);
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
	        String CONTENT = "";
	        
	        if(0 == totalRowNum)
	        {
	            System.out.println("Excel内没有数据！");
	        }
	        
	        Cell cell_1 = null,cell_2 = null;
	        
	        //测试图片路径解码
	        for(int i = 1 ; i <= totalRowNum ; i++)
	        {
	            //获得第i行对象
	            Row row = sheet.getRow(i);
	            
	            try
	            {
	                cell_1 = row.getCell(headMap.get("ID"));
	                cell_2 = row.getCell(headMap.get("CONTENT"));
	            } catch (Exception e)
	            {
	                e.printStackTrace();
	                System.out.println("获取单元格错误");
	            }
	            
	            try
	            {
	                //ID
	                ID = (String) getRightTypeCell(cell_1);
	                //CONTENT
	                String CONTENT1 = (String) getRightTypeCell(cell_2);
	                CONTENT = urldecode(CONTENT1);
	                
	            } catch (ClassCastException e)
	            {
	                e.printStackTrace();
	                System.out.println("数据不全是数字或全部是文字!");
	            }
	            System.out.println("ID："+ID+",\t\t CONTENT："+CONTENT);
	            
	        }
	    }
    
	    
/**
 *Base64编码解码
 */
	    public static String urldecode(String path) {
			// TODO Auto-generated method stub
	    	try{
	    		path = java.net.URLDecoder.decode(path,"UTF-8");
	    	}catch (UnsupportedEncodingException e) {
	    		e.printStackTrace();
	    	}
			return path;
		}  
 
}
