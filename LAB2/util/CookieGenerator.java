package util;

public class CookieGenerator {
    private static final long RUNTIME_START = System.currentTimeMillis();

    public static String genCookie (long threadID){
        StringBuilder cookieStr = new StringBuilder();
        cookieStr.append("SILLY_COOKIE: ");
        cookieStr.append(stringifyLong(RUNTIME_START));
        cookieStr.append(threadID);
        return cookieStr.toString();
    }

    private static String stringifyLong (long nr){
        byte toAdd = 65 - 48;
        char[] numsChar = String.valueOf(nr).toCharArray();
        for (int i = 0; i < numsChar.length; i++){
            numsChar[i] += (char) toAdd;
        }
        return String.valueOf(numsChar);
    }
}
