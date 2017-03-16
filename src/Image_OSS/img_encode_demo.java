package Image_OSS;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

public class img_encode_demo {

	public static void main(String[] args) {
		final String ENCODE_PREFIX = "ILS";
	    final String ENCODE_SEPARATOR = "!!";
		// TODO Auto-generated method stub
		String encodedPath = "ILSMjAxNy8wMi8xNS8wMDAw!!993f81991261416d963a3657fc6a2d51.jpg";
		int i = encodedPath.indexOf(ENCODE_SEPARATOR);
        if (-1 < i && encodedPath.startsWith(ENCODE_PREFIX)) {
            String fname = encodedPath.substring(i + 2);
            encodedPath = encodedPath.substring(ENCODE_PREFIX.length(), i);
            encodedPath = new String(Base64.decodeBase64(encodedPath), Charsets.UTF_8) + "/" + fname;
        }

        System.out.println(encodedPath);
	}

}
