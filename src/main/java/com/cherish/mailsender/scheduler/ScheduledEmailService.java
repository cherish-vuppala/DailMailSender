package com.cherish.mailsender.scheduler;

import com.cherish.mailsender.entity.FileIndex;
import com.cherish.mailsender.service.FileIndexService;
import com.cherish.mailsender.service.FileService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledEmailService {


    private final JavaMailSender javaMailSender;


    private final TemplateEngine templateEngine;


    private final FileService fileService;


    private final FileIndexService fileIndexService;

    private List<Path> fileList;

    // Initialize the file list when the service starts
    @Autowired
    public ScheduledEmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine, FileService fileService, FileIndexService fileIndexService) throws Exception {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        fileList = fileService.fetchAllFiles("classpath:files");
        this.fileService = fileService;
        this.fileIndexService = fileIndexService;
    }



    // This method is called once a day to send the email
    @Scheduled(cron = "0 0 8 * * *")  // Set to send every day at 8 AM
    @Transactional
    public void sendDailyEmailWithAttachment() throws MessagingException {
        int fileIndex = getCurrentFileIndex();

        // Get the file to send today
        Path fileToSend = fileList.get(fileIndex);
        File file = fileToSend.toFile();

        // Increment the index for the next day
        int nextFileIndex = (fileIndex + 1) % fileList.size();
        saveFileIndex(nextFileIndex);  // Save the updated index to the database

        // Send the email with today's file attached
        sendEmailWithAttachment("cherryvuppala@gmail.com", "Today's Java Topic", file.getName(), file);
    }

    // Method to send the actual email
    private void sendEmailWithAttachment(String to, String subject, String topicName, File attachment) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("name", "Cherish");
        context.setVariable("topicName", topicName);

        String htmlContent = templateEngine.process("emailTemplate", context);
        helper.setText(htmlContent, true);

        helper.addAttachment(attachment.getName(), attachment);
        javaMailSender.send(mimeMessage);
    }

    // Retrieve the current file index from the database
    private int getCurrentFileIndex() {
        Optional<FileIndex> fileIndexOpt = fileIndexService.findById(1L);
        return fileIndexOpt.map(FileIndex::getFileIndex).orElse(0);  // Default to 0 if not found
    }

    // Save the updated file index to the database
    @Transactional
    private void saveFileIndex(int fileIndex) {
        var savedFileIndex = fileIndexService.saveOrUpdate(1l, fileIndex);
        System.out.println(savedFileIndex);
    }
}
