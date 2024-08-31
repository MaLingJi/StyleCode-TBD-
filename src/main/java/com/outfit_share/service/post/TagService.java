package com.outfit_share.service.post;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.outfit_share.entity.post.Post;
import com.outfit_share.entity.post.PostDTO;
import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.Tags;
import com.outfit_share.entity.post.TagsDTO;
import com.outfit_share.repository.post.PostRepository;
import com.outfit_share.repository.post.TagsRepository;

@Service
public class TagService {
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired PostRepository postRepository;

    public TagsDTO getTagById(Integer id) {
        Optional<Tags> tag = tagsRepository.findById(id);
        return tag.map(TagsDTO::new).orElse(null);
    }

    public List<TagsDTO> getAllTags() {
        List<Tags> tags = tagsRepository.findAll();
        List<TagsDTO> tagsDTOList = new ArrayList<>();
        for (Tags tag : tags) {
            tagsDTOList.add(new TagsDTO(tag));
        }
        return tagsDTOList;
    }

    public TagsDTO createTag(TagsDTO tagsDTO) {
        if (isTagNameExists(tagsDTO.getName())) {
            throw new IllegalArgumentException("Tag name already exists.");
        }
        Tags tag = new Tags();
        tag.setName(tagsDTO.getName());
        tag = tagsRepository.save(tag);
        return new TagsDTO(tag);
    }

    public TagsDTO updateTag(Integer id, TagsDTO tagsDTO) {
        Optional<Tags> optionalTag = tagsRepository.findById(id);
        if (optionalTag.isPresent()) {
            Tags tag = optionalTag.get();
            if (!tag.getName().equals(tagsDTO.getName()) && isTagNameExists(tagsDTO.getName())) {
                throw new IllegalArgumentException("Tag name already exists.");
            }
            tag.setName(tagsDTO.getName());
            tag = tagsRepository.save(tag);
            return new TagsDTO(tag);
        }
        return null;
    }

    public void deleteTag(Integer id) {
        tagsRepository.deleteById(id);
    }

    public TagsDTO getTagByName(String name) {
        Optional<Tags> tag = tagsRepository.findByName(name);
        return tag.map(TagsDTO::new).orElse(null);
    }

    public List<PostDTO> getPostsByTagName(String name) {
        Optional<Tags> tag = tagsRepository.findByName(name);
        if (tag.isPresent()) {
            List<PostDTO> postDTOList = new ArrayList<>();
            for (PostTags postTag : tag.get().getPostTags()) {
                postDTOList.add(new PostDTO(postTag.getPost()));
            }
            return postDTOList;
        }
        return null;
    }

    public boolean isTagNameExists(String name) {
        return tagsRepository.existsByName(name);
    }

    public List<TagsDTO> getTagsByPostId(Integer postId) {
    Optional<Post> post = postRepository.findById(postId);
    List<TagsDTO> tagsDTOList = new ArrayList<>();

    if (post.isPresent()) {
        List<PostTags> postTagsList = post.get().getPostTags();
        for (PostTags postTag : postTagsList) {
            Tags tags = postTag.getTags();
            tagsDTOList.add(new TagsDTO(tags));
        }
    }
    return tagsDTOList;
}
}
