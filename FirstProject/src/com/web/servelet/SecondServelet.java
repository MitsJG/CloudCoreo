package com.web.servelet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

class cloudCoreoAns
{
	public static boolean isPrime (long n) {
		/*
		 *  This function returs true if the number is prime else it returns false
		 */
		if (n<=1) return false;
		if (n==2) return true;
		if (n%2==0) return false;
		// no need to traverse entire number so traversing till square root
		long m = (long) Math.sqrt(n);
		for (long i=3; i<=m; i+=2) // i is incremented by 2 because even number
								   // is checked before 
 			if (n%i==0)
				return false;
			return true;
		} 
	public static long calculatePrime(int first,int second){
		long num = 0;
		
		// BigDecimal is used to compute the value of e
		
		BigDecimal e = BigDecimal.ONE;
		BigDecimal bigDecimal = BigDecimal.ONE;
 
		/*
		 *  Formula used here is -> (1 + 1/n!)^n
		 *  To get best results n should be as high as possible
		 *  Since our limits for firstnumber and secondnumber is 5 and 12
		 *  I have chosen 100 as the value of n
		 * 
		 */
		
		for(int i=1;i<100;i++) {
			bigDecimal = bigDecimal.multiply(new BigDecimal(i * 1.0 + ""));
			e = e.add(new BigDecimal(1.0 + "").divide(bigDecimal, new MathContext(10000)));
		}
 
		// Counting after decimal so substring(2)
		String valueOfE = (e + "").substring(2);
		
		for(int i=0;i<valueOfE.length()-second;i++) {
			num = Long.parseLong(valueOfE.substring(i,i+second)); 
			if(cloudCoreoAns.isPrime(num)) {
				first--;				
				if(first==0)
					break;
			}
		}
		return num;
	}
}

public class SecondServelet extends HttpServlet{

	private static final long serialVersionUID = 1L;

    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		String firstNumber = request.getParameter("first");
		String secondNumber = request.getParameter("second");
		
		// Get the parameters from the browser
		// Since they are in string form typecast it to integer
		
		int first = Integer.parseInt(firstNumber);
		int second = Integer.parseInt(secondNumber);
		
		//call the calculatePrime method to find the desired result
		
		long num = cloudCoreoAns.calculatePrime(first , second);
		
		// display the output in dynamically generated /Answer page
		response.getOutputStream().print(
				"<html>"
				+ " <body> "
				+ "<h1>" + num + "</h1>" 
				+ "</body> </html>");
		response.getOutputStream().close();	
		}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("This is destroy block");
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("This is init block");
	}

}
