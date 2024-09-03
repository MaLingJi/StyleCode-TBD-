package com.outfit_share.service.post;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.Tags;
import com.outfit_share.entity.post.TagsDTO;
import com.outfit_share.repository.post.TagsRepository;

@Service
public class TagService {

    @Autowired
    private TagsRepository tagsRepository;

    public TagsDTO createTag(String tagName) {
        Tags tag = new Tags();
        tag.setName(tagName);
        tag = tagsRepository.save(tag);
        return new TagsDTO(tag);
    }

    public TagsDTO updateTag(Integer tagId, String newTagName) {
        Tags tag = tagsRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(newTagName);
        tag = tagsRepository.save(tag);
        return new TagsDTO(tag);
    }

    public void deleteTag(Integer tagId) {
        tagsRepository.deleteById(tagId);
    }

    public TagsDTO getTagById(Integer tagId) {
        Tags tag = tagsRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag not found"));
        return new TagsDTO(tag);
    }

    public TagsDTO getTagByName(String tagName) {
        Tags tag = tagsRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
        return new TagsDTO(tag);
    }

    public List<PostDTO> getPostsByTagName(String tagName) {
        Tags tag = tagsRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found"));
        List<PostDTO> postDTOs = new ArrayList<>();
        for (PostTags postTag : tag.getPostTags()) {
            postDTOs.add(new PostDTO(postTag.getPost()));
        }
        return postDTOs;
    }
}
