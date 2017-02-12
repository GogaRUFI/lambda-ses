package com.gogarufi.lambda.ses;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

class EmailService {
    static int send(String from, String to, String text, String subject) throws EmailServiceException {
        try {
            AmazonSimpleEmailServiceClientBuilder builder = AmazonSimpleEmailServiceClientBuilder.standard();

            builder.setRegion(Regions.EU_WEST_1.getName());

            builder.setClientConfiguration(
                    new ClientConfiguration()
                            .withConnectionTimeout(3000)
                            .withRequestTimeout(3000)
            );

            AmazonSimpleEmailService service = builder.build();

            SendEmailRequest sendEmailRequest = createEmailRequest(from, to, text, subject);

            SendEmailResult sendEmailResult = service.sendEmail(sendEmailRequest);

            return sendEmailResult.getSdkHttpMetadata().getHttpStatusCode();
        } catch (Throwable e) {
            throw new EmailServiceException(e);
        }
    }

    private static SendEmailRequest createEmailRequest(String from, String to, String text, String subject) {

        Destination destination = new Destination().withToAddresses(to);

        Content subjectContent = new Content().withData(subject);

        Content textContent = new Content().withData(text);
        Body body = new Body().withText(textContent);

        Message message = new Message().withSubject(subjectContent).withBody(body);

        return new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);

    }
}
