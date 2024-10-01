package com.cherish.mailsender.service;

import com.cherish.mailsender.entity.FileIndex;
import com.cherish.mailsender.repository.FileIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class FileIndexService {
    @Autowired
    private FileIndexRepository fileIndexRepository;

    public Optional<FileIndex> findById(long id) {
        return fileIndexRepository.findById(id);
    }

    public FileIndex saveOrUpdate(Long id, int index) {
        var fileIndexOptional = findById(id);
        if (fileIndexOptional.isPresent()) {
            fileIndexOptional.get().setFileIndex(index);
            return fileIndexRepository.save(fileIndexOptional.get());
        } else {
            var newFileIndex = new FileIndex(id, index);
            return fileIndexRepository.save(newFileIndex);
        }
    }
}
