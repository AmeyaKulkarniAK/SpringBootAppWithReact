package com.example.CRUDExample.repo;

import com.example.CRUDExample.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {

}
