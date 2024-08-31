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

    // 1. 透過 ID 查找 Tag
    @GetMapping("/{id}")
    public ResponseEntity<TagsDTO> getTagById(@PathVariable Integer id) {
        TagsDTO tag = tagService.getTagById(id);
        if (tag != null) {
            return ResponseEntity.ok(tag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 2. 查找所有 Tag
    @GetMapping
    public ResponseEntity<List<TagsDTO>> getAllTags() {
        List<TagsDTO> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    // 3. 透過 Tag 名字查找 Tag
    @GetMapping("/search")
    public ResponseEntity<TagsDTO> getTagByName(@RequestParam String name) {
        TagsDTO tag = tagService.getTagByName(name);
        if (tag != null) {
            return ResponseEntity.ok(tag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 透過 Tag 名字查找相關的 Post
    @GetMapping("/{name}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByTagName(@PathVariable String name) {
        List<PostDTO> posts = tagService.getPostsByTagName(name);
        if (posts != null && !posts.isEmpty()) {
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. 新增一個 Tag
    @PostMapping
    public ResponseEntity<TagsDTO> createTag(@RequestBody TagsDTO tagsDTO) {
        TagsDTO createdTag = tagService.createTag(tagsDTO);
        return ResponseEntity.ok(createdTag);
    }

    // 6. 更新一個 Tag
    @PutMapping("/{id}")
    public ResponseEntity<TagsDTO> updateTag(@PathVariable Integer id, @RequestBody TagsDTO tagsDTO) {
        TagsDTO updatedTag = tagService.updateTag(id, tagsDTO);
        if (updatedTag != null) {
            return ResponseEntity.ok(updatedTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 7. 刪除一個 Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/posts/{postId}/tags")
    public ResponseEntity<List<TagsDTO>> getTagsByPostId(@PathVariable Integer postId) {
        List<TagsDTO> tags = tagService.getTagsByPostId(postId);
        if (tags != null && !tags.isEmpty()) {
            return ResponseEntity.ok(tags);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
