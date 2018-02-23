package com.jt.dubbo.service.manage;

import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

public interface FileUploadService {
	//定义文件操作
	PicUploadResult uploadFile(MultipartFile uploadFile);

}
