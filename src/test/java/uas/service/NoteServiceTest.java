package uas.service;

import uas.model.Note;
import uas.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note noteBiasa;
    private Note noteTodo;

    @BeforeEach
    void setUp() {
        noteBiasa = new Note(1L, "Tugas MI", "Belajar Multimedia", false, false, false, null);
        noteTodo = new Note(2L, "Belanja Kelompok", "Beli Kertas Print", true, false, true, null);
    }

    @Test
    void testCreateCatatan_Sukses() {
        when(noteRepository.save(any(Note.class))).thenReturn(noteBiasa);
        Note saved = noteService.saveNote(noteBiasa);
        assertNotNull(saved);
        assertEquals("Tugas MI", saved.getTitle());
    }


    @Test
    void testGetAllNotes_Sukses() {
        List<Note> mockList = Arrays.asList(noteBiasa, noteTodo);
        when(noteRepository.findAllByOrderByIsPinnedDescIdDesc()).thenReturn(mockList);
        List<Note> result = noteService.getAllNotes();
        assertNotNull(result);
        verify(noteRepository, times(1)).findAllByOrderByIsPinnedDescIdDesc();
    }

    @Test
    void testUpdateCatatan_Sukses() {
        when(noteRepository.save(any(Note.class))).thenReturn(noteBiasa);
        Note updated = noteService.saveNote(noteBiasa);
        assertNotNull(updated);
    }

    @Test
    void testToggleTodo_MengubahStatusSelesai() {
        when(noteRepository.findById(2L)).thenReturn(Optional.of(noteTodo));
        noteService.toggleTodo(2L);
        verify(noteRepository, times(1)).save(any(Note.class));
    }


    @Test
    void testTogglePin_MengubahStatusSemat() {
        when(noteRepository.findById(1L)).thenReturn(Optional.of(noteBiasa));
        noteService.togglePin(1L);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testFiturPengingat_PastikanWaktuTersimpan() {
        noteBiasa.setReminderTime(null); 
        when(noteRepository.save(any(Note.class))).thenReturn(noteBiasa);
        Note saved = noteService.saveNote(noteBiasa);
        assertNull(saved.getReminderTime());
    }

    @Test
    void testDeleteCatatan_Sukses() {
        doNothing().when(noteRepository).deleteById(1L);
        noteService.deleteNote(1L);
        verify(noteRepository, times(1)).deleteById(1L);
    }
}