package com.ibm.parking.OIMSimpleParking;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import net.sf.json.JSONObject;

public class QueryParkingTable {
	
	public void query() throws Exception{
		
/**
* CBS_SYS value
* dev FCSF100SW3
* pro FPBF100SW3
*/
	
/**
 * mandt value
 * dev 300
 * pro 100 
 */
		
/**
 * ierpecc value
 * dev YDEY200
 * pro YPE100
*/
		
//		String RDH_CLASS = "RDH";
//		String CBS_CLASS = "MD_ESW_ALL";
		String IERPECC_CLASS = "MD_BH_ALL";
//		String IERPCRM_CLASS = "MD_BH_ALL";
		
//		String RDH_SYS = "RDH";
//	    String CBS_SYS = "FCSF100SW3";
//		String CBS_SYS = "FPBF100SW3";
//		String IERP_SYS = "YDEY220";
		String IERPECC_SYS = "YPE100";
//		String IERPCRM_SYS = "YCC330";
	
//		getJson(RDH_CLASS);
		getJson(IERPECC_CLASS,IERPECC_SYS);
//		getJson(IERPECC_CLASS);
//		getJson(IERPCRM_CLASS);
//		getJson(RDH_SYS);
//		getJson(CBS_SYS);
//		getJson(IERPECC_SYS);
//		getJson(IERPCRM_SYS);
		
	}
	private void getJson(String zdmclass,String zdmlogsys) throws Exception {
		Connection connection = ConnectionFactory.getRDHConnection();
		String count = "select count(*) as count from SAPR3.zdm_parktable where ZDMCLASS='"+zdmclass+"' and MANDT='100' and zdmlogsys='"+zdmlogsys+"' and ZDM_CREATE_DATE>='20190101' and ZDM_CREATE_DATE<='20191231' with ur";
		Hashtable<String,String> counttable = SqlHelper.getSingleRow(count, connection);
		int count_number = Integer.parseInt(counttable.get("count").toString());
		System.out.println(count_number);
		int count_divisor = count_number/2000 +2;
		System.out.println(count_divisor);
		for(int i = 1;i<count_divisor;i++){
		String sql = "select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,'U' as action,zdm_change_type,zdm_req_priority,zdm_broadcast,zdm_source from(select row_number() over(order by mandt) as tt,tab.* "
			+ "from(select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,'U' as action,zdm_change_type,"
			+ "zdm_req_priority,zdm_broadcast,zdm_source "
			+ "from SAPR3.zdm_parktable "
			+ "WHERE ZDMCLASS='"+zdmclass+"' and MANDT='100' and zdmlogsys='"+zdmlogsys+"'and ZDM_CREATE_DATE>='20190101' and ZDM_CREATE_DATE<='20191231'  with ur)tab"
			+ ")where tt between "+(i-1)*2000+" and "+i*2000+"";
			ArrayList<Hashtable<String, String>> teststr = SqlHelper.getMultiRowInfo(sql, connection);
			
	        JSONObject root =new JSONObject();
	        ArrayList<String> list = new ArrayList<String>();
	        list.add("CBS");
	        
	        root.put("repository_list",list);
	        root.put("parktable", teststr);
	        
	        String jsonString1 = root.toString();
//	        System.out.println(jsonString1);
	        CreateFileUtil.createJsonFile(jsonString1, "/fileStorage/download/json", zdmclass+i);
	        System.out.println("complete");
		}
	}

//	private void getJson() throws Exception {
//		Connection connection = ConnectionFactory.getRDHConnection();
//		String count = "select count(*) as count from SAPR3.zdm_parktable where ZDMCLASS='MD_ESW_ALL'";
//		Hashtable<String,String> counttable = SqlHelper.getSingleRow(count, connection);
//		int count_number = Integer.parseInt(counttable.get("count").toString());
//		System.out.println(count_number);
//		int count_divisor = count_number/5000 +1;
//		System.out.println(count_divisor);
//		for(int i = 1;i<count_divisor;i++){
//			String str1 = "select * from(select row_number() over(order by mandt) as tt,tab.* "
//			+ "from(select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,zdm_change_type,"
//			+ "zdm_req_priority,zdm_broadcast,zdm_source "
//			+ "from SAPR3.zdm_parktable "
//			+ "WHERE ZDMCLASS='MD_ESW_ALL' and MANDT='300' with ur)tab"
//			+ ")where tt between "+(i-1)*5000+1+" and "+i*5000+"";
//			ArrayList<Hashtable<String, String>> teststr = SqlHelper.getMultiRowInfo(str1, connection);
//			
//	        JSONObject root =new JSONObject();
//	        ArrayList<String> list = new ArrayList<String>();
//	        list.add("CBS");
//	        list.add("IERP");
//	        
//	        JSONArray array=new JSONArray();
//	        JSONObject major1=new JSONObject();
//	        major1.put("zdmobjtyp", "MAT");
//	        major1.put("zdm_change_num", "TEST");
//	        array.add(major1);
//	        
//	        root.put("repository_list",list);
//	        root.put("parktable", teststr);
////	        JSONArray jsonArray = JSONArray.fromObject(root);
//	        String jsonString1 = root.toString();
//	        System.out.println(jsonString1);
//	        CreateFileUtil.createJsonFile(jsonString1, "/fileStorage/download/json", "agency"+i);
//	        System.out.println("complete");
//		}
//	}
	

}
