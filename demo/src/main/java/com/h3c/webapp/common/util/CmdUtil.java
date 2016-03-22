package com.h3c.webapp.common.util;

import java.io.IOException;

public final class CmdUtil {

    /**
     * cmd命令拷贝文件(覆盖)
     */
    private final static String copyOrder = "cmd.exe /c copy /y sourceFile targetFile";

    /**
     * cmd命令移动文件(覆盖)
     */
    private final static String moveOrder = "cmd.exe /c move /y sourceFile targetFile";

    /**
     * 使用cmd命令拷贝或移动文件
     *
     * @param type
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyOrMoveFile(String type, String sourceFile,
                                      String targetFile) throws IOException {
        String sCmd = null;
        // 判断执行什么操作
        if (type.equalsIgnoreCase("copy")) {
            sCmd = copyOrder;
        } else if (type.equalsIgnoreCase("move")) {
            sCmd = moveOrder;
        }

        if (sCmd == null) {
            return;
        }

        // 源文件
        sCmd = sCmd.replace("sourceFile", sourceFile);
        // 目标目录
        sCmd = sCmd.replace("targetFile", targetFile);
        // 记录日志
        Runtime.getRuntime().exec(sCmd);
    }

}
