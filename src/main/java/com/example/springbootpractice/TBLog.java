package com.hexlant.tb.wallet.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 태그 설계
 * 1. 파일 쓰기 기능 날짜별로
 * 2. v, d, i, w, e 기능 정리
 * <p>
 * 로그의 우선순위
 * 우선순위는 다음 값 중 하나입니다.
 * V — Verbose (가장 낮은 우선순위)
 * D — Debug
 * I — Info
 * W — Warning
 * E — Error
 * A — Assert
 * <p>
 * Log level 메뉴에서 다음 값 중 하나를 선택합니다.
 * Verbose - 모든 로그 메시지를 표시합니다(기본 설정).
 * Debug - 개발 중에만 유용한 디버그 로그 메시지뿐 아니라 더 낮은 레벨의 메시지도 이 목록에 표시합니다.
 * Info - 일반적인 사용에 대해 예상할 수 있는 로그 메시지뿐 아니라 더 낮은 레벨의 메시지도 이 목록에 표시합니다.
 * Warn - 아직 오류는 아니지만 발생 가능한 문제뿐 아니라 더 낮은 레벨의 메시지도 이 목록에 표시합니다.
 * Error - 오류를 일으킨 문제뿐 아니라 더 낮은 레벨의 메시지도 이 목록에 표시합니다.
 * Assert - 개발자가 결코 발생해서는 안 된다고 생각하는 문제를 표시합니다.
 */
public class TBLog {

    private static final String TAG = "TBLog";

    private static final String gap = "%-25s";
    private static final String methodGap = "%-20s";

    private static SimpleDateFormat logFormat = new SimpleDateFormat("YYYY-MM-dd_hh:mm:ss");

    public static void writeFile(String data) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            String filename = simpleDateFormat.format(new Date(System.currentTimeMillis()));
            String folderName = "./";
            File folder = new File(folderName + File.separator + TAG + File.separator + filename);
            System.out.println(folder.getPath());
            boolean isExsistFolder = true;
            if (!folder.exists()) {
                isExsistFolder = folder.mkdirs();
            }
            if (isExsistFolder) {
                boolean isFileExists = true;
                File file = new File(folder.getPath() + File.separator + filename + ".txt");
                if (!file.exists()) {
                    isFileExists = file.createNewFile();
                }
                if (isFileExists) {
                    BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                    buf.append(data);
                    buf.newLine();
                    buf.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String message) {
        //if(BuildConfig.DEBUG)
        StackTraceElement[] stack = new Throwable().getStackTrace();
        StackTraceElement currentStack = stack[1];
        String time = logFormat.format(new Date(System.currentTimeMillis()));
        String fileName = String.format(gap, currentStack.getFileName() + "::");
        String method = String.format(methodGap, currentStack.getMethodName() + "(" + currentStack.getLineNumber() + ")");
        String log = fileName + method + ": " + message;
        //Log.v("walletlog", log);
        log = time + " " + fileName + method + ": " + message;
        System.out.println(log);
        writeFile(log);
    }

    public static void v(String tag, String message) {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        StackTraceElement currentStack = stack[1];
        String time = logFormat.format(new Date(System.currentTimeMillis()));
        String fileName = String.format(gap, currentStack.getFileName() + "::");
        String method = String.format(gap, currentStack.getMethodName() + "(" + currentStack.getLineNumber() + ")");
        String log = fileName + method + ": " + message;
        //Log.v(TAG + tag, log);
    }


    public static void ef(String format, Object... objects) {
        e(String.format(format, objects));
    }

    public static void e(String message) {
        /*if (BuildConfig.DEBUG)*/
        //Log.e(TAG, message);

        StackTraceElement[] stack = new Throwable().getStackTrace();
        StackTraceElement currentStack = stack[1];
        String time = logFormat.format(new Date(System.currentTimeMillis()));
        String fileName = String.format(gap, currentStack.getFileName() + "::");
        String method = String.format(gap, currentStack.getMethodName() + "(" + currentStack.getLineNumber() + ")");
        String log = fileName + method + ": " + message;
        log = time + " " + fileName + method + ": " + message;
        writeFile(log);
    }

    public static void e(String tag, String message) {
        //if(BuildConfig.DEBUG)
        //Log.e(tag, message);
    }

    public static void api(String simpleName, String msg) {
        StringBuffer sb = new StringBuffer(msg);
        if (sb.length() > 2000) {
            //Log.v(simpleName, "sb.length = " + sb.length());
            int chunkCount = sb.length() / 2000;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = 2000 * (i + 1);
                if (max >= sb.length()) {
                    //Log.v(simpleName, sb.substring(2000 * i));
                } else {
                    //Log.v(simpleName, sb.substring(2000 * i, max));
                }
            }
        } else {
            //Log.v(simpleName, sb.toString());
        }
    }
}
