package com.cherish.mailsender.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileIndex {

    @Id
    private Long id;
    @Column
    private int fileIndex;
}

