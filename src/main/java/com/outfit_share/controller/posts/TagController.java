package com.outfit_share.controller.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.entity.post.TagsDTO;
import com.outfit_share.service.post.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<TagsDTO> createTag(@RequestBody String tagName) {
        TagsDTO tagDTO = tagService.createTag(tagName);
        return ResponseEntity.ok(tagDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagsDTO> updateTag(@PathVariable Integer id, @RequestBody String newTagName) {
        TagsDTO tagDTO = tagService.updateTag(id, newTagName);
        return ResponseEntity.ok(tagDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagsDTO> getTagById(@PathVariable Integer id) {
        TagsDTO tagDTO = tagService.getTagById(id);
        return ResponseEntity.ok(tagDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<TagsDTO> getTagByName(@RequestParam String name) {
        TagsDTO tagDTO = tagService.getTagByName(name);
        return ResponseEntity.ok(tagDTO);
    }

    @GetMapping("/{name}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByTagName(@PathVariable String name) {
        List<PostDTO> posts = tagService.getPostsByTagName(name);
        return ResponseEntity.ok(posts);
    }
}
