import com.deanOfWalls.InPlainSight.encryption.ImageEncryptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageEncryptorTest {

    public static void main(String[] args) throws Exception {
        String key = "1234567890123456"; // 16 characters for AES-128
        String imagePath = "/home/dean/Dev/InPlainSight/decoyDog.jpg";
        String differentImagePath = "/home/dean/Dev/InPlainSight/secretCat.jpg";

        // Convert images to byte arrays
        byte[] originalImageData = imageToByteArray(imagePath);
        byte[] differentImageData = imageToByteArray(differentImagePath);

        // Encrypt the original image data
        byte[] encryptedData = ImageEncryptor.encryptImage(originalImageData, key);

        // Decrypt the image data
        byte[] decryptedData = ImageEncryptor.decryptImage(encryptedData, key);

        // Save decrypted data to a file for visual comparison
        byteArrayToImage(decryptedData, "/home/dean/Dev/InPlainSight/decryptedDog.jpg");

        // Compare original and decrypted data
        boolean isEqual = java.util.Arrays.equals(originalImageData, decryptedData);
        System.out.println("Is original image equal to decrypted image? " + isEqual);

        // Negative test: Compare original image data with a different image
        boolean isDifferent = java.util.Arrays.equals(originalImageData, differentImageData);
        System.out.println("Is original image equal to a different image? " + isDifferent);
    }

    private static byte[] imageToByteArray(String imagePath) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        return bos.toByteArray();
    }

    private static void byteArrayToImage(byte[] imageData, String outputPath) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
        File outputfile = new File(outputPath);
        ImageIO.write(img, "jpg", outputfile);
    }
}
