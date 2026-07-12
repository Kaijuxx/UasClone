package uas.model;

import jakarta.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String content; 
    private boolean isTodo;     
    private boolean isCompleted;
    private boolean isPinned;   
    private String reminderTime; 

    // Constructor Kosong (Wajib ada buat JPA/Hibernate)
    public Note() {}

    // Constructor Lengkap (Biar NoteServiceTest.java gak eror)
    public Note(Long id, String title, String content, boolean isTodo, boolean isCompleted, boolean isPinned, String reminderTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isTodo = isTodo;
        this.isCompleted = isCompleted;
        this.isPinned = isPinned;
        this.reminderTime = reminderTime;
    }

    // Getter dan Setter Manual (Pengganti @Data Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public boolean isTodo() { return isTodo; }
    public void setTodo(boolean todo) { isTodo = todo; }
    
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    
    public boolean isPinned() { return isPinned; }
    public void setPinned(boolean pinned) { isPinned = pinned; }
    
    public String getReminderTime() { return reminderTime; }
    public void setReminderTime(String reminderTime) { this.reminderTime = reminderTime; }
}