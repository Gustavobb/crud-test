package mvc.model;

public class USERS {
	
	private Integer id;
	private String name;
	private String password;
	private String repeatPassword;
	
	public Integer getId() { return this.id; }
	
	public String getName() { return this.name; }
	
	public String getPassword() { return this.password; }
	
	public String getRepeatPassword() { return this.repeatPassword; }
	
	public void setRepeatPassword(String repeatPassword) { this.repeatPassword = repeatPassword; }
	
	public void setName(String name) { this.name = name; }
	
	public void setPassword(String password) { this.password = password; }
	
	public void setId(Integer id) { this.id = id; }
}
