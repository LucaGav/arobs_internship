package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.dtos.TagDTO;
import com.arobs.internship.library.handler.MyCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping(path = "/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/addTag")
    public ResponseEntity<?> addTag(@RequestBody @Valid TagDTO tagDTO){
        try {
            tagService.insertTag(tagService.dtoToTag(tagDTO));
            return new ResponseEntity<>("Tag inserted successfully", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<?> findTags(){
        List<TagDTO> tags = tagService.findTags();
        if(tags == null){
            return new ResponseEntity<>("No tags present in the db", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTag(@RequestParam("tagDescription") String tagDescription) {
        TagDTO tagDTO;
        try {
            tagDTO = tagService.tagToDto(tagService.findTagByDescription(tagDescription));
        } catch (MyCustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTag")
    public ResponseEntity<?> deleteTag(@RequestParam("description") String description){
        try{
            tagService.deleteTag(description);
        } catch (MyCustomException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Tag with description: " + description + " deleted successfully.", HttpStatus.OK);
    }
}
