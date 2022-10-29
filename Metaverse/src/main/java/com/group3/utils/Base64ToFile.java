package com.group3.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * The generated files are saved to the images folder in the D drive
 */
@Component
public class Base64ToFile {
    /**
     * Tool class, base64 to file and save to local
     * @param base64 Incoming base64 data
     * @param filePath Address where the file is to be saved
     *
     * @return The address url where the file is saved
     */
    public boolean saveBase64(String base64, String filePath){

        //Remove the front part of base64 (can be optimized, sometimes not necessarily in png format)
        String newBase64 = base64.replace("data:image/png;base64,", "").replace("event: newImage\n" +
                "id: 1\n" +
                "data:", "");
        //Determine if a folder exists
        File outputFile = new File(filePath);

        File fileP = outputFile.getParentFile();

        if (!fileP.exists() && !fileP.mkdirs() && !fileP.mkdir()) {
            throw new IllegalArgumentException("Fail to create file direction");
        }

        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // Decode the byte array string using Base64 and generate the file
            byte[] byt = decoder.decodeBuffer(newBase64);
            for (int i = 0, len = byt.length; i < len; ++i) {
                // Adjusting abnormal data
                if (byt[i] < 0) {
                    byt[i] += 256;
                }
            }
            InputStream input = new ByteArrayInputStream(byt);
            // Generate a file in the specified format
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
