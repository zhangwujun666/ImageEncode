package Image_OSS;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

public class encode_demo {

	public static void main(String[] args) {
		final String ENCODE_PREFIX = "FLS";
	    final String ENCODE_SEPARATOR = "!!";
		// TODO Auto-generated method stub
		String encodedPath = "FLSMjAxNy8wMi8xMC8wMDAy!!8386c55a2bb14cceaedc3e5ba7ebb920.apk";
		int i = encodedPath.indexOf(ENCODE_SEPARATOR);
        if (-1 < i && encodedPath.startsWith(ENCODE_PREFIX)) {
            String fname = encodedPath.substring(i + 2);
            encodedPath = encodedPath.substring(ENCODE_PREFIX.length(), i);
            encodedPath = new String(Base64.decodeBase64(encodedPath), Charsets.UTF_8) + "/" + fname;
        }

        System.out.println(encodedPath);
	}
}
