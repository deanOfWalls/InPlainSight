import com.deanOfWalls.InPlainSight.service.SteganographyService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SteganographyServiceTest {
    private String tempDirPath = "src/main/resources/static";

    @Test
    public void testCreateRar() {
        //Given
        SteganographyService steganographyService = new SteganographyService();
        List<File> testFiles = Arrays.asList(
                new File("src/main/resources/static/images/1.png"),
                new File("src/main/resources/static/images/2.png")
        );
        String testPassword = "testPassword";
        String testRarFileName = tempDirPath + File.separator + "secret.rar";

        // log the file paths for debugging
        System.out.println("Test Files: ");
        testFiles.forEach(file -> System.out.println(file.getAbsolutePath()));
        System.out.println("RAR File: ");
        System.out.println(testRarFileName);


        //When
        try {
            steganographyService.createRar(testFiles, testPassword, testRarFileName);
        } catch (IOException | InterruptedException e) {
            // exceptions as needed
            e.printStackTrace();
        }
        // log the rar creation
        System.out.println("Rar created");

        //Then
        assertTrue(new File(testRarFileName).exists());

    }
}


