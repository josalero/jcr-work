package com.amex.srt.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.core.util.StringUtils;

/**
 * Utility class that will scrub out Microsoft formatted rich text symbols that
 * munk data everywhere
 * 
 * @author christopher.tai@starcomworldwide.com
 */
public class MicrosoftStringUtils {
	private static Log log = LogFactory.getLog(MicrosoftStringUtils.class);

	/**
	 * Scrubs input string and pulls out only ascii characters, converting the
	 * pretty Microsoft quotes into real quotes, and turning non-standard ascii
	 * characters into their entity equivalent.
	 * 
	 * @param input
	 * @return String
	 */
	public static String scrub(String input) {
		if (StringUtils.isEmpty(input)) {
			return input;
		}
		input = input.replaceAll("&rsquo;", "'");
		input = input.replaceAll("â€™", "'");
		input = input.replaceAll("â", "'");
		StringBuilder output = new StringBuilder();
		char[] chars = input.toCharArray();

		for (int i = 0; i < chars.length; ++i) {
			char character = chars[i];
			int ascii = (int) character;

			if (character > 0 && character < 127) {
				output.append(character);
			} else {
				switch (ascii) {
				case 38:
					output.append("&#38;");
					break; // &
				case 146:
					output.append("'");
					break;
				case 147:
					output.append("\"");
					break;
				case 148:
					output.append("\"");
					break;
				case 153:
					output.append("&#153;");
					break; // TM
				case 169:
					output.append("&#169;");
					break; // ©
				case 174:
					output.append("&#174;");
					break; // ®
				// damn Microsoft Word quotes!
				case 8216:
					output.append("'");
					break;
				case 8217:
					output.append("'");
					break;
				case 8220:
					output.append("\"");
					break;
				case 8221:
					output.append("\"");
					break;
				case ((int) '\u201E'):
					output.append('"');
					break; // double low quotation mark
				case ((int) '\u2039'):
					output.append('\'');
					break; // Single Left-Pointing Quotation Mark
				case ((int) '\u203A'):
					output.append('\'');
					break; // Single right-Pointing Quotation Mark
				case ((int) '\u02DC'):
					output.append('~');
					break; // Small Tilde
				case ((int) '\u2013'):
					output.append('-');
					break; // En Dash
				case ((int) '\u2014'):
					output.append('-');
					break; // EM Dash
				default:
					break;
				}
			}
		}
		return output.toString();
	}
}
