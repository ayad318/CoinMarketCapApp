package major_project.model.output;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRcode {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/MyQRCode.png";

    public static String createQR(String text) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;

        if (text == null)
            return null;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 115, 115);
        } catch (WriterException e) {

            e.printStackTrace();
            return null;
        }

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

        return QR_CODE_IMAGE_PATH;
    }
}
