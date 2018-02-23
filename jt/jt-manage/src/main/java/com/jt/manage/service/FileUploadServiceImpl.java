package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	//5.定义磁盘和url访问路径
	private String dirPath="F:/jt-upload/";//定义本地的磁盘路径
	private String url="http://image.jt.com/";//定义url访问路径
	//定义文件操作
	/***
	 * 编程思路:
	 *  1.获取图片的名称
	 *  2.截取图片类型
	 *  3.判断是否为图片格式jpg|gif|png
	 *  4.判断是否为恶意程序
	 *  5.定义磁盘和url访问路径
	 *  6.准备以时间为界限的文件夹
	 *  7.让图片不重名,时间毫秒数+三位随机数
	 *  8.创建文件夹
	 *  9.开始写盘操作
	 *  10.将数据准备好返回客户端
	 *  	真实路径:F:/jt-upload/2018/1/17/12/123.jpg
	 *   	虚拟路径:http://image.jt.com/2018/1/17/12/123.jpg
	 *   
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.获取图片的名称 aa.jpg
		 String fileName = uploadFile.getOriginalFilename();
		//2.获取图片类型
		 String fileType = fileName.substring(fileName.lastIndexOf("."));
		//3.判断是否为图片格式(采用正则表达式)
		 if(!fileType.matches("^.*(jpg|png|gif)$")){
			 //表示不是图片类型
			 result.setError(1);
			 return result;//如果程序出错直接返回对象
		 }
		// 4.判断是否为恶意程序
		//4.1通过工具类判断是否为图片
		 /**
		  * 判断一个文件是否为图片一般获取图片的高度和宽度
		  * 如果二者不为0,则表示图片
		  */
		  try {
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			//4.2获取图片的高度和宽度
			int width=bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0){
				//表示非法程序
				 result.setError(1);
				 return result;
			}
			//6.准备以时间为界限的文件夹,时间格式yyyy/MM/dd/HH
			String datePathDir=new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
			//7.准备时间
			String millis=System.currentTimeMillis()+"";
			Random random=new Random();
			int randomNum = random.nextInt(999);//0-999的三位随机数
			String randomPath=millis+randomNum;
			//8.创建文件夹
			//F:/jt-upload/yyyy/MM/dd/HH/xxxx.jpg
			String localPath=dirPath+datePathDir;
			File  file=new File(localPath);
			if(!file.exists()){
				file.mkdirs();//如果文件夹不存在则创建文件夹
			}
			//9.通过工具类实现写盘操作
			
			//F:/jt-upload/yyyy/MM/dd/HH/xxxx.jpg
			String localPathFile=localPath+"/"+randomPath+fileName;
			//文件写盘ccc/文件.jpg
			uploadFile.transferTo(new File(localPathFile));
			//准备数据返回客户端
			result.setHeight(height+"");
			result.setWidth(width+"");
			//代表虚拟的全路径
			String  urlPath=url+datePathDir+"/"+randomPath+fileName;
			result.setUrl(urlPath);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
