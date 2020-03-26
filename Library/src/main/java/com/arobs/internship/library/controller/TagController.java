package com.arobs.internship.library.controller;

import com.arobs.internship.library.business.TagService;
import com.arobs.internship.library.converters.TagDTOConverter;
import com.arobs.internship.library.dtos.book.TagDTO;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.handler.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);


    @PostMapping("/addTag")
    public ResponseEntity<?> addTag(@RequestBody @Valid TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("addTag: RequestBody has invalid information");
            return new ResponseEntity<>("Invalid information in fields", HttpStatus.BAD_REQUEST);
        }
        try {
            Tag tag = tagService.insertTag(tagDTOConverter.dtoToTag(tagDTO));
            logger.info("addTag: Tag with name: " + tag.getTagName() + " inserted successfully");
            return new ResponseEntity<>(tagDTOConverter.tagToDTO(tag), HttpStatus.OK);
        } catch (ValidationException ex) {
            logger.error("addTag: " + ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<?> findTags() {
        List<Tag> tags = tagService.findTags();
        if (tags.isEmpty()) {
            logger.info("findTags: No tags present in the database");
            return new ResponseEntity<>("No tags present in the db", HttpStatus.ACCEPTED);
        }
        List<TagDTO> tagDTOS = tagDTOConverter.listTagToDTO(tags);
        logger.info("findTags: List of tags found and sent in body");
        return new ResponseEntity<>(tagDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTag(@RequestParam("tagName") String tagName) {
        Tag tag = tagService.findTagByName(tagName);
        if (tag == null) {
            logger.info("getTag: No tag with name " + tagName + " found");
            return new ResponseEntity<>("No tag with this name found", HttpStatus.ACCEPTED);
        }
        logger.info("getTag: Tag with name " + tag.getTagName() + "found and sent");
        return new ResponseEntity<>(tagDTOConverter.tagToDTO(tag), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTag")
    public ResponseEntity<?> deleteTag(@RequestParam("name") String name) {
        if (tagService.deleteTag(name) == 1) {
            logger.info("Tag with name: " + name + " deleted successfully");
            return new ResponseEntity<>("Tag with name: " + name + " deleted successfully", HttpStatus.OK);
        } else {
            logger.error("getTag: No tag with name " + name + " found");
            return new ResponseEntity<>("No tag with this name found", HttpStatus.BAD_REQUEST);
        }
    }
}
