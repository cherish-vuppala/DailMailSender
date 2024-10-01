package com.cherish.mailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Fetch all files from a classpath location
    public List<Path> fetchAllFiles(String location) throws IOException {
        // Load resource from classpath
        Resource resource = resourceLoader.getResource(location);
        Path directory = Paths.get(resource.getURI());

        try (Stream<Path> walk = Files.walk(directory)) {
            return walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
    }
}
