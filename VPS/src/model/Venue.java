package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Collator;
import java.util.Locale;
import com.google.gson.Gson;

import model.Venue.venueMap;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.lang.reflect.InvocationTargetException;

public class Venue {

	private String venueType;
	private String venueEngName;
	private String venueChHKName;
	private String venueChSimName;
	private String venueLanguage;
	private String venueId;
	public static class venueMap {
		String id;
		String label;
		String defaultName;
		String locale;


		public String getLocale() {
			return locale;
		}

		public String getId() {
			return id;
		}
		
		public String getLabel() {
			return label;
		}
		
		public String getDefaultName() {
			return defaultName;
		}
		
		
	}

	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	public String getVenueLanguage() {
		return venueLanguage;
	}
	public void setVenueLanguage(String venueLanguage) {
		this.venueLanguage = venueLanguage;
	}
	public String getVenueType() {
		return venueType;
	}
	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}
	public String getVenueEngName() {
		return venueEngName;
	}
	public void setVenueEngName(String venueEngName) {
		this.venueEngName = venueEngName;
	}
	public String getVenueChHKName() {
		return venueChHKName;
	}
	public void setVenueChHKName(String venueChHKName) {
		this.venueChHKName = venueChHKName;
	}
	public String getVenueChSimName() {
		return venueChSimName;
	}
	public void setVenueChSimName(String venueChSimName) {
		this.venueChSimName = venueChSimName;
	}
	
	public static final String LSB = "LSB";
	public static final String CSB = "CSB";
	public static final List<String> VENUE_TYPE = new ArrayList<String>(Arrays.asList(LSB,
			CSB
	));
	private static int A=0,B=1,C=2,D=3;
	public static final int LSB_START_COL = A;
	public static final int CSB_START_COL = A;
	
	public static final int LSB_START_ROW = 2;
	public static final int CSB_START_ROW = 2;
	public static final int VENUE_ID = A;
	public static final int VENUE_ENG_NAME = B;
	public static final int VENUE_CHHK_NAME = C;
	public static final int VENUE_CH_SIMPLE_NAME = D;


	public static venueMap[] getSearchResult(String searchKW, List<Venue> sheetData) throws IOException {

//    	List<Venue> sheetData = this.getFile();
	List<venueMap> searchResult = new ArrayList<venueMap>();
	String lang = "";
	String reg = "[\\u4E00-\\u9FA5]+";
	Matcher m = Pattern.compile(reg).matcher(searchKW);
//  String encode = "GB2312";
//	String encode = "CP950";   	
    	
	if (m.find()) {
		if (searchKW.equals(new String(searchKW.getBytes("GBK"), "GBK"))) {		
				lang = "VenueChHK";					
		}
		else
			lang = "VenueChSim";			
	} else
		lang = "VenueEng";

	System.out.println("lang" + lang + searchKW);
	String s = new String(searchKW.getBytes(), "utf-8");
	for (int i = 0; i < sheetData.size(); i++) {
		venueMap map = new venueMap();
		map.id = sheetData.get(i).getVenueId();
		map.locale = lang;
		if (lang.equals("VenueChHK")) {
			String a = new String(sheetData.get(i).getVenueChHKName().getBytes(), "utf-8");
			if (a.contains(s)||s.contains(a)) {
				map.label = sheetData.get(i).getVenueChHKName();
				map.defaultName = sheetData.get(i).getVenueEngName();
				searchResult.add(map);
			}
		} 
//		else if (lang.equals("VenueChSim")) {
//			String a = new String(sheetData.get(i).getVenueChSimName().getBytes(), "utf-8");
//			if (a.contains(s)||s.contains(a)) {
//				map.label = sheetData.get(i).getVenueChSimName();
//				map.defaultName = sheetData.get(i).getVenueEngName();
//				searchResult.add(map);
//			}
//		} 
		else if (lang.equals("VenueEng")) {
			String a = new String(sheetData.get(i).getVenueEngName().getBytes(), "utf-8");
			if (a.toLowerCase().contains(s.toLowerCase())||s.toLowerCase().contains(a.toLowerCase())) {
				map.label = sheetData.get(i).getVenueEngName();
				map.defaultName = sheetData.get(i).getVenueChHKName();
				searchResult.add(map);
			}
		}
	}
	
//	if(lang.equals("VenueChHK") && searchResult.size()==0) {
//		for(int j=0;j< sheetData.size(); j++) {
//		
//			String b = new String(sheetData.get(j).getVenueChSimName().getBytes(), "utf-8");
//			if (b.contains(s)||s.contains(b)) {
//				venueMap map = new venueMap();
//				map.id = sheetData.get(j).getVenueId();			
//				map.label = sheetData.get(j).getVenueChSimName();
//				map.defaultName = sheetData.get(j).getVenueEngName();
//				map.locale="VenueChSim";
//				searchResult.add(map);			
//			
//			}
//		}
//		if(searchResult.size()>0) {
//			System.out.println("langVenueChSim");
//			lang="VenueChSim";
//		}
//	}
	
	if(lang.equals("VenueChSim") && searchResult.size()==0) {
		for(int j=0;j< sheetData.size(); j++) {
		
			String b = new String(sheetData.get(j).getVenueChHKName().getBytes(), "utf-8");
			if (b.contains(s)||s.contains(b)) {
				venueMap map = new venueMap();
				map.id = sheetData.get(j).getVenueId();			
				map.label = sheetData.get(j).getVenueChHKName();
				map.defaultName = sheetData.get(j).getVenueEngName();
				map.locale="VenueChHK";
				searchResult.add(map);			
			
			}
		}
		if(searchResult.size()>0) {
			System.out.println("langVenueChHK");
			lang="VenueChHK";
		}
	}
	venueMap[] sortedSearchResult = new venueMap[searchResult.size()];
	if(searchResult.size()>0) {

	for(int i=0;i<searchResult.size();i++) {

		sortedSearchResult[i]=searchResult.get(i);
	}

    Collator collator1 =  Collator.getInstance(Locale.TRADITIONAL_CHINESE); 
    Collator collator2 =  Collator.getInstance(Locale.SIMPLIFIED_CHINESE); 

    int i,j;
    boolean flag = true;  // will determine when the sort is finished    

    venueMap temp = new venueMap();

    while ( flag )
    {
          flag = false;
          for ( j = 0;  j < sortedSearchResult.length - 1;  j++ )
          {
                  if ( 
                	   (lang.equals("VenueEng") && sortedSearchResult[ j ].getLabel().compareTo( sortedSearchResult [ j+1 ].getLabel()) > 0 )||
                	   (lang.equals("VenueChHK") && collator1.compare(sortedSearchResult[ j ].getLabel(),sortedSearchResult [ j+1 ].getLabel()) > 0)||
                	   (lang.equals("VenueChSim") && collator1.compare(sortedSearchResult[ j ].getLabel(),sortedSearchResult [ j+1 ].getLabel()) > 0)
                	  )
                  {                                             // ascending sort
                              temp = sortedSearchResult [ j ];
                              sortedSearchResult [ j ] = sortedSearchResult [ j+1];     // swapping
                              sortedSearchResult [ j+1] = temp;
                              flag = true;
                   }
           }
    }


//	System.out.println("sortedSearchResult:"+(new Gson()).toJson(sortedSearchResult));
//	System.out.println("searchResult:"+(new Gson()).toJson(searchResult));
    //Or can use this sorting if it becomes slow
//    Collections.sort(searchResult,(venueMap o1,venueMap o2)-> Collator.getInstance(Locale.TRADITIONAL_CHINESE).compare(o1.getLabel(),o2.getLabel()));
//  searchResult.forEach((venueMap t)->System.out.println(t.toString()));
	}

	return sortedSearchResult;

}	


}
