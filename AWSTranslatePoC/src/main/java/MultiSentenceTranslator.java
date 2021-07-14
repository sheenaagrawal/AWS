import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import java.util.ArrayList;
import java.util.List;

public class MultiSentenceTranslator {

    public static void main(String[] args) {
        // Define the text to be translated here
        String region = "us-west-2";
        String text = "Hola como estas. Mi nombre es Sheena. Yo vivo en India. Estoy traduciendo idiomas";

        String sourceLang = "spanish";
        String targetLang = "english";

        // Break text into sentences
        SentenceSegmenter sentenceSegmenter = new SentenceSegmenter();
        List<String> sentences = new ArrayList<>();
        try {
            sentences = sentenceSegmenter.segment(text, sourceLang);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(1);
        }

        // Create an Amazon Translate client
        System.out.println("before build");
        AWSCredentialsProvider awsCredentialsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return "AKIAURKSDPWER2BFMKY7";
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return "+7lCsA6y3GLAENDTBKFJmjFTFgSjr09vBbe8gVsE";
                    }
                };
            }

            @Override
            public void refresh() {

            }
        };
        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withRegion("us-west-2")
                .withCredentials(awsCredentialsProvider)
                .build();
        System.out.println("successfully built");
        // Translate sentences and print the results to stdout
        for (String sentence : sentences) {
            TranslateTextRequest request = new TranslateTextRequest()
                    .withText(sentence)
                    .withSourceLanguageCode(sourceLang)
                    .withTargetLanguageCode(targetLang);
            System.out.println("before translation");
            var result = translate.translateText(request).getTranslatedText();
            System.out.println("after translation");
            System.out.println("Original text: " + sentence);
            System.out.println("Translated text: " + result);
        }
    }

}