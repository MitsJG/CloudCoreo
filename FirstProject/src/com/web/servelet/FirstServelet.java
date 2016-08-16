package com.web.servelet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

class csvFile{
	String finalAnswer = "";
	void csv(String filePath){
		  String csvFile = filePath;
		  BufferedReader br = null;
		  String line = "";
		  String splitBy = ",";
		  try {

		   br = new BufferedReader(new FileReader(csvFile));
		   while ((line = br.readLine()) != null) {

		    String[] data = line.split(splitBy);
		    
		      int first = Integer.parseInt(data[0]);
	      	  int second = Integer.parseInt(data[1]);
	  		
	      	  long num = cloudCoreoAns.calculatePrime(first , second);
	      	  finalAnswer+= num + " "+" ";
	      	 
		   }

		  } catch (FileNotFoundException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  } finally {
		   if (br != null) {
		    try {
		     br.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		   }
		  }

	}
}

public class FirstServelet extends HttpServlet{
	
		private static final long serialVersionUID = 1L;

	    private static final String DATA_DIRECTORY = "data";
	    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
	    
	public FirstServelet(){
		System.out.println("This is constructor");
	}
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
	
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // constructs the folder where uploaded file will be stored
        String uploadFolder = getServletContext().getRealPath("")
                + File.separator + DATA_DIRECTORY;

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        String filePath = "";
        String finalAnswer = "";

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    filePath = uploadFolder + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                }
            }}catch (FileUploadException ex) {
                throw new ServletException(ex);
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
            
          // create csvFile object and call .csv method 
          csvFile fileObject = new csvFile();
          fileObject.csv(filePath);
          // display the finalAnswer on /UploadServelet page
          response.getOutputStream().print(
  				"<html>"
  				+ " <body> "
  				+ "<h1>" + fileObject.finalAnswer + "</h1>" 
  				+ "</body> </html>");
  		response.getOutputStream().close();	
        } 

	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("This is the destroy block");
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("This is the init block");
	}
	

}
