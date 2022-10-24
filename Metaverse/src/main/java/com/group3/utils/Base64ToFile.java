package com.group3.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * 生成的文件保存到了d盘images里
 */
@Component
public class Base64ToFile {
    /**
     * 工具类，base64转文件并保存到本地
     * @param base64 传入的base64数据
     * @param filePath 文件要保存的地址
     *
     * @return 文件所保存的地址url
     */
    public boolean saveBase64(String base64, String filePath){

        //去除base64前面部分(可优化，有的时候不一定是png格式)
        String newBase64 = base64.replace("data:image/png;base64,", "").replace("event: newImage\n" +
                "id: 1\n" +
                "data:", "");
        //判断文件夹是否存在
        File outputFile = new File(filePath);

        File fileP = outputFile.getParentFile();

        if (!fileP.exists() && !fileP.mkdirs() && !fileP.mkdir()) {
            throw new IllegalArgumentException("Fail to create file direction");
        }

        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // Base64解码,对字节数组字符串进行Base64解码并生成文件
            byte[] byt = decoder.decodeBuffer(newBase64);
            for (int i = 0, len = byt.length; i < len; ++i) {
                // 调整异常数据
                if (byt[i] < 0) {
                    byt[i] += 256;
                }
            }
            InputStream input = new ByteArrayInputStream(byt);
            // 生成指定格式的文件
            out = new FileOutputStream(filePath);
            byte[] buff = new byte[1024];
            int len;
            while ((len = input.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.flush();
                out.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
