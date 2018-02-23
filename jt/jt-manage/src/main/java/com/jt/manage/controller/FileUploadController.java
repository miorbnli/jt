
package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileUploadService;

@Controller
public class FileUploadController {
	@Autowired
	private  FileUploadService fileUploadService ;
	/***
	 * 文件上传是需要通过文件解析器才能完成,所以获取文件对象时也许要相对于的接口MultipartFile
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping("pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile){
			return fileUploadService.uploadFile(uploadFile);
	}
}
