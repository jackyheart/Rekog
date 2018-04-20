import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQS;

import com.amazonaws.services.rekognition.model.NotificationChannel;

public class VideoDetect {
    private static AmazonSNS sns = null;
    private static AmazonSQS sqs = null;
    private static AmazonRekognition rek = null;
    //private static NotificationChannel channel = new NotificationChannel();
}
