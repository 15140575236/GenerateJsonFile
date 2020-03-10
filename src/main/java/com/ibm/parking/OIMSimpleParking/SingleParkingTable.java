package com.ibm.parking.OIMSimpleParking;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import net.sf.json.JSONObject;

public class SingleParkingTable {

		
		public void query() throws Exception{

		getJson();
			
		}
		private void getJson() throws Exception {
			Connection connection = ConnectionFactory.getRDHConnection();
//			String count = "select count(*) as A from sapr3.zdm_parktable where ZDMOBJKEY in ('S0187HW','S018DZJ') and ZDM_SESSION = '1999999896' with ur";
//			Hashtable<String,String> counttable = SqlHelper.getSingleRow(count, connection);
//			System.out.println(counttable);
//			int count_number = Integer.parseInt(counttable.get("a").toString());
//			System.out.println(count_number);
//			int count_divisor = count_number;
//			System.out.println(count_divisor);
//			for(int i = 0;i<count_divisor;i++){
//			String sql = "select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,'U' as action,zdm_change_type,zdm_req_priority,zdm_broadcast,zdm_source from(select row_number() over(order by mandt) as tt,tab.* "
//				+ "from(select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,'U' as action,zdm_change_type,"
//				+ "zdm_req_priority,zdm_broadcast,zdm_source "
//				+ "from SAPR3.zdm_parktable "
//				+ "WHERE ZDMCLASS='"+zdmclass+"' and MANDT='300' and zdmlogsys='"+zdmlogsys+"'and ZDM_CREATE_DATE>='20190101' and ZDM_CREATE_DATE<='20191231'  with ur)tab"
//				+ ")where tt between "+(i-1)*2000+" and "+i*2000+"";
			String sql = "select mandt,zdmmsgtyp,zdmobjtyp,zdmobjkey,zdm_change_num,tabname,'R' as zdm_status,'U' as action,zdm_change_type,zdm_req_priority,zdm_broadcast,zdm_source from sapr3.zdm_parktable where ZDMOBJKEY = 'MK_5751CS4_S017281_RELMOD' and ZDMCLASS = 'MD_BH_ALL' with ur";

				ArrayList<Hashtable<String, String>> teststr = SqlHelper.getMultiRowInfo(sql, connection);
				
		        JSONObject root =new JSONObject();
		        ArrayList<String> list = new ArrayList<String>();
		        list.add("IERPECC");
		        
		        root.put("repository_list",list);
		        root.put("parktable", teststr);
		        
		        String jsonString1 = root.toString();
		        CreateFileUtil.createJsonFile(jsonString1, "/fileStorage/download/json1", "ierp1");
		        System.out.println("complete");
			}
		}


