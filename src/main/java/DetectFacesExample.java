import java.util.List;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AgeRange;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetectFacesExample {

    public static void main(String[] args) {
        String photo = "0a85af03f9d77b0bc91d492153d6b1551416709189_full.jpg";
        String bucket = "tryaws-deployments-mobilehub-1214293560";

        AWSCredentials credentials;

        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
        }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image()
                .withS3Object(new S3Object()
                .withName(photo)
                .withBucket(bucket)))
                .withAttributes(Attribute.ALL);
                // Replace Attribute.ALL with Attribute.DEFAULT to get default values.

        try {
            DetectFacesResult result = rekognitionClient.detectFaces(request);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            for(FaceDetail face: faceDetails) {
                if(request.getAttributes().contains("ALL")) {
                    AgeRange ageRange = face.getAgeRange();
                    System.out.println("The detected face is estimated to be between "
                            + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
                            + " years old.");
                    System.out.println("Here's the complete set of attributes:");
                } else {
                    System.out.println("Here's the default set of attributes:");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
