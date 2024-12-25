package com.example.authorarticlesproject.service;

import com.example.authorarticlesproject.model.Reader;
import com.example.authorarticlesproject.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Optional<Reader> getReaderById(Long id) {
        return readerRepository.findById(id);
    }

    public Reader saveReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader updateReader(Long id, Reader reader) {
        return readerRepository.findById(id).map(existingReader -> {
            existingReader.setName(reader.getName());
            existingReader.setSurname(reader.getSurname());
            existingReader.setNotifications(reader.getNotifications());
            return readerRepository.save(existingReader);
        }).orElseThrow(() -> new RuntimeException("Reader not found"));
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }
}
