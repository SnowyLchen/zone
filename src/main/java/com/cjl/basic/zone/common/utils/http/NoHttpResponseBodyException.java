package com.cjl.basic.zone.common.utils.http;

/**
 * @author hd_zhu
 */
public class NoHttpResponseBodyException extends Exception {
    private static final long serialVersionUID = -7658940387386078766L;

    public NoHttpResponseBodyException(String message) {
        super(clean(message));
    }

    private static String clean(String message) {
        char[] chars = message.toCharArray();

        int i;
        for (i = 0; i < chars.length && chars[i] >= ' '; ++i) {
        }

        if (i == chars.length) {
            return message;
        } else {
            StringBuilder builder = new StringBuilder(chars.length * 2);

            for (i = 0; i < chars.length; ++i) {
                char ch = chars[i];
                if (ch < ' ') {
                    builder.append("[0x");
                    String hexString = Integer.toHexString(i);
                    if (hexString.length() == 1) {
                        builder.append("0");
                    }

                    builder.append(hexString);
                    builder.append("]");
                } else {
                    builder.append(ch);
                }
            }

            return builder.toString();
        }
    }
}
