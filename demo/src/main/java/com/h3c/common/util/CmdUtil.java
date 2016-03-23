package com.h3c.common.util;

import java.io.IOException;

/**
 * CmdUtil.java<br>
 *
 * @author Lu, Xing-Lin
 * @description 常用的CMD命令工具
 */
public final class CmdUtil {

    /**
     * cmd命令拷贝文件(覆盖)
     */
    private final static String copyOrder = "cmd.exe /c copy /y sourceFile targetFile";

    /**
     * cmd命令移动文件(覆盖)
     */
    private final static String moveOrder = "cmd.exe /c move /y sourceFile targetFile";

    private final static String CMB_TYPE_COPY = "copy";
    private final static String CMB_TYPE_MOVE = "move";

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
        if (type == null || type.trim().isEmpty())
            return;

        if (sourceFile == null || sourceFile.trim().isEmpty())
            return;

        if (targetFile == null || targetFile.trim().isEmpty())
            return;

        String sCmd = null;
        // 判断执行什么操作
        if (CMB_TYPE_COPY.equalsIgnoreCase(type)) {
            sCmd = copyOrder;
        } else if (CMB_TYPE_MOVE.equalsIgnoreCase(type)) {
            sCmd = moveOrder;
        } else {
            return;
        }

        // 源文件
        sCmd = sCmd.replace("sourceFile", sourceFile);
        // 目标目录
        sCmd = sCmd.replace("targetFile", targetFile);

        //执行命令
        Runtime.getRuntime().exec(sCmd);
    }

}
