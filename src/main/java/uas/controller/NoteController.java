package uas.controller;

import uas.model.Note;
import uas.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        model.addAttribute("newNote", new Note());
        return "index";
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute Note note) {
        try {
            noteService.saveNote(note);
        } catch (IllegalArgumentException e) {
            // Validasi gagal dialihkan kembali ke halaman utama
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        model.addAttribute("notes", noteService.getAllNotes());
        // Ambil data lama berdasarkan ID, kalau ga ketemu bikin Note baru biar ga crash
        model.addAttribute("newNote", noteService.getAllNotes().stream()
                .filter(n -> n.getId().equals(id)).findFirst().orElse(new Note()));
        return "index";
    }

    @GetMapping("/pin/{id}")
    public String pinNote(@PathVariable Long id) {
        noteService.togglePin(id);
        return "redirect:/";
    }

    @GetMapping("/toggle-todo/{id}")
    public String toggleTodo(@PathVariable Long id) {
        noteService.toggleTodo(id);
        return "redirect:/";
    }
}