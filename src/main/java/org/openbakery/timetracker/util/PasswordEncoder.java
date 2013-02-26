package org.openbakery.timetracker.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {

	public static String encode(String password) {
		byte[] hash = DigestUtils.sha(password);
		return Base64.encodeBase64String(hash);
	}

}
