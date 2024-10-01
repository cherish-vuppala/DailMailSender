package com.cherish.mailsender.repository;

import com.cherish.mailsender.entity.FileIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FileIndexRepository extends JpaRepository<FileIndex, Long> {
}
