package uas.service;

import uas.model.Note;
import uas.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAllByOrderByIsPinnedDescIdDesc();
    }

    public Note saveNote(Note note) {
        if (note.getReminderTime() != null && !note.getReminderTime().isEmpty()) {
            String regex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$";
            if (!note.getReminderTime().matches(regex)) {
                throw new IllegalArgumentException("Format waktu salah!");
            }
        }
        return noteRepository.save(note);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tidak ditemukan"));
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public void togglePin(Long id) {
        Note note = getNoteById(id);
        note.setPinned(!note.isPinned());   
        noteRepository.save(note);
    }

    public void toggleTodo(Long id) {
        Note note = getNoteById(id);
        if(note.isTodo()) {
            note.setCompleted(!note.isCompleted());
            noteRepository.save(note);
        }
    }
}