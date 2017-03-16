package Image_OSS;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.record.ScenarioProtectRecord;

public class html_img {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String img = "";
		img = getImgSrc(img);
	}
	
	public static String getImgSrc(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
//		 String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			// Matcher m =
			// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				System.out.println(img);
			}
		}
		return img;
	}
}
