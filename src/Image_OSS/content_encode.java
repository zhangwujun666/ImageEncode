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
     * ��ȡ��filePath�е�����������Ϣ
     * @param filePath excel�ļ��ľ���·��
     * 
     */
	 /**
	     *     
	     * @param cell һ����Ԫ��Ķ���
	     * @return ���ظõ�Ԫ����Ӧ�����͵�ֵ
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
	     * ��ȡ��filePath�е�����������Ϣ
	     * @param filePath excel�ļ��ľ���·��
	     * 
	     */
	    
	    public static void getDataFromExcel2(String filePath)
	    {
	        List<Map<String,Integer>> list = new ArrayList<Map<String, Integer>>();
	        //�ж��Ƿ�Ϊexcel�����ļ�
	        if(!filePath.endsWith(".xls")&&!filePath.endsWith(".xlsx"))
	        {
	            System.out.println("�ļ�����excel����");
	        }
	        
	        FileInputStream fis =null;
	        Workbook wookbook = null;
	        int flag = 0;
	        
	        try
	        {
	            //��ȡһ�����Ե�ַ����
	              fis = new FileInputStream(filePath);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	       
	        try 
	        {
	            //2003�汾��excel����.xls��β
	            wookbook = new HSSFWorkbook(fis);//�õ�������
	             
	        } 
	        catch (Exception ex) 
	        {
	            //ex.printStackTrace();
	            try
	            {
	                //������Ҫ���»�ȡ��������Ϊǰ����쳣���������Ĺرա���������������������������������������������������������������һ��
	                 fis = new FileInputStream(filePath);
	                //2007�汾��excel����.xlsx��β
	                
	                wookbook = new XSSFWorkbook(filePath);//�õ�������
	            } catch (IOException e)
	            {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        
	        //�õ�һ��������
	        Sheet sheet = wookbook.getSheetAt(0);
	        
	        //��ñ�ͷ
	        Row rowHead = sheet.getRow(0);
	        
	      //���ݲ�ͬ��data���ò�ͬ�ı�ͷ
	        Map<Object,Integer> headMap = new HashMap<Object, Integer>();
	        
	        
	        //�жϱ�ͷ�Ƿ�ϸ�  ------------------------���￴���ж�����
	        if(rowHead.getPhysicalNumberOfCells() != 2)
	        {
	            System.out.println("��ͷ������Ҫ��������ݿⲻ��Ӧ");
	        }
	        
	        try
	        {
	            //----------------���������ı���ж�����
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
	            System.out.println("��ͷ���Ϲ淶�����޸ĺ����µ���");
	        }
	        
	        
	        //������ݵ�������
	        int totalRowNum = sheet.getLastRowNum();
	        
	        
	        
	        //Ҫ�������
	        String ID = "";
	        String CONTENT = "";
	        
	        if(0 == totalRowNum)
	        {
	            System.out.println("Excel��û�����ݣ�");
	        }
	        
	        Cell cell_1 = null,cell_2 = null;
	        
	        //����ͼƬ·������
	        for(int i = 1 ; i <= totalRowNum ; i++)
	        {
	            //��õ�i�ж���
	            Row row = sheet.getRow(i);
	            
	            try
	            {
	                cell_1 = row.getCell(headMap.get("ID"));
	                cell_2 = row.getCell(headMap.get("CONTENT"));
	            } catch (Exception e)
	            {
	                e.printStackTrace();
	                System.out.println("��ȡ��Ԫ�����");
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
	                System.out.println("���ݲ�ȫ�����ֻ�ȫ��������!");
	            }
	            System.out.println("ID��"+ID+",\t\t CONTENT��"+CONTENT);
	            
	        }
	    }
    
	    
/**
 *Base64�������
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
