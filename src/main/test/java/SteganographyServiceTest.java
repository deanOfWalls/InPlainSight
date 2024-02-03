import com.deanOfWalls.InPlainSight.service.SteganographyService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SteganographyServiceTest {

    private SteganographyService service;

    // Declare and initialize the tempDirPath field
    private String tempDirPath = "/path/to/temp/directory"; // Replace with the actual path

    @Before
    public void setUp() {
        service = new SteganographyService();
    }

    @Test
    public void testSteganographyProcess() throws IOException, InterruptedException {
        // Load the test images
        File actuallyALizardFile = new File("src/main/resources/static/images/actually_a_lizard.png");
        File notALizardFile = new File("src/main/resources/static/images/not_a_lizard.png");

        byte[] actuallyALizardBytes = loadFileContent(actuallyALizardFile);
        byte[] notALizardBytes = loadFileContent(notALizardFile);

        MultipartFile actuallyALizard = new MockMultipartFile("fileToHide", "actually_a_lizard.png", "image/png", actuallyALizardBytes);
        MultipartFile notALizard = new MockMultipartFile("decoyImage", "not_a_lizard.png", "image/png", notALizardBytes);

        // Encode the files
        byte[] stegoImageBytes = service.encodeFiles(notALizard, actuallyALizard, "testPassword");

        // Verify the result is not null
        assertNotNull("Stego image should not be null", stegoImageBytes);

        // Decode the files into the specified directory
        byte[] extractedData = service.decodeFiles(stegoImageBytes, "testPassword");

        // Verify the extraction
        assertNotNull("Extracted data should not be null", extractedData);

        // Additional verifications
        // Compare size/content of the original file and the extracted data
        assertTrue("Extracted data should match original", Arrays.equals(actuallyALizardBytes, extractedData));
    }

    private byte[] loadFileContent(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
            return fileContent;
        }
    }
}
