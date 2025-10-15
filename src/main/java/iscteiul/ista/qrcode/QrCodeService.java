package iscteiul.ista.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrCodeService {

    private final QrCodeRepository repository;

    public QrCodeService(QrCodeRepository repository) {
        this.repository = repository;
    }

    public QrCode readFromFile(File file) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);
        QrCode qrCode = new QrCode(result.getText());
        repository.save(qrCode);
        return qrCode;
    }
}
