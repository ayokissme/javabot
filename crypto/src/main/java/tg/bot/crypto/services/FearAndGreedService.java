package tg.bot.crypto.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import tg.bot.crypto.config.ApiProperties;
import tg.bot.crypto.config.YandexProperties;
import tg.bot.crypto.exceptions.FearAndGreedException;

/**
 * @author nnikolaev
 * @since 27.05.2023
 */
@Service
@RequiredArgsConstructor
public class FearAndGreedService {

    private final ApiProperties apiProperties;
    private final YandexProperties yaProperties;

    public InputFile getPhoto() {
        try {
            String yandexBucket = String.format(apiProperties.getYandexBucket(), getDateString());
            URL url = new URL(yandexBucket);
            InputStream inputStream;
            try {
                inputStream = url.openStream();
            } catch (IOException e) {
                savePhotoToYandexBucket();
                try {
                    inputStream = url.openStream();
                } catch (IOException ex) {
                    throw new FearAndGreedException();
                }
            }
            return new InputFile(inputStream, "Fear&Greed");
        } catch (MalformedURLException ignored) {
            throw new FearAndGreedException();
        }
    }

    @SneakyThrows
    private void savePhotoToYandexBucket() {
        PropertiesCredentials credentials = new PropertiesCredentials(getCredentials());

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(
                new AmazonS3ClientBuilder.EndpointConfiguration(
                    "storage.yandexcloud.net","ru-central1"
                )
            )
            .build();

        File file = getFearAndGreedImage();
        PutObjectRequest putObjectRequest = new PutObjectRequest(yaProperties.getBucket(), getFileName(), file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        putObjectRequest.withMetadata(metadata);

        s3.putObject(putObjectRequest);
    }

    private File getCredentials() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("yc/credentials").getFile());
    }

    @SneakyThrows
    private File getFearAndGreedImage() {
        URL url = new URL(apiProperties.getFearAndGreed());
        File tempFile = File.createTempFile("FearAndGreedImage", ".png");
        tempFile.deleteOnExit();

        try (InputStream input = url.openStream()) {
            try (OutputStream output = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[2048];
                int length;

                while ((length = input.read(buffer)) != -1) {
                    output.write(buffer, 0, length);
                }
            }
        }

        return tempFile;
    }

    private String getDateString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
    }

    private String getFileName() {
        return getDateString() + ".png";
    }
}
