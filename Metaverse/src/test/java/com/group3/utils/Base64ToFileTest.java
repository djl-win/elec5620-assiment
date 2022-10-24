package com.group3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class Base64ToFileTest {

    @Autowired
    private Base64ToFile base64ToFile;

    @Test
    public void testSaveBase64(){

        base64ToFile.saveBase64("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGMAAABSCAYAAABAHWqdAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAADYSURBVHhe7dMhEQAwDMDA+ddaWtzZeBDwCnJ5u3sxFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDEgxIMWAFANSDMibmYuhMyDFgBQDUgxIMSDFgBQDUgxIMSDFYOx95FueiiZmtJUAAAAASUVORK5CYII=","D:\\nftImages\\test.png");
    }

    @Test
    public void te(){
        String preUuid2 = UUID.randomUUID().toString();
        String changUuid = preUuid2.substring(0,8)+preUuid2.substring(9,13)+preUuid2.substring(14,18)+preUuid2.substring(19,23)+preUuid2.substring(24);

        System.out.println(changUuid);


    }
}
