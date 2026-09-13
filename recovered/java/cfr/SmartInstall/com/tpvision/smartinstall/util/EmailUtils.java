/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.tpvision.smartinstall.util.EncryptionDecryptionUtility;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtils {
    private static final Logger LOG = LoggerFactory.getLogger(EmailUtils.class);
    private static final String AMAZON_MAIL_FROM = EncryptionDecryptionUtility.decryptEmailParam("Oq7TBBAjK+6DN6OcJzkQHg/hjVamdLzM2/9WKpQgXUY=");
    private static final String AMAZON_MAIL_REGION = EncryptionDecryptionUtility.decryptEmailParam("LCxkHkkRQz1FHlNYWlZgNg==");
    private static final String AMAZON_ACCESS_KEY = EncryptionDecryptionUtility.decryptEmailParam("F3fJvQ/7Xhd4S9t1IC+AzEgRJ7zC07SmVHGYCCLzr+M=");
    private static final String AMAZON_SECRET_KEY = EncryptionDecryptionUtility.decryptEmailParam("5vuzJp+i866jd+vTcVMM+SM/Xw3VHHLBmw/i8tci6P5B5s9rIatvhABER6QUrxCf");

    private EmailUtils() {
    }

    public static boolean sendEmailByAmazonService(String subject, String content, String mailAddress) {
        try {
            System.setProperty("aws.accessKeyId", AMAZON_ACCESS_KEY);
            System.setProperty("aws.secretKey", AMAZON_SECRET_KEY);
            String charset = StandardCharsets.UTF_8.name();
            String[] emails = mailAddress.split(";");
            AmazonSimpleEmailService client = (AmazonSimpleEmailService)((AmazonSimpleEmailServiceClientBuilder)AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.fromName(AMAZON_MAIL_REGION))).build();
            SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(emails)).withMessage(new Message().withBody(new Body().withHtml(new Content().withCharset(charset).withData(content)).withText(new Content().withCharset(charset).withData(content))).withSubject(new Content().withCharset(charset).withData(subject))).withSource(AMAZON_MAIL_FROM);
            client.sendEmail(request);
            LOG.info("Email sent success to <{}>!", (Object)mailAddress);
            return true;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}

