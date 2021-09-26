package com.sy.center.common.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUitl
{
  public static final Logger LOGGER = LoggerFactory.getLogger(FileUitl.class);
  private static final String ENDS_WITH = "/";
  
  public static String uploadFile(MultipartFile file, String filePath)
    throws IOException
  {
    File existsDirectory = new File(filePath);
    if (!existsDirectory.exists()) {
      existsDirectory.mkdirs();
    }
    if (!filePath.endsWith("/")) {
      filePath = filePath + "/";
    }
    File uploadFile = new File(filePath + file.getOriginalFilename());
    file.transferTo(uploadFile);
    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    String newFile = newFileName() + suffix;
    
    new File(filePath + file.getOriginalFilename()).renameTo(new File(filePath + newFile));
    return newFile;
  }
  
  public static String[] uploadFiles(MultipartFile[] files, String filePath)
    throws IOException
  {
    String[] fileName = new String[files.length];
    for (int i = 0; i < files.length; i++) {
      fileName[i] = uploadFile(files[i], filePath);
    }
    return fileName;
  }
  
  public static String newFileName()
  {
    String str = String.valueOf(System.currentTimeMillis()) + String.valueOf((int)((Math.random() * 9.0D + 1.0D) * 100000.0D));
    return str;
  }
  
  public static boolean deleteAnyone(String FileName)
  {
    File file = new File(FileName);
    if (!file.exists())
    {
      LOGGER.info("文件" + FileName + "不存在，删除失败");
      return false;
    }
    if (file.isFile()) {
      return deleteFile(FileName);
    }
    return deleteDir(FileName);
  }
  
  public static boolean deleteFile(String fileName)
  {
    File file = new File(fileName);
    if ((file.exists()) && (file.isFile()))
    {
      if (file.delete())
      {
        LOGGER.info("文件" + fileName + "删除成功");
        return true;
      }
      LOGGER.info("文件" + fileName + "删除失败");
      return false;
    }
    LOGGER.info("文件" + fileName + "不存在，删除失败");
    return false;
  }
  
  public static boolean deleteDir(String dirName)
  {
    if (dirName.endsWith(File.separator)) {
      dirName = dirName + File.separator;
    }
    File file = new File(dirName);
    if ((!file.exists()) || (!file.isDirectory()))
    {
      LOGGER.info("目录删除失败" + dirName + "目录不存在");
      return false;
    }
    File[] fileArrays = file.listFiles();
    for (int i = 0; i < fileArrays.length; i++) {
      deleteAnyone(fileArrays[i].getAbsolutePath());
    }
    if (file.delete()) {
      LOGGER.info("目录" + dirName + "删除成功");
    }
    return true;
  }
}
