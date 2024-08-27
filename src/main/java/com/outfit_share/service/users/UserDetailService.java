package com.outfit_share.service.users;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.outfit_share.entity.post.Images;
import com.outfit_share.entity.users.UserDetail;
import com.outfit_share.entity.users.UserDetailDTO;
import com.outfit_share.entity.users.Users;
import com.outfit_share.repository.users.UserDetailRepository;

@Service
@Transactional
public class UserDetailService {

    @Value("${user.upload.dir}")
    private String saveDirPath;

    @Autowired
    private UserDetailRepository udRepo;

    public UserDetailDTO findUserById(Integer userId) {
        UserDetail userDetail = udRepo.findById(userId).orElse(null);
        return converEntityToDto(userDetail);
    }

    public UserDetail findDetailById(Integer id) {
        Optional<UserDetail> optional = udRepo.findById(id);
        if (optional.isPresent()) {
            UserDetail dbUserDetail = optional.get();
            return dbUserDetail;
        }
        return null;
    }

    public UserDetail createDetail(Users users) {
        UserDetail uDetail = new UserDetail();
        uDetail.setUsers(users);
        uDetail.setUserName("user");
        uDetail.setCreatedTime(new Date());
        uDetail.setUserPhoto(saveDirPath + "user.png");
        uDetail.setDiscountPoints(0);
        return udRepo.save(uDetail);
    }

    private UserDetailDTO converEntityToDto(UserDetail user) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setUserId(user.getId());
        userDetailDTO.setUserEmail(user.getUsers().getEmail());
        userDetailDTO.setRealName(user.getRealName());
        userDetailDTO.setUserName(user.getUserName());
        userDetailDTO.setAddress(user.getAddress());
        userDetailDTO.setPhone(user.getPhone());
        userDetailDTO.setCreatedTime(user.getCreatedTime());
        userDetailDTO.setUpdatedTime(user.getUpdatedTime());
        userDetailDTO.setUserPhoto(user.getUserPhoto());
        userDetailDTO.setDiscountPoints(user.getDiscountPoints());
        return userDetailDTO;
    }

    public UserDetail saveDetail(UserDetail uDetail) {
        return udRepo.save(uDetail);
    }

    public UserDetail updateUserImage(Integer userId, MultipartFile multipartFile)
            throws IllegalStateException, IOException {
        Optional<UserDetail> optional = udRepo.findById(userId);

        if (optional.isPresent()) {
            UserDetail dbUserDetail = optional.get();
            String originalFileName = multipartFile.getOriginalFilename();
            // 取得副檔名
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 取得時間
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
            String timestamp = dateFormat.format(new Date());

            // 使檔名為user-20240821-0000.副檔名
            String fileName = dbUserDetail.getUserName() + "-" + timestamp + fileExtension;

            File saveFilePath = new File(saveDirPath, fileName);
            multipartFile.transferTo(saveFilePath);

            String filePath = saveFilePath.getAbsolutePath();
            dbUserDetail.setUserPhoto(filePath);
            return udRepo.save(dbUserDetail);
        }
        return null;
    }
}
