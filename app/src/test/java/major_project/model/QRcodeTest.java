package major_project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import major_project.model.output.QRcode;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import com.google.zxing.WriterException;

public class QRcodeTest {
    @Test
    void genearateQRcodeTest() throws WriterException, IOException {
        assertEquals("./src/main/resources/MyQRCode.png", QRcode.createQR("any"));
        assertNull(QRcode.createQR(null));
    }

}
