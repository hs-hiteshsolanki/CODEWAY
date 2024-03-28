package com.codeway_task.university_attendance.context;

import android.app.Application;

import com.codeway_task.university_attendance.bean.AttendanceBean;
import com.codeway_task.university_attendance.bean.AttendanceSessionBean;
import com.codeway_task.university_attendance.bean.FacultyBean;
import com.codeway_task.university_attendance.bean.StudentBean;

import java.util.ArrayList;

public class ApplicationContext extends Application {
	private FacultyBean facultyBean;
	private AttendanceSessionBean attendanceSessionBean;
	private ArrayList<StudentBean> studentBeanList;
	private ArrayList<AttendanceBean> attendanceBeanList;
	
	public FacultyBean getFacultyBean() {
		return facultyBean;
	}
	public void setFacultyBean(FacultyBean facultyBean) {
		this.facultyBean = facultyBean;
	}
	public AttendanceSessionBean getAttendanceSessionBean() {
		return attendanceSessionBean;
	}
	public void setAttendanceSessionBean(AttendanceSessionBean attendanceSessionBean) {
		this.attendanceSessionBean = attendanceSessionBean;
	}
	public ArrayList<StudentBean> getStudentBeanList() {
		return studentBeanList;
	}
	public void setStudentBeanList(ArrayList<StudentBean> studentBeanList) {
		this.studentBeanList = studentBeanList;
	}
	public ArrayList<AttendanceBean> getAttendanceBeanList() {
		return attendanceBeanList;
	}
	public void setAttendanceBeanList(ArrayList<AttendanceBean> attendanceBeanList) {
		this.attendanceBeanList = attendanceBeanList;
	}
	
	

}
