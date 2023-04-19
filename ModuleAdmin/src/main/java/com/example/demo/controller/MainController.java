package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.Job;
import com.example.demo.model.Notification;
import com.example.demo.model.Student;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.JobService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.StudentService;
@Controller
public class MainController {
	//add service
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private NotificationService notificationService;
	
	//controller homepage
	@RequestMapping("/homepage")
	public String homepage() {
		return "homepage";
	}
	
	
	//Xu ly controller employee
	
	@RequestMapping("/")
	public String index() throws InterruptedException, ExecutionException {
		System.out.println(jobService.getJobs());
		return "index";
	}
	
	@GetMapping("/add-employee")
	public String addEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "add-employee";
	}
	
	@PostMapping("/save-employee")
	public String saveEmployee(@ModelAttribute Employee employee,
			Model model) throws InterruptedException, ExecutionException {
		//get id
//		List<Employee> listEmployees = employeeService.getEmployees();
//		Employee employeeLast = listEmployees.get(listEmployees.size()-1);
//		String idNewEmployee = String.valueOf((Integer.parseInt(employeeLast.getId()) + 1));
//		employee.setId(idNewEmployee);
		System.out.print(employee);
		//add employee to database
		employeeService.createEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("detail-employee")
	public String detailEmployee(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		
		//data student
		List<Student> listStudent = new ArrayList<>();
		List<Student> students = studentService.getStudents();
		List<Job> job1s = jobService.getJobs();
		
		for(Job job : job1s) {
			if(job.getEmployee() == employee.getName()) {
				for(String jobStudent : job.getStudent()) {
					for(Student student : students) {
						if(jobStudent == student.getEmail() || jobStudent == student.getId()){
							listStudent.add(student);
						}
					}
				}
			}
		}
		
		System.out.print("List student nek" + listStudent);
		
		List<Student> listStudentOfEmployee = employeeService.getStudentOfEmployee(id);
		model.addAttribute("students", listStudentOfEmployee);
		System.out.print(listStudentOfEmployee);
		
		List<Job> jobs = employeeService.getJobByEmployee(employee.getName());
		model.addAttribute("jobs", jobs);
		
		return "detail-employee";
	}
	
	@GetMapping("/update-employee")
	public String updateEmployee(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update-employee";
	}
	
	@PostMapping("save-update-employee")
	public String saveUpdateEmployee(@ModelAttribute Employee employee,
			Model model) throws InterruptedException, ExecutionException {
		
		System.out.print("Update employee: "+employee);
		//update employee to database
		employeeService.updateEmployee(employee);
		return "redirect:/";
	}
	//delete employee
	@GetMapping("/delete-employee")
	public String deleteEmployee(@RequestParam String id) throws InterruptedException, ExecutionException {
		employeeService.deleteEmployee(id);
		return "redirect:/";
	}
	
	//------------------------------------------------------------//
	
	//Xu ly controller student
	@GetMapping("/student/")
	public String student(Model model) throws InterruptedException, ExecutionException {
		List<Student> listStudents = studentService.getStudents();
		model.addAttribute("students", listStudents);
		
		return "list-student";
	}
	@GetMapping("/student/add-student")
	public String addStudent(Model model) {
		model.addAttribute("student", new Student());
		
		List<String> languages = new ArrayList<>();
		languages.add("C");
		languages.add("C++");
		languages.add("C#");
		languages.add("Java");
		languages.add("JavaScript");
		languages.add("Python");
		model.addAttribute("languages", languages);
		
		return "add-student";
	}
	@PostMapping("/student/save-student")
	public String saveStudent(@ModelAttribute Student student,
			Model model) throws InterruptedException, ExecutionException {
		//get id
//		List<Student> listStudents = studentService.getStudents();
//		Student studentLast = listStudents.get(listStudents.size()-1);
//		String idNewStudent = String.valueOf((Integer.parseInt(studentLast.getId()) + 1));
//		student.setId(idNewStudent);
		System.out.print("Student add: "+student);
		//add employee to database
		studentService.createStudent(student);
		return "redirect:/student/";
	}
	
	@GetMapping("/student/detail-student")
	public String detailStudent(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Student student = studentService.getStudentByEmail(id);
		model.addAttribute("student", student);
		
		//data employee in student
//		List<Employee> lisEmployeesOfStudent = employeeService.getEmployeesByStudent(id);
//		model.addAttribute("employees", lisEmployeesOfStudent);
		System.out.print(student);
		
		return "detail-student";
	}
	
	@GetMapping("/student/update-student")
	public String updateStudent(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Student student = studentService.getStudentById(id);
		model.addAttribute("student", student);
		
		List<String> languages = new ArrayList<>();
		languages.add("C");
		languages.add("C++");
		languages.add("C#");
		languages.add("Java");
		languages.add("JavaScript");
		languages.add("Python");
		model.addAttribute("languages", languages);
		
		
		return "update-student";
	}
	//save update info student
	@PostMapping("/student/save-update-student")
	public String saveUpdateStudent(@ModelAttribute Student student,
			Model model) throws InterruptedException, ExecutionException {
		
		Student oldStudent = studentService.getStudentByEmail(student.getEmail());
		
		student.setEmployee(oldStudent.getEmployee());
		
		System.out.print("Update employee: "+student);
		//update employee to database
		studentService.updateStudent(student);
		return "redirect:/student/detail-student?id="+student.getEmail();
	}
	//delete student
	@GetMapping("/student/delete-student")
	public String deleteStudent(@RequestParam String id) throws InterruptedException, ExecutionException {
		studentService.deleteStudent(id);
		return "redirect:/student/";
	}
	
	//-------------------------------------------------------------------
	//Controller job
	
	@GetMapping("/job/")
	public String job(Model model) throws InterruptedException, ExecutionException {
		List<Job> listJobs = jobService.getJobs();
		
		
		List<Employee> employees = new ArrayList<>();
		
//		for(Job job : listJobs) {
//			job.setEmployee(employeeService.getEmployeeById(job.getEmployee()).getName());
//		}
		
		model.addAttribute("jobs", listJobs);
		
		return "list-job";
	}
	
	@GetMapping("/job/detail-job")
	public String detailJob(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Job job = jobService.getJobById(id);
		model.addAttribute("job", job);
		
		//data employee in student
		List<Student> students = studentService.getStudents();
		List<Student> studentOfJob = new ArrayList<>();
		
		for(Student student : students) {
			for(String studentId : job.getStudent()) {
				if(studentId.equals(student.getEmail()) || studentId.equals(student.getId())){
					studentOfJob.add(student);
				}
			}
		}
		model.addAttribute("students", studentOfJob);
		
//		System.out.println("Job and student" + employeeService.getStudentByJob(id));
		
		return "detail-job";
	}
	
	@GetMapping("/job/add-job")
	public String addJob(Model model) throws InterruptedException, ExecutionException {
		model.addAttribute("job", new Job());
		
		model.addAttribute("employees", employeeService.getEmployees());
		
		return "add-job";
	}
	
	@PostMapping("/job/save-job")
	public String saveJob(@ModelAttribute Job job,
			Model model) throws InterruptedException, ExecutionException {
		
		System.out.print("Job add: "+job);
		//add employee to database
		jobService.createEmployee(job);
		return "redirect:/job/";
	}
	@GetMapping("/job/update-job")
	public String updateJob1(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Job job = jobService.getJobById(id);
		model.addAttribute("job", job);		
		
		model.addAttribute("employees", employeeService.getEmployees());

		
		return "update-job";
	}
	//save update info student
	@PostMapping("/job/save-update-job")
	public String saveUpdateJob(@ModelAttribute Job job,
			Model model) throws InterruptedException, ExecutionException {
		
		Job oldJob = jobService.getJobById(job.getId());
		
		job.setStudent(oldJob.getStudent());
		
		System.out.print("Update employee: "+job);
		//update employee to database
		jobService.updateJob(job);
		return "redirect:/job/detail-job?id="+job.getId();
	}
	//delete student
	@GetMapping("/job/delete-job")
	public String deleteJob(@RequestParam String id) throws InterruptedException, ExecutionException {
		jobService.deleteJob(id);
		return "redirect:/job/";
	}
	//-------------------------------------------------------------------------------------
	
	//Controller notification
	
	@GetMapping("/notification/")
	public String notification(Model model) throws InterruptedException, ExecutionException {
		List<Notification> listNotifications = notificationService.getNotifications();
		
		model.addAttribute("notifications", listNotifications);
		
		return "list-notification";
	}
	
	@ModelAttribute("nameNotifications")
	public List<String> getListNameNofication(Model model){
		List<String> listNameNotifition = new ArrayList<>();
		listNameNotifition.add("Bảo trì");
		listNameNotifition.add("Công việc mới");
		listNameNotifition.add("Sự kiện");
		
		return listNameNotifition;
	}
	
	@GetMapping("/notification/add-notification")
	public String addNotification(Model model) {
		model.addAttribute("notification", new Notification());
		return "add-notification";
	}
	
	@PostMapping("/notification/save-notification")
	public String saveNotification(@ModelAttribute Notification notification,
			Model model) throws InterruptedException, ExecutionException {
		
		System.out.print("Notification add: "+notification);
		//add employee to database
		notificationService.createNotification(notification);
		return "redirect:/notification/";
	}
	@GetMapping("/notification/update-notification")
	public String updateNotification(@RequestParam String id, Model model) throws InterruptedException, ExecutionException {
		Notification notification = notificationService.getNotificationById(id);
		model.addAttribute("notification", notification);		
		
//		model.addAttribute("employees", employeeService.getEmployees());

		
		return "update-notification";
	}
	//save update notification
	@PostMapping("/notification/save-update-notification")
	public String saveNotificationJob(@ModelAttribute Notification notification,
			Model model) throws InterruptedException, ExecutionException {
		
		System.out.print("Update employee: "+notification);
		//update employee to database
		notificationService.updateNotification(notification);
		return "redirect:/notification/";
	}
	//delete notification
	@GetMapping("/notification/delete-notification")
	public String deleteNotification(@RequestParam String id) throws InterruptedException, ExecutionException {
		notificationService.deleteNotification(id);
		return "redirect:/notification/";
	}
}
