package uas.service;

import uas.model.Note;
import uas.repository.NoteRepository;
import uas.service.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void testSaveAndGetAllNotes_Success() {
        Note note1 = new Note(1L, "Catatan 1", "Isi Catatan 1", false, false, false, null);
        List<Note> listNotes = new ArrayList<>();
        listNotes.add(note1);

        Mockito.when(noteRepository.save(note1)).thenReturn(note1);
        Mockito.when(noteRepository.findAllByOrderByIsPinnedDescIdDesc()).thenReturn(listNotes);

        Note saved = noteService.saveNote(note1);
        List<Note> allNotes = noteService.getAllNotes();

        Assertions.assertNotNull(saved);
        Assertions.assertEquals(1, allNotes.size());
    }

    @Test
    public void testToggleTodoCheckbox_Success() {
        Note todoNote = new Note(2L, "Belanja", "Beli Kopi", true, false, false, null);
        Mockito.when(noteRepository.findById(2L)).thenReturn(Optional.of(todoNote));
        Mockito.when(noteRepository.save(todoNote)).thenReturn(todoNote);

        noteService.toggleTodo(2L);
        Assertions.assertTrue(todoNote.isCompleted());
    }

    @Test
    public void testTogglePin_Success() {
        Note normalNote = new Note(3L, "Kuliah", "UAS Besok", false, false, false, null);
        Mockito.when(noteRepository.findById(3L)).thenReturn(Optional.of(normalNote));
        Mockito.when(noteRepository.save(normalNote)).thenReturn(normalNote);

        noteService.togglePin(3L);
        Assertions.assertTrue(normalNote.isPinned());
    }

    @Test
    public void testDeleteNote_Success() {
        Long noteId = 4L;
        Mockito.doNothing().when(noteRepository).deleteById(noteId);

        Assertions.assertDoesNotThrow(() -> noteService.deleteNote(noteId));
        Mockito.verify(noteRepository, Mockito.times(1)).deleteById(noteId);
    }

    @Test
    public void testSaveNoteWithReminder_Success() {
        String waktuReminderRapat = "2026-07-13T10:00"; 
        Note noteWithReminder = new Note(5L, "Rapat", "Rapat UKM", false, false, false, waktuReminderRapat);

        Mockito.when(noteRepository.save(noteWithReminder)).thenReturn(noteWithReminder);

        Note result = noteService.saveNote(noteWithReminder);
        Assertions.assertEquals(waktuReminderRapat, result.getReminderTime());
    }

    @Test
    public void testSaveNoteWithReminder_Failure_ContainsAlphabet() {
        String waktuNgacoHuruf = "2026-07-13Tjam:ok"; 
        Note noteRusak = new Note(6L, "Kuliah", "Beli Buku", false, false, false, waktuNgacoHuruf);

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> noteService.saveNote(noteRusak)
        );
        Mockito.verify(noteRepository, Mockito.never()).save(Mockito.any(Note.class));
    }
}