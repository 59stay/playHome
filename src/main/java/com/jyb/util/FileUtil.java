package com.jyb.util;

import java.io.File;
import java.util.List;
/**
 * 目录文件操作工具类
 * @author jyb
 *
 */
@SuppressWarnings("all")
public class FileUtil {
	
	 private static String fileName =  null;
	 
	 
	 
	 /**
	  *  使用递归找出某目录("C:\\JavaProducts")下的所有子目录以及子文件
	  * @param filePath
	  * @param filePaths
	  * @return
	  */
	 public static List<String> getAllFilePaths(File filePath,List<String> filePaths){
		          File[] files = filePath.listFiles();
		          if(files == null){
		              return filePaths;    
		          }    
		          for(File f:files){
		              if(f.isDirectory()){
		                  filePaths.add(f.getPath());
		                  getAllFilePaths(f,filePaths);
		              }else{
		                  filePaths.add(f.getPath());
		              }    
		          }
		          return filePaths;
		      } 
	 
	 
	 
	/**
	 *  读取指定路径下的文件名和目录名
	 * @param fileList
	 * @return
	 */
	public static String readFlieAndCatalog( File[] fcList){
		for (int i = 0; i < fcList.length; i++) {
            if (fcList[i].isDirectory()) {
                 fileName = fcList[i].getName();
                System.out.println("目录：" + fileName);        
            }
            if (fcList[i].isFile()) {
                fileName = fcList[i].getName();
                System.out.println("文件：" + fileName);                
            }
        }
		return fileName;
	}
	
	/**
	 *  读取指定路径下的文件名
	 * @param fileList
	 * @return
	 */
	public static String readFlie( File[] fileList){
		for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                fileName = fileList[i].getName();
                System.out.println("文件：" + fileName);                
            }
        }
		return fileName;
	}
	
	
	/**
	 * 读取指定路径下的目录名
	 * @param catalogList
	 * @return
	 */
	public static String  readCatalog( File[] catalogList){
		for (int i = 0; i < catalogList.length; i++) {
            if (catalogList[i].isFile()) {
            	fileName = catalogList[i].getName();
                System.out.println("文件：" + fileName);                
            }
        }
		return fileName;
	}
	
	
	  
   /**
    * 递归删除目录下的所有文件及子目录下所有文件
    * @param dir 将要删除的文件目录
    * @return
    */
	public static boolean deleteDir(File dir) {
	      if (dir.isDirectory()) {
	          String[] children = dir.list();
	          for (int i=0; i<children.length; i++) {//递归删除目录中的子目录下
	              boolean success = deleteDir(new File(dir, children[i]));
	              if (!success) {
	                  return false;
	              }
	          }
	      }
	      return dir.delete();
	  }
}
