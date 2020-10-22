package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.PrintWriter;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


import model.Venue;

public class FileData {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	public static long getLastModified() {

		File f = new File(getFileName("SHEET_DATA_PATH"));
		return f.lastModified();
	}
	public static String getFileName(String propPath){
		 InputStream inputStream;
		 try {	
			 
			 Properties prop = new Properties();
			 inputStream = FileData.class.getClassLoader().getResourceAsStream("config.properties");
			 prop.load(inputStream);
			 System.out.println(prop.getProperty(propPath));
			 if(prop.getProperty(propPath)==null){
				 System.out.println("file name not found");
			 }else
				 return prop.getProperty(propPath);
			 
		 }catch(Exception e) {
				e.printStackTrace();
		 }
		 return "";
	}

	public static List<Venue> getFile() throws IOException {

		List<Venue> sheetData = new ArrayList<Venue>();
		FileInputStream fis = null;
		try {
//			fis = new FileInputStream(filename);
			fis = new FileInputStream(getFileName("SHEET_DATA_PATH"));
			

			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			for (String page : Venue.VENUE_TYPE) {

				XSSFSheet sheet = workbook.getSheet(page);
				if (sheet == null) {
					System.out.println(page + " cannot be found");
					continue;
				}
				String name = sheet.getSheetName();
				List<XSSFRow> rowList = getRowList(workbook, sheet, name);

				for (XSSFRow row : rowList) {
					Venue venue = new Venue();
					venue.setVenueType(page);
					venue.setVenueId(getText(row.getCell(Venue.VENUE_ID)));
					venue.setVenueEngName(getText(row.getCell(Venue.VENUE_ENG_NAME)));
					venue.setVenueChHKName(getText(row.getCell(Venue.VENUE_CHHK_NAME)));
//					venue.setVenueChSimName(getText(row.getCell(Venue.VENUE_CH_SIMPLE_NAME)));
					sheetData.add(venue);
				}

				for (int i = 0; i < sheetData.size(); i++) {
//					System.out.println("venue.setVenue: " + sheetData.get(i).getVenueType());
//					System.out.println("venue.setVenueEngName: " + sheetData.get(i).getVenueEngName());
//					System.out.println("venue.setVenueCHHKName: " + sheetData.get(i).getVenueChHKName());
//					System.out.println("venue.setVenueCHSIMName: " + sheetData.get(i).getVenueChSimName());
//					System.out.println("venue.setVenue: " + sheetData.get(i).getVenueId());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return sheetData;

	}

	private static String getText(XSSFCell cell) {
		if (cell == null)
			return "";
		else {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		}
	}


	private static List<XSSFRow> getRowList(XSSFWorkbook WB, XSSFSheet sheet, String name) {
		int col1 = 0;
		int firstRow = 0;

		List<XSSFRow> rowList = new ArrayList<XSSFRow>();

		if (name.equals(Venue.LSB)) {
			col1 = Venue.LSB_START_COL;
			firstRow = Venue.LSB_START_ROW;
		}
		if (name.equals(Venue.CSB)) {
			col1 = Venue.CSB_START_COL;
			firstRow = Venue.CSB_START_ROW;
		}
		for (int i = firstRow; i <= sheet.getLastRowNum(); i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null)
				continue;

			XSSFCell cell = row.getCell(col1);

			if (cell != null && !getText(cell).isEmpty())
				rowList.add(row);
		}

		return rowList;
	}

	
	public static void createCsv(String id,String feedback,String location, String category ){
		
		 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			File directory = new File(FileData.getFileName("CSV_FILE_PATH")+"vps_cac_"+sdf1.format(timestamp));
			
			if (directory.exists() == false) {
				directory.mkdir();
				System.out.println("Directory " + directory.toString() + " created.");
			} else {
				System.out.println("Directory " + directory.toString() + " is existed.");
			}
			
		String	FileName = directory + "/vps_cac_"+sdf.format(timestamp)+".csv";
	
		
		 try { 
		        
			 PrintWriter pw= new PrintWriter(FileName, "UTF-8");		  
			 	//pw.write("\ufeff");

		        String data = sdf.format(timestamp)+"|"+id+"|"+location+"|"+category+"|"+feedback.replace('|', ',')+"\n";
		        pw.write(data); 
		        pw.close(); 
		         
		    } 
		    catch (IOException e) { 
		        // TODO Auto-generated catch block 
		        e.printStackTrace(); 
		    } 
		
	}
	

	
}
