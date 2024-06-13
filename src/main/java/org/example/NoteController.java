package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteRepo noteRepository;

    public NoteController() {
        this.noteRepository = new NoteRepo();
    }

    @GetMapping("/")
    public String viewNotes(Model model) {
        model.addAttribute("notes", noteRepository.getNotes());
        return "view-notes";
    }

    @GetMapping("/add")
    public String addNoteView(Model model) {
        model.addAttribute("note", new Note());
        return "add-note";
    }

    @PostMapping("/add")
    public RedirectView addNote(@ModelAttribute("note") Note note, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/notes/add", true);
        Note savedNote = noteRepository.addNote(note);
        redirectAttributes.addFlashAttribute("savedNote", savedNote);
        redirectAttributes.addFlashAttribute("addNoteSuccess", Optional.of(true));
        return redirectView;
    }
}