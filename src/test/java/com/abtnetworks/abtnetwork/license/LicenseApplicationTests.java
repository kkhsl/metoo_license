package com.abtnetworks.abtnetwork.license;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Encoder;

@SpringBootTest
class LicenseApplicationTests {

//
//
//    private static final String licenseInfoEncrypt = "Gx5r3sWA0GTQu4ES/BNllZ14TuPY3XWKlAyf5D6bV4XCovcz9hZH++UeOxFloQL+qdzXf/5Afiksd//FxVMqLHoIliO1lvOIoFsG9lHgwy1ZuoLXugjj9k4TqilDGQ490Lii8IZpp9BPIU/1mff7ow==";
//
//
//
//    private static final byte[] key = new byte[]{26, 43, 60, 77, 94, 111, 122, -117};
//    private static final byte[] iv = new byte[]{70, 105, 114, 101, 119, 97, 108, 108};
//
//
//    @Test
//    void contextLoads() {
//
//    }
//
//    @Test
//    public void test(){
//
//        String decodeStr = this.decode(licenseInfoEncrypt);
//        System.out.println("decodeStr：" + decodeStr);
//
//        String encode = this.encode();
//        System.out.println(encode);
//    }
//
//
//    public static void main(String[] args) {
//        String decodeStr = decode(licenseInfoEncrypt);
//        System.out.println(decodeStr);
//        String encode = encode();
//        System.out.println(encode);
//    }
//
//    public static String decode(String aCode) {
//        byte[] base64Decoded = Base64.decode(aCode.getBytes());
//        byte[] decoded = DesCBC.CBCDecrypt(base64Decoded, key, iv);
//        String decodedStr = "";
//        if (decoded != null) {
//            decodedStr = new String(decoded);
//        }
//
//        return decodedStr;
//    }
//
//    public static String encode() {
//        String aCode = "TTTT-TTTT-TTTT-TTTT817481827391379630835439T-99999;99999;99999;99999;99999;99999;-A;B;C;D;F-20220222-20230225---";
//        byte[] source = DesCBC.CBCEncrypt(aCode.getBytes(), key, iv);
//        BASE64Encoder encoder = new BASE64Encoder();
//        byte[] encode64 = encoder.encode(source).getBytes();
//        String decodedStr = new String(encode64);
//        return decodedStr;
//    }
//
//

}
