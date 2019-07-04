package com.olayc.common.vo;


import lombok.Data;

@Data
public class EmployeeInfo {
	private long id;
	private String loginName;
	private String employeeName;
	private String employeePhone;
	private String employeePost;
	private String employeeGroupIdChain;
	private String employeeGroupNameChain;
	private String employeeRole;
	private String modulePermission;
	private String resPermission;
	private long version;
	//获取操作者姓名，对应数据库create_name || modified_name
	public String getOpreatorName(){
		return employeeName+"("+loginName+")";
	}
}
