package online.zhaopei.mqoperation.utils;

import org.apache.commons.logging.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static void logError(Log log, Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        log.error(sw.toString());
    }

    public static String removeAnd(String sql) {
        String patternStr = "where.{1,}and";
        Pattern r = Pattern.compile(patternStr);
        Matcher m = r.matcher(sql);
        return m.replaceAll("where ");
    }
}
