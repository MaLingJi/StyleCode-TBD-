package com.outfit_share.service.post;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.outfit_share.entity.post.PostTags;
import com.outfit_share.entity.post.PostTagsDTO;
import com.outfit_share.repository.post.PostTagsRepository;

@Service
@Transactional
public class PostTagService {

    @Autowired
    private PostTagsRepository postTagsRepository;

    public PostTags savePostTags(PostTags postTags) {
        return postTagsRepository.save(postTags);
    }

    public List<PostTagsDTO> findByPostPostId(Integer postId) {
        List<PostTags> result = postTagsRepository.findByPostPostId(postId);
        List<PostTagsDTO> dtoList = new ArrayList<>();

        for (PostTags postTag : result) {
            Hibernate.initialize(postTag.getPost());
            PostTagsDTO postTagsDTO = new PostTagsDTO(postTag);
            dtoList.add(postTagsDTO);
        }
        return dtoList;
    }

    public List<PostTagsDTO> findByTagsId(Integer Id) {
        List<PostTags> result = postTagsRepository.findByTagsId(Id);
        List<PostTagsDTO> dtoList = new ArrayList<>();

        for (PostTags postTag : result) {
            Hibernate.initialize(postTag.getTags());
            PostTagsDTO postTagsDTO = new PostTagsDTO(postTag);
            dtoList.add(postTagsDTO);
        }
        return dtoList;
    }

    public List<PostTagsDTO> findAll() {
        List<PostTags> result = postTagsRepository.findAll();
        List<PostTagsDTO> dtoList = new ArrayList<>();

        for (PostTags postTag : result) {
            Hibernate.initialize(postTag.getTags());
            Hibernate.initialize(postTag.getPost());
            PostTagsDTO postTagsDTO = new PostTagsDTO(postTag);
            dtoList.add(postTagsDTO);
        }
        return dtoList;
    }
}
