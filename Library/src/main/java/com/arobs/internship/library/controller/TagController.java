package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.handler.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagDTOConverter tagDTOConverter;

    @PostMapping("/addTag")
    public ResponseEntity<?> addTag(@RequestBody @Valid TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            tagService.insertTag(tagDTOConverter.dtoToTag(tagDTO));
            return new ResponseEntity<>("Tag inserted successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<?> findTags() {
        List<Tag> tags = tagService.findTags();
        if (tags.isEmpty()) {
            return new ResponseEntity<>("No tags present in the db", HttpStatus.BAD_REQUEST);
        }
        List<TagDTO> tagDTOS = tagDTOConverter.listTagToDTO(tags);
        return new ResponseEntity<>(tagDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTag(@RequestParam("tagName") String tagName) {
        Tag tag = tagService.findTagByName(tagName);
        if (tag == null) {
            return new ResponseEntity<>("No tag with this name found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tagDTOConverter.tagToDTO(tag), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTag")
    public ResponseEntity<?> deleteTag(@RequestParam("name") String name) {
        try {
            tagService.deleteTag(name);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Tag with name: " + name + " deleted successfully.", HttpStatus.OK);
    }
}
