package com.gogarufi.lambda.ses;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.Map;

public class EmailSender {
    public void send(Context context) {
        LambdaLogger logger = context.getLogger();

        Map<String, String> env = System.getenv();

        String from = env.get("FROM");
        String to = env.get("TO");

        String body = "This email was sent through Amazon SES";
        String title = "Amazon SES Test";

        logger.log("Sending an email ... \n");

        try {
            Integer httpStatusCode = EmailService.send(from, to, body, title);
            logger.log("Response HTTP status code: " + httpStatusCode + "\n");
        } catch (EmailServiceException e) {
            logger.log("Can't send the email due to an exception: " + e + "\n");
        }
    }
}