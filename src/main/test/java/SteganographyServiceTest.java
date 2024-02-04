import com.deanOfWalls.InPlainSight.service.SteganographyService;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SteganographyServiceTest {

    @Test
    public void testCreateRar() {
        // Given
        SteganographyService steganographyService = new SteganographyService();

        List<MultipartFile> testFiles = new ArrayList<>();
        testFiles.add(new MockMultipartFile("1.png", "1.png", "image/png", new byte[0]));
        testFiles.add(new MockMultipartFile("2.png", "2.png", "image/png", new byte[0]));

        String testPassword = "testPassword";
        String testRarFileName = "target/classes/static/secret.rar"; // Updated path

        // When
        try {
            steganographyService.createRar(testFiles, testRarFileName, testPassword);
        } catch (IOException | InterruptedException e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }

        // Then
        assertTrue(new File(testRarFileName).exists());
    }
}
